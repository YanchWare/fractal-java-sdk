package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemId;
import com.yanchware.fractal.sdk.domain.exceptions.ComponentInstantiationException;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.exceptions.ProviderException;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.InstantiateLiveSystemCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.UpdateLiveSystemCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.*;
import com.yanchware.fractal.sdk.utils.EnvVarUtils;
import com.yanchware.fractal.sdk.utils.HttpUtils;
import com.yanchware.fractal.sdk.utils.LocalDebugUtils;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

import static com.yanchware.fractal.sdk.utils.HttpUtils.ensureAcceptableResponse;
import static com.yanchware.fractal.sdk.utils.ResiliencyUtils.executeRequestWithRetries;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.deserialize;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@AllArgsConstructor
public class LiveSystemService {

  private static final int CHECK_LIVE_SYSTEM_MUTATION_STATUS_MAX_ATTEMPTS = 55;
  private final HttpClient client;
  private final SdkConfiguration sdkConfiguration;
  private final RetryRegistry retryRegistry;

  public LiveSystemMutationDto instantiate(InstantiateLiveSystemCommandRequest command) throws InstantiatorException {
    log.info("Starting to instantiate live system: {}", getCommandAsJsonString(command));

    if (retrieveLiveSystem(new LiveSystemId(command.getLiveSystemId())) != null) {
      return updateLiveSystem(UpdateLiveSystemCommandRequest.fromInstantiateCommand(command));
    }

    return instantiateLiveSystem(command);
  }

  public void checkLiveSystemMutationStatus(
      LiveSystemId liveSystemId,
      String liveSystemMutationId) throws InstantiatorException {
    log.info("Starting operation [checkLiveSystemMutationStatus] for LiveSystem [id: '{}'] and mutation ['{}']",
        liveSystemId.toString(), liveSystemMutationId);

    var requestName = "checkLiveSystemMutationStatus";

    var acceptedResponses = new int[]{200, 404};

    var request = HttpUtils.buildGetRequest(
        getLiveSystemMutationUri(liveSystemId, liveSystemMutationId),
        sdkConfiguration);

    var retryConfig = RetryConfig.custom()
        .retryExceptions(InstantiatorException.class)
        .maxAttempts(CHECK_LIVE_SYSTEM_MUTATION_STATUS_MAX_ATTEMPTS)
        .waitDuration(Duration.ofMinutes(1L))
        .build();

    var retry = RetryRegistry.of(retryConfig).retry(requestName);

    try {
      Retry.decorateCheckedSupplier(retry, () ->
          checkLiveSystemMutationStatus(
              liveSystemId,
              liveSystemMutationId,
              requestName,
              acceptedResponses,
              request)).get();
    } catch (Throwable ex) {
      throw new InstantiatorException(ex.getLocalizedMessage());
    }
  }

