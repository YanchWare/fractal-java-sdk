package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.configuration.instantiation.InstantiationWaitConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.exceptions.ProviderException;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.InstantiationStepDto;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemMutationDto;
import com.yanchware.fractal.sdk.services.contracts.providerscontract.dtos.ProviderLiveSystemComponentDto;
import com.yanchware.fractal.sdk.services.contracts.providerscontract.responses.LiveSystemMutationResponse;
import com.yanchware.fractal.sdk.utils.HttpUtils;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentStatusDto.Active;
import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentStatusDto.Failed;
import static com.yanchware.fractal.sdk.utils.HttpUtils.ensureAcceptableResponse;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.deserialize;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@AllArgsConstructor
public class ProviderService {

  private final HttpClient client;
  private final SdkConfiguration sdkConfiguration;

  public void checkLiveSystemMutationStatus(
    LiveSystem liveSystem,
    LiveSystemMutationDto liveSystemMutation,
    InstantiationWaitConfiguration config) throws InstantiatorException {
    log.info("Starting operation [checkLiveSystemMutationStatus] for livesystem [{}] and mutation [{}]",
        liveSystem.getLiveSystemId(), liveSystemMutation.getId());

    var requestName = "checkLiveSystemMutationStatus";
    var acceptedResponses = new int[]{ 200, 404 };
    var request = HttpUtils.buildGetRequest(
      getProvidersLiveSystemMutationUri(
          liveSystem.getResourceGroupId(),
          sdkConfiguration.getProviderName(),
          liveSystem.getName(),
          liveSystemMutation.getId()
      ),
      sdkConfiguration
    );

    var retryConfig = RetryConfig.custom()
        .ignoreExceptions(ProviderException.class)
        .maxAttempts(config.getTimeoutMinutes() + 1)
        .waitDuration(Duration.ofMinutes(3L))
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

      LiveSystemMutationResponse liveSystemMutationResponse;
      try {
        liveSystemMutationResponse = deserialize(bodyContents, LiveSystemMutationResponse.class);
      } catch (JsonProcessingException e) {
        String message = String.format("Attempted %s failed. Deserialization of %s failed.", requestName, bodyContents);
        log.error(message);
        throw new ProviderException(message);
      }

      if (liveSystemMutationResponse == null) {
        String message = String.format("The live-system [%s] exists, but the livesystem's provider livesystem is still empty or not fully updated for mutation [%s]",
            liveSystem.getLiveSystemId(), liveSystemMutation.getId());
        log.info(message);
        throw new InstantiatorException(message);
      }

      // Load components' statuses:
      var activeComponents = new ArrayList<ProviderLiveSystemComponentDto>();
      var failedComponents = new ArrayList<ProviderLiveSystemComponentDto>();
      for (var component: liveSystemMutationResponse.getComponents()) {
        if(component.getStatus() == Active) {
          activeComponents.add(component);
        }
        if(component.getStatus() == Failed) {
          failedComponents.add(component);
        }
      }

      // All components instantiated:
      if(activeComponents.size() == liveSystemMutationResponse.getComponents().size() &&
          liveSystemMutationResponse.getComponents().size() == liveSystemMutation.getStepsById().size()) {
        log.info("Livesystem [{}] instantiated for mutation [{}]. Active components: {}",
            liveSystem.getLiveSystemId(),
            liveSystemMutation.getId(),
            serialize(activeComponents));
        return liveSystemMutationResponse;
      }

      // At least one one component failed (with fail fast configuration):
      else if (config.isFailFast() && failedComponents.size() > 0) {
        String faileInstantiationMessage = String.format(
            "Livesystem [%s] instantiation failed for mutation [%s]. Failed components: %s",
            liveSystem.getLiveSystemId(),
            liveSystemMutation.getId(),
            serialize(failedComponents)
        );
        throw new ProviderException(failedComponents, faileInstantiationMessage);
      }

      // All the components have completed or failed the instantiation (with no fail fast configuration):
      else if (!config.isFailFast() &&
          failedComponents.size() + activeComponents.size() == liveSystemMutationResponse.getComponents().size()) {
        String failedInstantiationMessage = String.format(
            "Livesystem [%s] instantiation failed for mutation [%s]. Failed components: %s",
            liveSystem.getLiveSystemId(),
            liveSystemMutation.getId(),
            serialize(failedComponents)
        );
        throw new ProviderException(failedComponents, failedInstantiationMessage);
      }

      // The instantiation is still in progress:
      log.info("Livesystem [{}] instantiation is still in progress...", liveSystem.getLiveSystemId());
      log.info("Livesystem [{}] - [All] components: {}", liveSystem.getLiveSystemId(),
          liveSystemMutation.getStepsById().values().stream().map(InstantiationStepDto::getId).collect(Collectors.joining(", ")));
      log.info("Livesystem [{}] - [Active] components: {}", liveSystem.getLiveSystemId(),
          activeComponents.stream().map(ComponentDto::getId).collect(Collectors.joining(", ")));

      if (failedComponents.size() > 0) {
        log.info("Livesystem [{}] - [Failed] components: {}", liveSystem.getLiveSystemId(),
            failedComponents.stream().map(ComponentDto::getId).collect(Collectors.joining(", ")));
      }

      String timeoutExceptionMessage = String.format(
          "Livesystem [%s] instantiation wait timeout. Response is %s",
          liveSystem.getLiveSystemId(),
          response.body());
      throw new InstantiatorException(timeoutExceptionMessage);
    });

    Try<LiveSystemMutationResponse> result = Try.of(callWithRetry);

    if (result.isFailure()) {
      throw new InstantiatorException(
          String.format("Livesystem [%s] - all attempts for request %s failed with cause: %s",
              liveSystem.getLiveSystemId(),
              requestName,
              result.getCause()));
    }

  }

  private URI getProvidersLiveSystemMutationUri(
      String resourceGroupId,
      String providerName,
      String liveSystemName,
      String mutationId)
  {
    if (isBlank(providerName)) {
      throw new IllegalArgumentException("In order to use certain SDK features the provider name needs to be set " +
        "through SDK configuration");
    }
    return URI.create(String.format("%s/%s/%s/livesystems/%s/mutations/%s",
        sdkConfiguration.getProviderEndpoint(), resourceGroupId, providerName, liveSystemName, mutationId));
  }
}
