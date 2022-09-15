package com.yanchware.fractal.sdk.services;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.providers.responses.CurrentLiveSystemsResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_ID_HEADER;
import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_SECRET_HEADER;
import static com.yanchware.fractal.sdk.utils.ResiliencyUtils.executeRequestWithRetries;

@Slf4j
@AllArgsConstructor
public class ProviderService {

  private final HttpClient client;
  private final SdkConfiguration sdkConfiguration;

  public void checkLiveSystem(String liveSystemId, String resourceGroup) throws InstantiatorException {
    log.info("Starting operation [getLiveSystems] for resource group [{}]", resourceGroup);

    HttpRequest request = HttpRequest.newBuilder()
        .uri(getProvidersUri(resourceGroup))
        .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
        .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
        .header("Content-Type", "application/json")
        .GET()
        .build();

    CurrentLiveSystemsResponse liveSystemsResponse = executeRequestWithRetries(
        "getLiveSystems",
        client,
        request,
        new int[]{200, 404},
        CurrentLiveSystemsResponse.class,
        liveSystemId);
  }

  private URI getProvidersUri(String resourceGroup) {
    return URI.create(String.format("%s/%s/liveSystems", sdkConfiguration.getProviderEndpoint(), resourceGroup));
  }
}
