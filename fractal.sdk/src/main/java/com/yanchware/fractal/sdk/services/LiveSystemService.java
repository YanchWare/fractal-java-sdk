package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.InstantiateLiveSystemCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.UpdateLiveSystemCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemDto;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemMutationDto;
import com.yanchware.fractal.sdk.utils.HttpUtils;
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

    public LiveSystemMutationDto instantiate(InstantiateLiveSystemCommandRequest command) throws InstantiatorException {
        log.info("Starting to instantiate live system: {}", command);

        if (retrieveLiveSystem(command.getLiveSystemId()) != null) {
            return updateLiveSystem(UpdateLiveSystemCommandRequest.fromInstantiateCommand(command));
        }
        return instantiateLiveSystem(command);
    }

    public LiveSystemDto retrieveLiveSystem(String liveSystemId) throws InstantiatorException {
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
            log.info("LiveSystem with id {} does not exist. Will attempt to instantiate it.", liveSystemId);
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

    private LiveSystemMutationDto updateLiveSystem(UpdateLiveSystemCommandRequest command) throws InstantiatorException {
        HttpRequest request;
        try {
            String serializedCommand = serialize(command);
            log.debug("Update LiveSystem message: {}", serializedCommand);
            request = HttpUtils.buildPutRequest(
                getLiveSystemUri(command.getLiveSystemId()), sdkConfiguration, serializedCommand);
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

        try {
            var mutation = deserialize(response.body(), LiveSystemMutationDto.class);
            return mutation;
        }
        catch (Exception e) {
            throw new InstantiatorException("Could not deserialize the mutation on LiveSystem update", e);
        }

    }

    private LiveSystemMutationDto instantiateLiveSystem(InstantiateLiveSystemCommandRequest command) throws InstantiatorException {
        HttpRequest request;
        try {
            String serializedCommand = serialize(command);
            log.debug("Instantiate LiveSystem message: {}", serializedCommand);
            request = HttpUtils.buildPostRequest(getLiveSystemUri(), sdkConfiguration, serializedCommand);
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

    private URI getLiveSystemUri(String liveSystemId) {
        return URI.create(String.format("%s%s", getLiveSystemUri(), liveSystemId));
    }

    private URI getLiveSystemUri() {
        return URI.create(sdkConfiguration.getLiveSystemEndpoint() + "/");
    }
}
