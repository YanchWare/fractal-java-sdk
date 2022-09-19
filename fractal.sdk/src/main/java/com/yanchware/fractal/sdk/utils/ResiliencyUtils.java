package com.yanchware.fractal.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.configuration.instantiation.InstantiationWaitConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.exceptions.ProviderException;
import com.yanchware.fractal.sdk.services.LiveSystemService;
import com.yanchware.fractal.sdk.services.contracts.providers.dtos.ProviderLiveSystemComponentDto;
import com.yanchware.fractal.sdk.services.contracts.providers.responses.CurrentLiveSystemsResponse;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentStatusDto.Active;
import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentStatusDto.Failed;
import static com.yanchware.fractal.sdk.utils.HttpUtils.*;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.deserialize;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
public class ResiliencyUtils {

  public static void executeRequestWithRetries(
      String requestName,
      HttpClient client,
      RetryRegistry retryRegistry,
      HttpRequest request,
      int[] acceptedResponses) throws InstantiatorException {

    Retry retry = retryRegistry.retry(requestName);

    CheckedFunction0<Throwable> callWithRetry = Retry.decorateCheckedSupplier(retry, () -> {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      ensureAcceptableResponse(response, requestName, acceptedResponses);

      return null;
    });

    Try<Throwable> result = Try.of(callWithRetry);

    if (result.isFailure()) {
      throw new InstantiatorException(
          String.format("All attempts for request %s failed", requestName),
          result.get());
    }
  }

  public static <T> T executeRequestWithRetries(
      String requestName,
      HttpClient client,
      RetryRegistry retryRegistry,
      HttpRequest request,
      int[] acceptedResponses,
      Class<T> classRef) throws InstantiatorException {

    Retry retry = retryRegistry.retry(requestName);

    CheckedFunction0<T> callWithRetry = Retry.decorateCheckedSupplier(retry, () -> {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      ensureAcceptableResponse(response, requestName, acceptedResponses);

      if (response.statusCode() == 404) {
        log.info("Attempted {} has come up with a 404 Not Found. Will attempt to create it.", requestName);
        return null;
      }

      String bodyContents = response.body();
      if (isBlank(bodyContents)) {
        log.error("Attempted {} has come up with empty or null body contents: {}", requestName, bodyContents);
        return null;
      }

      try {
        return deserialize(bodyContents, classRef);
      } catch (JsonProcessingException e) {
        log.error("Attempted {} failed. Deserialization of {} failed.", requestName, bodyContents);
        return null;
      }

    });

    Try<T> result = Try.of(callWithRetry);

    if (result.isFailure()) {
      log.error("Attempted {} failed all attempts", requestName, result.getCause());
      throw new InstantiatorException(
          String.format("All attempts for request %s failed with cause: %s", requestName, result.getCause()));
    }

    return result.get();
  }

