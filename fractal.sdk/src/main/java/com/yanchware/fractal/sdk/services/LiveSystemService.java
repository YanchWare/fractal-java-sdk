package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.InstantiateLiveSystemCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.UpdateLiveSystemCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemDto;
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
import static com.yanchware.fractal.sdk.utils.ResiliencyUtils.executeRequestWithRetries;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.deserialize;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;
import static java.net.http.HttpRequest.BodyPublishers.ofString;

@Slf4j
@AllArgsConstructor
public class LiveSystemService {

    private final HttpClient client;
    private final SdkConfiguration sdkConfiguration;
    private final RetryRegistry retryRegistry;

    public void instantiate(InstantiateLiveSystemCommandRequest command) throws InstantiatorException {
        log.debug("Starting to instantiate live system: {}", command);

        if (retrieveLiveSystem(command.getLiveSystemId()) != null) {
            updateLiveSystem(UpdateLiveSystemCommandRequest.fromInstantiateCommand(command));
            return;
        }
        instantiateLiveSystem(command);
    }

    private LiveSystemDto retrieveLiveSystem(String liveSystemId) throws InstantiatorException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(getLiveSystemUri(liveSystemId))
                .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
                .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new InstantiatorException("Attempted Retrieve LiveSystem failed", e);
        }

        if (response.statusCode() == 404) {
            log.info("LiveSystem with id {} does not exist", liveSystemId);
            return null;
        }

        if (response.statusCode() != 200) {
            throw new InstantiatorException(
                    String.format(
                            "Attempted Retrieve LiveSystem with response code: %s and body %s ",
                            response.statusCode(),
                            response.body()));
        }

        String bodyContents = response.body();
        try {
            return deserialize(bodyContents, LiveSystemDto.class);
        } catch (JsonProcessingException e) {
            throw new InstantiatorException(
                    String.format("Attempted Retrieve LiveSystem failed. Deserialization of %s failed.", bodyContents),
                    e);
        }
    }

    private void updateLiveSystem(UpdateLiveSystemCommandRequest command) throws InstantiatorException {
        HttpRequest request;
        try {
            String serializedCommand = serialize(command);
            log.debug("Update LIveSystem message: {}", serializedCommand);
            request = HttpRequest.newBuilder()
                    .uri(getLiveSystemUri(command.getLiveSystemId()))
                    .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
                    .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
                    .header("content-type", "application/json")
                    .PUT(ofString(serializedCommand))
                    .build();
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
                            "Attempted UpdateLiveSystemCommandRequest failed with response code: %s and body %s ",
                            response.statusCode(),
                            response.body()));
        }
    }

    private void instantiateLiveSystem(InstantiateLiveSystemCommandRequest command) throws InstantiatorException {
        HttpRequest request;
        try {
            String serializedCommand = serialize(command);
            log.debug("Instantiate LIveSystem message: {}", serializedCommand);
            request = HttpRequest.newBuilder()
                    .uri(getLiveSystemUri())
                    .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
                    .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
                    .header("content-type", "application/json")
                    .POST(ofString(serializedCommand))
                    .build();
        } catch (JsonProcessingException e) {
            throw new InstantiatorException("Error processing InstantiateLiveSystemCommandRequest because of JsonProcessing", e);
        }

        executeRequestWithRetries("instantiate", client, retryRegistry, request, new int[]{200});
    }

    private URI getLiveSystemUri(String liveSystemId) {
        return URI.create(String.format("%s%s", getLiveSystemUri(), liveSystemId));
    }

    private URI getLiveSystemUri() {
        return URI.create(sdkConfiguration.getLiveSystemEndpoint() + "/");
    }
}
