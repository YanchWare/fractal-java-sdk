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

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentStatusDto.*;
import static com.yanchware.fractal.sdk.utils.HttpUtils.ensureAcceptableResponse;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.deserialize;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@AllArgsConstructor
public class ProviderService {

  private final HttpClient client;
  private final SdkConfiguration sdkConfiguration;

  public void checkLiveSystemMutationStatus(LiveSystem liveSystem,
                                            LiveSystemMutationDto liveSystemMutation,
                                            InstantiationWaitConfiguration config)
      throws InstantiatorException {
    log.info("Starting operation [checkLiveSystemMutationStatus] for LiveSystem [id: {}] and mutation ['{}']",
        liveSystem.getLiveSystemId(), liveSystemMutation.getId());

    var requestName = "checkLiveSystemMutationStatus";
    var acceptedResponses = new int[]{200, 404};
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

    var callWithRetry = Retry.decorateCheckedSupplier(retry, () -> 
        getLiveSystemMutationResponse(liveSystem, 
            liveSystemMutation, 
            config, 
            requestName, 
            acceptedResponses, 
            request));

    Try<LiveSystemMutationResponse> result = Try.of(callWithRetry);

    if (result.isFailure()) {
      throw new InstantiatorException(
          String.format("LiveSystem [%s] - all attempts for request %s failed with cause: %s",
              liveSystem.getLiveSystemId(),
              requestName,
              result.getCause()));
    }
  }

  private LiveSystemMutationResponse getLiveSystemMutationResponse(LiveSystem liveSystem, LiveSystemMutationDto liveSystemMutation, InstantiationWaitConfiguration config, String requestName, int[] acceptedResponses, HttpRequest request) throws IOException, InterruptedException, InstantiatorException, ProviderException {
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
      String message = String.format("The LiveSystem [%s] exists, but the LiveSystem's provider LiveSystem is still empty or not fully updated for mutation [%s]",
          liveSystem.getLiveSystemId(), liveSystemMutation.getId());
      log.info(message);
      throw new InstantiatorException(message);
    }
    
    var liveSystemId = liveSystem.getLiveSystemId();
    var liveSystemMutationResponseComponents = liveSystemMutationResponse.getComponents();
    var liveSystemMutationId = liveSystemMutation.getId();
    var liveSystemMutationStepsById = liveSystemMutation.getStepsById();

    var processedComponents = new ArrayList<ProviderLiveSystemComponentDto>();
    var failedComponents = new ArrayList<ProviderLiveSystemComponentDto>();
    for (var component : liveSystemMutationResponseComponents) {
      var componentStatus = component.getStatus();
      if (componentStatus == Active || componentStatus == Deleted) {
        processedComponents.add(component);
      } else if (componentStatus == Failed) {
        failedComponents.add(component);
      }
    }

    processedComponents.sort(Comparator.comparing(ProviderLiveSystemComponentDto::getId));
    failedComponents.sort(Comparator.comparing(ProviderLiveSystemComponentDto::getId));

    // All components instantiated:
    if (processedComponents.size() == liveSystemMutationResponseComponents.size() &&
        liveSystemMutationResponseComponents.size() == liveSystemMutationStepsById.size()) {
      log.info("LiveSystem [id: '{}'] instantiated for mutation ['{}']. Active components [{}] -> {}",
          liveSystemId,
          liveSystemMutationId,
          processedComponents.size(),
          serialize(processedComponents));
      return liveSystemMutationResponse;
    } else if (config.isFailFast() && failedComponents.size() > 0) {
      // At least one component failed (with fail fast configuration):
      var failedInstantiationMessage = String.format(
          "LiveSystem [%s] instantiation failed for mutation [%s]. Failed components [%s] -> %s",
          liveSystemId,
          liveSystemMutationId,
          failedComponents.size(),
          serialize(failedComponents)
      );
      throw new ProviderException(failedComponents, failedInstantiationMessage);
    } else if (!config.isFailFast() &&
        failedComponents.size() + processedComponents.size() == liveSystemMutationResponseComponents.size()) {
      // All the components have completed or failed the instantiation (with no fail fast configuration):
      var failedInstantiationMessage = String.format(
          "LiveSystem [%s] instantiation failed for mutation [%s]. Failed components [%s] -> %s",
          liveSystemId,
          liveSystemMutation.getId(),
          failedComponents.size(),
          serialize(failedComponents)
      );
      throw new ProviderException(failedComponents, failedInstantiationMessage);
    }

    // The instantiation is still in progress:
    log.info("LiveSystem [id: '{}'] instantiation is still in progress ...", liveSystemId);

    logAllComponents(liveSystemId, liveSystemMutationStepsById);

    logProcessedComponents(liveSystemId, processedComponents);

    logFailedComponents(liveSystemId, failedComponents);

    String timeoutExceptionMessage = String.format(
        "LiveSystem [%s] instantiation wait timeout. Response is %s",
        liveSystem.getLiveSystemId(),
        response.body());
    throw new InstantiatorException(timeoutExceptionMessage);
  }

  private static void logAllComponents(String liveSystemId, Map<String, InstantiationStepDto> liveSystemMutationStepsById) {
    var allComponentsIdsSorted = liveSystemMutationStepsById
        .values()
        .stream()
        .map(InstantiationStepDto::getId)
        .sorted()
        .toList();

    log.info("LiveSystem [id: '{}'] - All components [{}] -> {}",
        liveSystemId,
        allComponentsIdsSorted.size(),
        String.join(", ", allComponentsIdsSorted));
  }

  private static void logProcessedComponents(String liveSystemId, ArrayList<ProviderLiveSystemComponentDto> processedComponents) {
    var processedComponentsIdsSorted = processedComponents
        .stream()
        .map(ComponentDto::getId)
        .sorted()
        .toList();

    log.info("LiveSystem [id: '{}'] - Processed components [{}] -> {}",
        liveSystemId,
        processedComponentsIdsSorted.size(),
        String.join(", ", processedComponentsIdsSorted));
  }

  private static void logFailedComponents(String liveSystemId, ArrayList<ProviderLiveSystemComponentDto> failedComponents) {
    if (failedComponents.size() > 0) {
      var failedComponentsIdsSorted = failedComponents
          .stream()
          .map(ComponentDto::getId)
          .sorted()
          .toList();
      
      log.info("LiveSystem [id: '{}'] - Failed components [{}] -> {}",
          liveSystemId,
          failedComponentsIdsSorted.size(),
          String.join(", ", failedComponentsIdsSorted));
    }
  }

  private URI getProvidersLiveSystemMutationUri(
      String resourceGroupId,
      String providerName,
      String liveSystemName,
      String mutationId) {
    return URI.create(String.format("%s/%s/%s/livesystems/%s/mutations/%s",
        sdkConfiguration.getProviderEndpoint(), resourceGroupId, providerName, liveSystemName, mutationId));
  }
}