  public static <T> T executeRequestWithRetries(
      String requestName,
      HttpClient client,
      HttpRequest request,
      int[] acceptedResponses,
      Class<T> classRef,
      String liveSystemId,
      LiveSystemService liveSystemService,
      InstantiationWaitConfiguration waitConfig) throws InstantiatorException {

    var retryConfig = RetryConfig.custom()
        .ignoreExceptions(ProviderException.class)
        .maxAttempts(waitConfig.getTimeoutMinutes() + 1)
        .waitDuration(Duration.ofMinutes(1L))
        .build();

    var retry = RetryRegistry.of(retryConfig).retry(requestName);

    var callWithRetry = Retry.decorateCheckedSupplier(retry, () -> {
      var response = client.send(request, HttpResponse.BodyHandlers.ofString());
      ensureAcceptableResponse(response, requestName, acceptedResponses);

      if (response.statusCode() == 404) {
        log.info("Attempted {} has come up with a 404 Not Found. Will attempt to create it.", requestName);
        return null;
      }

      String bodyContents = response.body();
      if (isBlank(bodyContents)) {
        log.error("Attempted {} has come up with empty or null body contents: {}", requestName, bodyContents);
        return null;
      }
      T deserialized;
      try {
        deserialized = deserialize(bodyContents, classRef);
      } catch (JsonProcessingException e) {
        log.error("Attempted {} failed. Deserialization of {} failed.", requestName, bodyContents);
        return null;
      }

      var currentLiveSystem = liveSystemService.retrieveLiveSystem(liveSystemId);
      if (currentLiveSystem == null) {
        String message = String.format("LiveSystem [%s] found", liveSystemId);
        log.info(message);
        throw new ProviderException(message);
      }

      CurrentLiveSystemsResponse liveSystemsResponse = (CurrentLiveSystemsResponse) deserialized;
      if (liveSystemsResponse == null || CollectionUtils.isBlank(liveSystemsResponse.getLiveSystems())) {
        String message = String.format("The live-system [%s] exists, but the livesystem providers collection is still empty",
            liveSystemId);
        log.info(message);
        throw new ProviderException(message);
      }

      var optionalProviderLiveSystemDto = liveSystemsResponse.getLiveSystems().stream()
          .filter(x -> x.getId().equalsIgnoreCase(liveSystemId)).findFirst();
      if (optionalProviderLiveSystemDto.isEmpty()) {
        String message = String.format("The live-system [%s] exists, but the provider for livesystem [%s] ts still empty",
            liveSystemId);
        log.info(message);
        return null;
      }

      var liveSystem = optionalProviderLiveSystemDto.get();

      // Retrieve the components' statuses:
      var activeComponents = new ArrayList<ProviderLiveSystemComponentDto>();
      var failedComponents = new ArrayList<ProviderLiveSystemComponentDto>();
      for (var component: liveSystem.getComponents()) {
        if(component.getStatus() == Active) {
          activeComponents.add(component);
        }
        if(component.getStatus() == Failed) {
          failedComponents.add(component);
        }
      }
      // All components instantiated:
      if(activeComponents.size() == liveSystem.getComponents().size()) {
        log.info("Livesystem [{}] instantiated. Active components: {}",
            liveSystemId,
            serialize(activeComponents));
        return deserialized;
      }
      // At least one one component failed (with fail fast configuration):
      else if (waitConfig.isFailFast() && failedComponents.size() > 0) {
        String faileInstantiationMessage = String.format(
            "Livesystem [%s] instantiation failed. Failed components: %s",
            liveSystemId,
            serialize(failedComponents)
        );
        throw new ProviderException(failedComponents, faileInstantiationMessage);
      }
      // All the components have completed or failed the instantiation (with no fail fast configuration):
      else if (!waitConfig.isFailFast() &&
          failedComponents.size() + activeComponents.size() == liveSystem.getComponents().size()) {
        String failedInstantiationMessage = String.format(
            "Livesystem [%s] instantiation failed. Failed components: %s",
            liveSystemId,
            serialize(failedComponents)
        );
        throw new ProviderException(failedComponents, failedInstantiationMessage);
      }

      // The instantiation is still in progress:
      log.info("Livesystem [{}] instantiation is still in progress...", liveSystemId);
      log.info("Livesystem [{}] - [Active] components: {}", liveSystemId,
          activeComponents.stream().map(c -> c.getId()).collect(Collectors.joining(", ")));
      log.info("Livesystem [{}] - [Failed] components: {}", liveSystemId,
          failedComponents.stream().map(c -> c.getId()).collect(Collectors.joining(", ")));
      String timeoutExceptionMessage = String.format(
          "Livesystem [%s] instantiation wait timeout. Response is %s",
          liveSystemId,
          response.body());
      throw new InstantiatorException(timeoutExceptionMessage);
    });

    Try<T> result = Try.of(callWithRetry);

    if (result.isFailure()) {
      throw new InstantiatorException(
          String.format("Livesystem [%s] - all attempts for request %s failed with cause: %s",
              liveSystemId,
              requestName,
              result.getCause()));
    }

    return result.get();
  }

}