  private LiveSystemMutationDto checkLiveSystemMutationStatus(
      LiveSystemId liveSystemId,
      String liveSystemMutationId,
      String requestName,
      int[] acceptedResponses,
      HttpRequest request) throws IOException, InterruptedException, InstantiatorException, ProviderException {

    if (EnvVarUtils.isLocalDebug()) {
      request = LocalDebugUtils.getHttpRequestBuilder(request.uri(), sdkConfiguration)
          .build();
    }

    var livesystemIdStr = liveSystemId.toString();
    var response = client.send(request, HttpResponse.BodyHandlers.ofString());
    ensureAcceptableResponse(response, requestName, acceptedResponses);

    if (response.statusCode() == 404) {
      throw new InstantiatorException(
          String.format("Attempted %s has come up with a 404 Not Found. Will attempt to create it", requestName));
    }

    String bodyContents = response.body();
    if (isBlank(bodyContents)) {
      throw new InstantiatorException(
          String.format("Attempted %s has come up with empty or null body contents", requestName));
    }

    var liveSystemMutationResponse = getLiveSystemMutationResponse(requestName, bodyContents);

    if (liveSystemMutationResponse == null) {
      String message = String.format("The LiveSystem [%s] exists, but the LiveSystem's provider LiveSystem is still empty or not fully updated for mutation [%s]",
          livesystemIdStr, liveSystemMutationId);
      log.info(message);
      throw new InstantiatorException(message);
    }

    var liveSystemMutationResponseComponents = liveSystemMutationResponse.getComponentsById();
    if (liveSystemMutationResponseComponents == null) {
      throw new ProviderException(String.format("LiveSystem [%s] instantiation failed for mutation [%s] as component list is null",
          livesystemIdStr,
          liveSystemMutationId));
    }

    switch (liveSystemMutationResponse.getStatus()) {
      case COMPLETED -> {
        log.info("LiveSystem [id: '{}'] instantiation completed: {}", liveSystemId, getStatusFromComponents(liveSystemMutationResponseComponents));

        return liveSystemMutationResponse;
      }
      case FAILED -> {
        var messageToThrow = getFailedComponentsMessageToThrow(livesystemIdStr,
            liveSystemMutationId,
            liveSystemMutationResponseComponents);

        if (StringUtils.isBlank(messageToThrow)) {
          log.warn("LiveSystem [id: '{}'] instantiation failed but erorrMessage is not visible yet, retrying: {}", liveSystemId, getStatusFromComponents(liveSystemMutationResponseComponents));
          String timeoutExceptionMessage = String.format(
              "LiveSystem [%s] instantiation wait timeout. Response is %s",
              livesystemIdStr,
              response.body());
          throw new InstantiatorException(timeoutExceptionMessage);
        } else {
          log.warn("LiveSystem [id: '{}'] instantiation failed: {}", liveSystemId, getStatusFromComponents(liveSystemMutationResponseComponents));
          throw new ProviderException(messageToThrow);
        }
      }
      case CANCELLED -> {
        log.warn("LiveSystem [id: '{}'] instantiation cancelled: {}", liveSystemId, getStatusFromComponents(liveSystemMutationResponseComponents));

        var cancelledInstantiationMessage = String.format(
            "LiveSystem [%s] instantiation did not complete for mutation [%s] as the latter has been cancelled by an operator",
            liveSystemId,
            liveSystemMutationId);
        throw new ProviderException(cancelledInstantiationMessage);
      }
      case INPROGRESS -> {
        var uniqueStatuses = getUniqueStatusesFromComponents(liveSystemMutationResponseComponents);

        if (allComponentsProcessed(uniqueStatuses)) {
          log.info("LiveSystem [id: '{}'] instantiation completed: {}", liveSystemId, getStatusFromComponents(liveSystemMutationResponseComponents));

          return liveSystemMutationResponse;
        } else {
          log.info("LiveSystem [id: '{}'] instantiation is in progress: {}", liveSystemId, getStatusFromComponents(liveSystemMutationResponseComponents));
          String timeoutExceptionMessage = String.format(
              "LiveSystem [%s] instantiation wait timeout. Response is %s",
              livesystemIdStr,
              response.body());
          throw new InstantiatorException(timeoutExceptionMessage);
        }
      }
      default ->
          throw new ProviderException(String.format("LiveSystem [%s] instantiation has an unknown state for mutation [%s]: [%s]",
              livesystemIdStr,
              liveSystemMutationId,
              liveSystemMutationResponse.getStatus()));
    }
  }

  private boolean allComponentsProcessed(String uniqueStatuses) {
    return uniqueStatuses.equals("Active")
        || uniqueStatuses.equals("Active,Deleted")
        || uniqueStatuses.equals("Deleted");
  }

  private String getUniqueStatusesFromComponents(Map<String, LiveSystemComponentDto> mutationComponents) {
    return mutationComponents.values()
        .stream()
        .map(c -> c.getStatus().toString())
        .distinct()
        .collect(Collectors.joining(","));
  }

