package com.yanchware.fractal.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.exceptions.ProviderException;
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
import java.util.Arrays;

import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentStatusDto.Active;
import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentStatusDto.Failed;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.deserialize;
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
      if (Arrays.stream(acceptedResponses).noneMatch((x) -> x == response.statusCode())) {
        throw new InstantiatorException(
            String.format(
                "Attempted %s failed with response code: %s and body %s ",
                requestName,
                response.statusCode(),
                response.body()));
      }

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
      if (Arrays.stream(acceptedResponses).noneMatch((x) -> x == response.statusCode())) {
        String errorMessage = String.format(
            "Attempted %s failed with response code: %s and body %s ",
            requestName,
            response.statusCode(),
            response.body());
        log.error(errorMessage);
        throw new InstantiatorException(errorMessage);
      }

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
      String liveSystemId) throws InstantiatorException {

    //12 attempts each at 10 minutes = 2 hours
    RetryConfig retryConfig = RetryConfig.custom()
        .ignoreExceptions(ProviderException.class)
        .maxAttempts(12)
        .waitDuration(Duration.ofMinutes(10L))
        .build();

    Retry retry = RetryRegistry.of(retryConfig).retry(requestName);

    CheckedFunction0<T> callWithRetry = Retry.decorateCheckedSupplier(retry, () -> {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if (Arrays.stream(acceptedResponses).noneMatch((x) -> x == response.statusCode())) {
        String errorMessage = String.format(
            "Attempted %s failed with response code: %s and body %s ",
            requestName,
            response.statusCode(),
            response.body());
        log.error(errorMessage);
        throw new InstantiatorException(errorMessage);
      }

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

      CurrentLiveSystemsResponse liveSystemsResponse = (CurrentLiveSystemsResponse) deserialized;
      if (liveSystemsResponse == null || CollectionUtils.isBlank(liveSystemsResponse.getLiveSystems())) {
        log.info("No LiveSystems found.");
        return null;
      }

      var optionalProviderLiveSystemDto = liveSystemsResponse.getLiveSystems().stream().filter(x -> x.getId().equalsIgnoreCase(liveSystemId)).findFirst();
      if (optionalProviderLiveSystemDto.isEmpty()) {
        log.info("Could not find live system with id [{}]", liveSystemId);
        return null;
      }

      var liveSystem = optionalProviderLiveSystemDto.get();

      if (liveSystem.getComponents().stream().allMatch(x -> x.getStatus().equals(Active))) {
        log.debug("All components are Active");
        return deserialized;
      } else if (liveSystem.getComponents().stream().anyMatch(x -> x.getStatus().equals(Failed))) {
        log.debug("At least one component has status Failed");
        throw new ProviderException("At least one component has status Failed");
      }

      String errorMessage = String.format(
          "Attempted %s failed because components are not Active or Failed. Full LiveSystem: %s ",
          requestName,
          response.body());
      log.error(errorMessage);
      throw new InstantiatorException(errorMessage);
    });

    Try<T> result = Try.of(callWithRetry);

    if (result.isFailure()) {
      log.error("Attempted {} failed all attempts", requestName, result.getCause());
      throw new InstantiatorException(
          String.format("All attempts for request %s failed with cause: %s", requestName, result.getCause()));
    }

    return result.get();
  }

}