package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.configuration.EnvVarServiceConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.CreateBlueprintCommandRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.yanchware.fractal.sdk.configuration.Constants.*;
import static java.net.http.HttpRequest.BodyPublishers.ofString;

@Data
@Slf4j
public class BlueprintService {

    private final HttpClient client;

    private final EnvVarServiceConfiguration envVarServiceConfiguration;

    public void instantiate(CreateBlueprintCommandRequest command, String fractalName, String fractalVersion) throws InstantiatorException {
        var objectMapper = new ObjectMapper();

        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(getBlueprintsUri(fractalName, fractalVersion))
                    .header(X_CLIENT_ID_HEADER, envVarServiceConfiguration.getClientId())
                    .header(X_CLIENT_SECRET_HEADER, envVarServiceConfiguration.getClientSecret())
                    .POST(ofString(objectMapper.writeValueAsString(command)))
                    .build();
        } catch (JsonProcessingException e) {
            log.error("Error processing CreateBlueprintCommandRequest: {}", command, e);
            throw new InstantiatorException("Error processing CreateBlueprintCommandRequest because of JsonProcessing", e);
        }

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.error("Attempted CreateBlueprintCommandRequest for resourceGroupId: {}, but received status {}", envVarServiceConfiguration.getResourceGroupId(), response.statusCode());
                throw new InstantiatorException("Attempted CreateBlueprintCommandRequest with response code: " + response.statusCode());
            }
        } catch (Exception e) {
            log.error("Attempted CreateBlueprintCommandRequest failed", e);
            throw new InstantiatorException("Attempted CreateBlueprintCommandRequest with generic exception", e);
        }
    }

    private URI getBlueprintsUri(String fractalName, String fractalVersion) {
        return URI.create(BLUEPRINTS_ENDPOINT + "/" + envVarServiceConfiguration.getResourceGroupId() + "/" + fractalName + "/" + fractalVersion);
    }
}