  private String getFailedComponentsMessageToThrow(String liveSystemId,
                                                   String liveSystemMutationId,
                                                   Map<String, LiveSystemComponentDto> mutation) {
    var failedComponents = mutation.values()
        .stream()
        .filter(c -> c.getStatus() == LiveSystemComponentStatusDto.Failed)
        .toList();

    if (failedComponents.isEmpty()) {
      return "";
    } else {
      var messageToThrow = new StringBuilder();
      messageToThrow.append(
          String.format("LiveSystem [id: '%s'] instantiation failed for mutation [id: '%s']. " +
                  "Failed components -> ",
              liveSystemId, liveSystemMutationId));
      for (var component : failedComponents) {
        var errorMessage = component.getLastOperationStatusMessage();

        if (StringUtils.isBlank(errorMessage)) {
          return "";
        }

        messageToThrow.append(String.format("id: '%s', status: '%s', errorMessage: '%s' - ",
            component.getId(),
            component.getStatus(),
            errorMessage));
      }

      return messageToThrow.substring(0, messageToThrow.length() - 3);
    }
  }

  private String getStatusFromComponents(Map<String, LiveSystemComponentDto> liveSystemMutationResponseComponents) {
    var sb = new StringBuilder();

    for (var component : liveSystemMutationResponseComponents.values()) {
      sb.append(String.format("%s [%s] - ", component.getId(), component.getStatus()));
    }
    var status = sb.toString();
    return status.substring(0, status.length() - 3);
  }

  private static LiveSystemMutationDto getLiveSystemMutationResponse(String requestName, String bodyContents) throws ProviderException {
    try {
      return deserialize(bodyContents, LiveSystemMutationDto.class);
    } catch (JsonProcessingException e) {
      String message = String.format("Attempted %s failed. Deserialization of %s failed.", requestName, bodyContents);
      log.error(message);
      throw new ProviderException(message);
    }
  }

  private static LiveSystemComponentMutationDto getLiveSystemComponentMutationResponse(String requestName, String bodyContents) throws ProviderException {
    try {
      return deserialize(bodyContents, LiveSystemComponentMutationDto.class);
    } catch (JsonProcessingException e) {
      String message = String.format("Attempted %s failed. Deserialization of %s failed.", requestName, bodyContents);
      log.error(message);
      throw new ProviderException(message);
    }
  }

  public LiveSystemDto retrieveLiveSystem(LiveSystemId liveSystemId) throws InstantiatorException {
    HttpResponse<String> response;
    try {
      response = client.send(
          HttpUtils.buildGetRequest(getLiveSystemUri(liveSystemId), sdkConfiguration),
          HttpResponse.BodyHandlers.ofString()
      );
    } catch (IOException | InterruptedException e) {
      throw new InstantiatorException("Attempted Retrieve LiveSystem failed", e);
    }

    if (response.statusCode() == 404) {
      log.info("LiveSystem [id: '{}'] does not exist. Adding it ...", liveSystemId);
      return null;
    }

    if (response.statusCode() != 200) {
      throw new InstantiatorException(
          String.format(
              "Attempted Retrieve LiveSystem with response code: %s and body %s ",
              response.statusCode(),
              response.body()));
    }

    return getDeserializedResponseBody(response.body());
  }

  public LiveSystemComponentMutationDto instantiateComponent(String resourceGroupId, String liveSystemName, String componentId)
      throws InstantiatorException {
    log.info("Instantiating component [id: '{}'] in LiveSystem [name: '{}', resource group id: '{}']",
        componentId, liveSystemName, resourceGroupId);

    HttpRequest request = HttpUtils.buildPostRequest(
        getInstantiateComponentUri(resourceGroupId, liveSystemName, componentId),
        sdkConfiguration, null); // Assuming no request body needed for instantiation

    return executeRequestWithRetries(
        "instantiateComponent",
        client,
        retryRegistry,
        request,
        new int[]{200, 404}, // Accepted status code
        LiveSystemComponentMutationDto.class);
  }

