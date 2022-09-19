package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.CreateBlueprintCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.UpdateBlueprintCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos.BlueprintDto;
import com.yanchware.fractal.sdk.utils.HttpUtils;
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
public class BlueprintService {

    private final HttpClient client;
    private final SdkConfiguration sdkConfiguration;
    private final RetryRegistry retryRegistry;

    public void createOrUpdateBlueprint(CreateBlueprintCommandRequest command, String fractalId) throws InstantiatorException {
        log.debug("Create or update blueprint: {}", command);
        if (retrieveBlueprint(fractalId) != null) {
            updateBlueprint(UpdateBlueprintCommandRequest.fromCreateCommand(command, fractalId), fractalId);
            return;
        }
        createBlueprint(command, fractalId);
    }

    protected void createBlueprint(CreateBlueprintCommandRequest command, String fractalId) throws InstantiatorException {
        HttpRequest request;
        try {
            request = HttpUtils.buildPostRequest(getBlueprintsUri(fractalId), sdkConfiguration, serialize(command));
        } catch (JsonProcessingException e) {
            throw new InstantiatorException("Error processing CreateBlueprintCommandRequest because of JsonProcessing", e);
        }

        executeRequestWithRetries("createBlueprint", client, retryRegistry, request, new int[] { 202 });
    }

    protected void updateBlueprint(UpdateBlueprintCommandRequest command, String fractalId) throws InstantiatorException {
        HttpRequest request;
        try {
            request = HttpUtils.buildPutRequest(getBlueprintsUri(fractalId), sdkConfiguration, serialize(command));
        } catch (JsonProcessingException e) {
            throw new InstantiatorException("Error processing UpdateBlueprintCommandRequest because of JsonProcessing", e);
        }

        executeRequestWithRetries("updateBlueprint", client, retryRegistry, request, new int[] { 202 });
    }

    protected BlueprintDto retrieveBlueprint(String fractalId) throws InstantiatorException {

        return executeRequestWithRetries(
          "retrieveBlueprint",
          client,
          retryRegistry,
          HttpUtils.buildGetRequest(getBlueprintsUri(fractalId), sdkConfiguration),
          new int[] { 200, 404 },
          BlueprintDto.class);
    }

    private URI getBlueprintsUri(String fractalId) {
        return URI.create(sdkConfiguration.getBlueprintEndpoint() + "/" + fractalId.replace(":", "/"));
    }
}
