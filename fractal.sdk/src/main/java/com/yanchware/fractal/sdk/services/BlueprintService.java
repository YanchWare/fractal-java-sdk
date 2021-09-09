package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.CreateBlueprintCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.UpdateBlueprintCommandRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_ID_HEADER;
import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_SECRET_HEADER;
import static java.net.http.HttpRequest.BodyPublishers.ofString;

@AllArgsConstructor
@Slf4j
public class BlueprintService {

    private final HttpClient client;

    private final SdkConfiguration sdkConfiguration;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void createOrUpdateBlueprint(CreateBlueprintCommandRequest command, String fractalId) throws InstantiatorException {
        if (retrieveBlueprint(fractalId)) {
            updateBlueprint(UpdateBlueprintCommandRequest.fromCreateCommand(command, fractalId), fractalId);
            return;
        }
        createBlueprint(command, fractalId);
    }

    protected void createBlueprint(CreateBlueprintCommandRequest command, String fractalId) throws InstantiatorException {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(getBlueprintsUri(fractalId))
                    .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
                    .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
                    .POST(ofString(objectMapper.writeValueAsString(command)))
                    .build();
        } catch (JsonProcessingException e) {
            log.error("Error processing CreateBlueprintCommandRequest: {}", command, e);
            throw new InstantiatorException("Error processing CreateBlueprintCommandRequest because of JsonProcessing", e);
        }

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 202) {
                log.error("Attempted CreateBlueprintCommandRequest for resourceGroupId: {}, but received status {}", sdkConfiguration.getResourceGroupId(), response.statusCode());
                throw new InstantiatorException("Attempted CreateBlueprintCommandRequest with response code: " + response.statusCode());
            }
        } catch (Exception e) {
            log.error("Attempted CreateBlueprintCommandRequest failed", e);
            throw new InstantiatorException("Attempted CreateBlueprintCommandRequest with generic exception", e);
        }
    }

    protected void updateBlueprint(UpdateBlueprintCommandRequest command, String fractalId) throws InstantiatorException {
        HttpRequest updateRequest;
        try {
            updateRequest = HttpRequest.newBuilder()
                    .uri(getBlueprintsUri(fractalId))
                    .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
                    .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
                    .PUT(ofString(objectMapper.writeValueAsString(command)))
                    .build();
        } catch (JsonProcessingException e) {
            log.error("Error processing UpdateBlueprintCommandRequest: {}", command, e);
            throw new InstantiatorException("Error processing UpdateBlueprintCommandRequest because of JsonProcessing", e);
        }

        try {
            HttpResponse<String> response = client.send(updateRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.error("Attempted UpdateBlueprintCommandRequest for resourceGroupId: {}, but received status {}", sdkConfiguration.getResourceGroupId(), response.statusCode());
                throw new InstantiatorException("Attempted UpdateBlueprintCommandRequest with response code: " + response.statusCode());
            }
        } catch (Exception e) {
            log.error("Attempted UpdateBlueprintCommandRequest failed", e);
            throw new InstantiatorException("Attempted UpdateBlueprintCommandRequest with generic exception", e);
        }
    }

    protected boolean retrieveBlueprint(String fractalId) throws InstantiatorException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(getBlueprintsUri(fractalId))
                .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
                .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.error("Attempted Retrieve Blueprint for fractalId: {}, but received status {}", fractalId, response.statusCode());
                throw new InstantiatorException("Attempted Retrieve Blueprint with code: " + response.statusCode());
            }
        } catch (Exception e) {
            log.error("Attempted Retrieve Blueprint for fractalId: {}, but received status {}", fractalId, e);
            throw new InstantiatorException("Attempted Retrieve Blueprint with generic exception", e);
        }
        return true;
    }

    private URI getBlueprintsUri(String fractalId) {
        log.info("BLUEPRINT URI: {}", URI.create(sdkConfiguration.getBlueprintEndpoint() + "/" + fractalId.replace(":", "/")));
        return URI.create(sdkConfiguration.getBlueprintEndpoint() + "/" + fractalId.replace(":", "/"));
    }
}