  public LiveSystemComponentMutationDto getComponentMutationStatus(
      String liveSystemId,
      String componentId,
      String mutationId) throws InstantiatorException {
    var requestName = "getComponentMutationStatus";

    var acceptedResponses = new int[]{200, 404};

    var request = HttpUtils.buildGetRequest(
        getComponentStateUri(liveSystemId, componentId, mutationId),
        sdkConfiguration);

    var retryConfig = RetryConfig.custom()
        .retryExceptions(InstantiatorException.class)
        .maxAttempts(CHECK_LIVE_SYSTEM_MUTATION_STATUS_MAX_ATTEMPTS)
        .waitDuration(Duration.ofSeconds(30L))
        .build();

    var retry = RetryRegistry.of(retryConfig).retry(requestName);

    try {
      return Retry.decorateCheckedSupplier(retry, () ->
          getComponentMutationStatus(
              liveSystemId,
              componentId,
              mutationId,
              requestName,
              acceptedResponses,
              request)).get();
    } catch (Throwable ex) {
      throw new InstantiatorException(ex.getLocalizedMessage());
    }
  }

  private LiveSystemComponentMutationDto getComponentMutationStatus(
      String liveSystemId,
      String componentId,
      String mutationId,
      String requestName,
      int[] acceptedResponses,
      HttpRequest request) throws IOException, InterruptedException, InstantiatorException, ProviderException, ComponentInstantiationException {

    if (EnvVarUtils.isLocalDebug()) {
      request = LocalDebugUtils.getHttpRequestBuilder(request.uri(), sdkConfiguration)
          .build();
    }

    var response = client.send(request, HttpResponse.BodyHandlers.ofString());
    ensureAcceptableResponse(response, requestName, acceptedResponses);

    if (response.statusCode() == 404) {
      throw new ComponentInstantiationException(
          String.format(
              "Component [id: '%s'] not found [LiveSystemId: '%s', MutationId: '%s']",
              componentId, liveSystemId, mutationId));
    }

    String bodyContents = response.body();
    if (isBlank(bodyContents)) {
      throw new ComponentInstantiationException(
          String.format(
              "Unexpected empty or null response body for component [id: '%s'] in LiveSystem [id: '%s']. Status code: %d.",
              componentId, liveSystemId, response.statusCode()));
    }

    var componentMutationDto = getLiveSystemComponentMutationResponse(requestName, bodyContents);

    if (componentMutationDto == null) {
      String message = String.format("The LiveSystem [%s] exists, but the LiveSystem's provider LiveSystem is still empty or not fully updated for mutation [%s]",
          liveSystemId, mutationId);
      log.info(message);
      throw new InstantiatorException(message);
    }

    var component = componentMutationDto.getComponent();

    switch (component.getStatus()) {
      case Active -> {
        log.info("Instantiation completed [LiveSystemId: '{}', ComponentId: '{}']", liveSystemId, component.getId());

        return componentMutationDto;
      }
      case Failed -> {
        log.warn("Instantiation failed [LiveSystemId: '{}', ComponentId: '{}']", liveSystemId, componentId);
        throw new ProviderException(component.getLastOperationStatusMessage());
      }
      case Instantiating -> {
        log.info("Instantiation is in progress [LiveSystemId: '{}', ComponentId: '{}']", liveSystemId, componentId);
        String timeoutExceptionMessage = String.format(
            "Instantiation wait timeout [LiveSystemId: '%s', ComponentId: '%s']. Response is %s",
            liveSystemId,
            componentId,
            response.body());
        throw new InstantiatorException(timeoutExceptionMessage);
      }
      default ->
          throw new ProviderException(String.format("Instantiation has an unknown state '%s' [LiveSystemId: '%s', ComponentId: '%s', MutationId: '%s']",
              component.getStatus(),
              liveSystemId,
              componentId,
              mutationId));
    }
  }

