package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.providers.dtos.ProviderLiveSystemDto;
import com.yanchware.fractal.sdk.services.contracts.providers.responses.CurrentLiveSystemsResponse;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_ID_HEADER;
import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_SECRET_HEADER;
import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentStatusDto.Active;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.deserialize;

@Slf4j
@AllArgsConstructor
public class ProviderService {

  private final HttpClient client;
  private final SdkConfiguration sdkConfiguration;
  private final RetryRegistry retryRegistry;

  public ProviderLiveSystemDto getLiveSystem(String liveSystemId, String resourceGroup) throws InstantiatorException {
    CurrentLiveSystemsResponse allLiveSystemsFromProviders = getLiveSystems(resourceGroup);
    if (allLiveSystemsFromProviders == null || CollectionUtils.isBlank(allLiveSystemsFromProviders.getLiveSystems())) {
      log.info("No live systems found for resource group [{}]", resourceGroup);
      return null;
    }

    var optionalProviderLiveSystemDto = allLiveSystemsFromProviders.getLiveSystems().stream().filter(x -> x.getId().equalsIgnoreCase(liveSystemId)).findFirst();

    if(optionalProviderLiveSystemDto.isEmpty()) {
      log.info("No live systems found to match id [{}]", liveSystemId);
      return null;
    }

    var liveSystem = optionalProviderLiveSystemDto.get();
    for(var component : liveSystem.getComponents()) {
      log.info("Component [{}] has status [{}]", component.getId(), component.getStatus());
      if (component.getStatus() != Active) {
        log.info("As status is not Active, retry operation starts.");
      }
    }

    return liveSystem;
  }

  private CurrentLiveSystemsResponse getLiveSystems(String resourceGroup) throws InstantiatorException {
    log.info("Starting operation [getLiveSystems] for resource group [{}]", resourceGroup);

    HttpRequest request = HttpRequest.newBuilder()
        .uri(getProvidersUri(resourceGroup))
        .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
        .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
        .header("Content-Type", "application/json")
        .GET()
        .build();

    HttpResponse<String> response;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      log.error("Unexpected exception when retrieving livesystems for resource group: [{}]", resourceGroup, e);
      throw new InstantiatorException(
          String.format("Attempted retrieval of LiveSystems from Providers for resource group [%s] failed with generic exception.", resourceGroup),
          e);
    }

    String bodyContents = response.body();
    try {
      return deserialize(bodyContents, CurrentLiveSystemsResponse.class);
    } catch (JsonProcessingException e) {
      log.error("Unable to deserialize content: [{}]", bodyContents, e);
      throw new InstantiatorException(
          String.format("Attempted retrieval of LiveSystems from Providers for resource group [%s] failed. Deserialization of %s failed.", resourceGroup, bodyContents),
          e);
    }
  }

  private URI getProvidersUri(String resourceGroup) {
    return URI.create(String.format("%s/%s/liveSystems", sdkConfiguration.getProviderEndpoint(), resourceGroup));
  }
}
