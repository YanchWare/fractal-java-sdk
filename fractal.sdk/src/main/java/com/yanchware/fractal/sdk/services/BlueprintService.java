package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.CreateBlueprintCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.UpdateBlueprintCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos.BlueprintDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_ID_HEADER;
import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_SECRET_HEADER;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;
import static java.net.http.HttpRequest.BodyPublishers.ofString;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.deserialize;

@Slf4j
@AllArgsConstructor
public class BlueprintService {

    private final HttpClient client;
    private final SdkConfiguration sdkConfiguration;

    public void createOrUpdateBlueprint(CreateBlueprintCommandRequest command, String fractalId) throws InstantiatorException {
        if (retrieveBlueprint(fractalId) != null) {
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
              .header("content-type", "application/json")
              .POST(ofString(serialize(command)))
              .build();
        } catch (JsonProcessingException e) {
            throw new InstantiatorException("Error processing CreateBlueprintCommandRequest because of JsonProcessing", e);
        }

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new InstantiatorException("Attempted CreateBlueprintCommandRequest failed", e);
        }

        if (response.statusCode() != 202) {
            throw new InstantiatorException(
              String.format(
                "Attempted CreateBlueprintCommandRequest failed with response code: %s and body %s ",
                response.statusCode(),
                response.body()));
        }
    }

    protected void updateBlueprint(UpdateBlueprintCommandRequest command, String fractalId) throws InstantiatorException {
        HttpRequest updateRequest;
        try {
            updateRequest = HttpRequest.newBuilder()
              .uri(getBlueprintsUri(fractalId))
              .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
              .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
              .header("content-type", "application/json")
              .PUT(ofString(serialize(command)))
              .build();
        } catch (JsonProcessingException e) {
            throw new InstantiatorException("Error processing UpdateBlueprintCommandRequest because of JsonProcessing", e);
        }

        HttpResponse<String> response;
        try {
            response = client.send(updateRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new InstantiatorException("Attempted UpdateBlueprintCommandRequest failed.", e);
        }

        if (response.statusCode() != 202) {
            throw new InstantiatorException(
              String.format(
                "Attempted UpdateBlueprintCommandRequest with response code: %s and body %s ",
                response.statusCode(),
                response.body()));
        }
    }

    protected BlueprintDto retrieveBlueprint(String fractalId) throws InstantiatorException {
        HttpRequest request = HttpRequest.newBuilder()
          .uri(getBlueprintsUri(fractalId))
          .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
          .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
          .GET()
          .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new InstantiatorException("Attempted Retrieve Blueprint failed", e);
        }

        if (response.statusCode() == 404) {
            log.info("Blueprint with id {} does not exist", fractalId);
            return null;
        }

        if (response.statusCode() != 200) {
            throw new InstantiatorException(
              String.format(
                "Attempted UpdateBlueprintCommandRequest with response code: %s and body %s ",
                response.statusCode(),
                response.body()));
        }

        String bodyContents = response.body();
        try {
            return deserialize(bodyContents, BlueprintDto.class);
        } catch (JsonProcessingException e) {
            throw new InstantiatorException(
              String.format("Attempted Retrieve Blueprint failed. Deserialization of %s failed.", bodyContents),
              e);
        }
    }

    private URI getBlueprintsUri(String fractalId) {
        log.info("BLUEPRINT URI: {}", URI.create(sdkConfiguration.getBlueprintEndpoint() + "/" + fractalId.replace(":", "/")));
        return URI.create(sdkConfiguration.getBlueprintEndpoint() + "/" + fractalId.replace(":", "/"));
    }
}