  private URI getInstantiateComponentUri(String resourceGroupId, String liveSystemName, String componentId) {
    return URI.create(String.format(
        "%s/%s/%s/component/%s/instantiate",
        getLiveSystemUri(), resourceGroupId, liveSystemName, componentId));
  }

  private URI getComponentStateUri(String liveSystemId, String componentId, String mutationId) {
    return URI.create(String.format(
        "%s/%s/component/%s/state/%s",
        getLiveSystemUri(), liveSystemId, componentId, mutationId));
  }

  private static LiveSystemDto getDeserializedResponseBody(String bodyContents) throws InstantiatorException {
    try {
      return deserialize(bodyContents, LiveSystemDto.class);
    } catch (JsonProcessingException e) {
      throw new InstantiatorException(
          String.format("Attempted Retrieve LiveSystem failed. Deserialization of %s failed.", bodyContents),
          e);
    }
  }

  private LiveSystemMutationDto updateLiveSystem(UpdateLiveSystemCommandRequest command) throws InstantiatorException {

    HttpRequest request;
    try {
      String serializedCommand = serialize(command);
      log.info("Update LiveSystem message: {}", serializedCommand);
      request = HttpUtils.buildPutRequest(
          getLiveSystemUri(new LiveSystemId(command.getLiveSystemId())), sdkConfiguration, serializedCommand);
    } catch (JsonProcessingException e) {
      throw new InstantiatorException("Error processing UpdateLiveSystemCommandRequest because of JsonProcessing", e);
    }

    HttpResponse<String> response;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new InstantiatorException("Attempted LiveSystem update failed with generic exception", e);
    }

    if (response.statusCode() != 202) {
      throw new InstantiatorException(
          String.format(
              "Attempted UpdateLiveSystemCommandRequest failed [response code: '%s', message: '%s']",
              response.statusCode(),
              response.body()));
    }

    try {
      return deserialize(response.body(), LiveSystemMutationDto.class);
    } catch (Exception e) {
      throw new InstantiatorException("Could not deserialize the mutation on LiveSystem update", e);
    }

  }

  private LiveSystemMutationDto instantiateLiveSystem(InstantiateLiveSystemCommandRequest command) throws InstantiatorException {
    HttpRequest request;
    try {
      String serializedCommand = serialize(command);
      log.info("Instantiate LiveSystem message: {}", serializedCommand);

      var liveSystemUri = URI.create(String.format("%s/", getLiveSystemUri()));
      request = HttpUtils.buildPostRequest(liveSystemUri, sdkConfiguration, serializedCommand);
    } catch (JsonProcessingException e) {
      throw new InstantiatorException("Error processing InstantiateLiveSystemCommandRequest because of JsonProcessing", e);
    }

    return executeRequestWithRetries(
        "instantiate",
        client,
        retryRegistry,
        request,
        new int[]{200},
        LiveSystemMutationDto.class);
  }

  private URI getLiveSystemUri(LiveSystemId liveSystemId) {
    return URI.create(String.format("%s/%s", getLiveSystemUri(), liveSystemId.toString()));
  }

  private URI getLiveSystemUri() {
    return sdkConfiguration.getLiveSystemEndpoint();
  }

  private URI getLiveSystemMutationUri(LiveSystemId liveSystemId, String mutationId) {
    return URI.create(String.format("%s/mutations/%s", getLiveSystemUri(liveSystemId), mutationId));
  }

  private String getCommandAsJsonString(InstantiateLiveSystemCommandRequest command) throws InstantiatorException {
    try {
      return serialize(command);
    } catch (JsonProcessingException e) {
      var errorMessage = String.format("Unable to serialize Instantiate LiveSystem Command Request. %s",
          e.getLocalizedMessage());

      log.error(errorMessage, e);
      throw new InstantiatorException(errorMessage, e);
    }
  }
}
