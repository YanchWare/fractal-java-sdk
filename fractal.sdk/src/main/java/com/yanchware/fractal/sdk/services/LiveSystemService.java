package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.configuration.EnvVarServiceConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.InstantiateLiveSystemCommandRequest;
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
public class LiveSystemService {

    private final HttpClient client;

    private final EnvVarServiceConfiguration envVarServiceConfiguration;

    /*public LiveSystemDto Retrieve(RetrieveQuery query) {

    }

    public void Update(UpdateCommand command) {

    }*/

    public void instantiate(InstantiateLiveSystemCommandRequest command) throws InstantiatorException {
        var objectMapper = new ObjectMapper();

        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(ENDPOINT + "/" + envVarServiceConfiguration.getResourceGroupId() + "/livesystems"))
                    .header(X_CLIENT_ID_HEADER, envVarServiceConfiguration.getClientId())
                    .header(X_CLIENT_SECRET_HEADER, envVarServiceConfiguration.getClientSecret())
                    .POST(ofString(objectMapper.writeValueAsString(command)))
                    .build();
        } catch (JsonProcessingException e) {
            log.error("Error processing command: {}", command, e);
            throw new InstantiatorException("Attempted instantiation because of JsonProcessing", e);
        }

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.error("Attempted instantiation of livesystem for resourceGroupId: {}, but received status {}", envVarServiceConfiguration.getResourceGroupId(), response.statusCode());
                throw new InstantiatorException("Attempted instantiation with response code: " + response.statusCode());
            }
        } catch (Exception e) {
            log.error("Attempted instantiation of livesystem failed", e);
            throw new InstantiatorException("Attempted instantiation with generic exception", e);
        }

        throw new UnsupportedOperationException("Instantiate not implemented");
    }
}
