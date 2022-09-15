package com.yanchware.fractal.sdk.services;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
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

@Slf4j
@AllArgsConstructor
public class ProviderService {

  private final HttpClient client;
  private final SdkConfiguration sdkConfiguration;
  private final RetryRegistry retryRegistry;

  public void getLiveSystems(String resourceGroup) {
    log.info("Starting operation [getLiveSystems] for resource group [{}]", resourceGroup);

    HttpRequest request = HttpRequest.newBuilder()
        .uri(getProvidersUri(resourceGroup))
        .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
        .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
        .GET()
        .build();

    HttpResponse<String> response;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      log.error("Error", e);
    }
  }

  private URI getProvidersUri(String resourceGroup) {
    return URI.create("");
  }
}
