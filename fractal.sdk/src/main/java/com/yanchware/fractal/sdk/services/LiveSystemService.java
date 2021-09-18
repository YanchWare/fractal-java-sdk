package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.InstantiateLiveSystemCommandRequest;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_ID_HEADER;
import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_SECRET_HEADER;
import static com.yanchware.fractal.sdk.utils.ResiliencyUtils.executeRequestWithRetries;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;
import static java.net.http.HttpRequest.BodyPublishers.ofString;

@Slf4j
@AllArgsConstructor
public class LiveSystemService {

    private final HttpClient client;
    private final SdkConfiguration sdkConfiguration;
    private final RetryRegistry retryRegistry;

    public void instantiate(InstantiateLiveSystemCommandRequest command) throws InstantiatorException {

        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
              .uri(getLiveSystemUri())
              .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
              .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
              .header("content-type", "application/json")
              .POST(ofString(serialize(command)))
              .build();
        } catch (JsonProcessingException e) {
            throw new InstantiatorException("Error processing InstantiateLiveSystemCommandRequest because of JsonProcessing", e);
        }

        executeRequestWithRetries("instantiate", client, retryRegistry, request, new int[] { 200 });
    }

    private URI getLiveSystemUri() {
        log.info("LIVESYSTEM URI: {}", URI.create(sdkConfiguration.getLiveSystemEndpoint()));
        return URI.create(sdkConfiguration.getLiveSystemEndpoint() + "/");
    }
}
