package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.InstantiateLiveSystemCommandRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_ID_HEADER;
import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_SECRET_HEADER;
import static java.net.http.HttpRequest.BodyPublishers.ofString;

@Data
@Slf4j
public class LiveSystemService {

    private final HttpClient client;

    private final SdkConfiguration sdkConfiguration;

    public void instantiate(InstantiateLiveSystemCommandRequest command) throws InstantiatorException {
        var objectMapper = new ObjectMapper();

        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(getLiveSystemUri())
                    .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
                    .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
                    .POST(ofString(objectMapper.writeValueAsString(command)))
                    .build();
        } catch (JsonProcessingException e) {
            log.error("Error processing InstantiateLiveSystemCommandRequest: {}", command, e);
            throw new InstantiatorException("Error processing InstantiateLiveSystemCommandRequest because of JsonProcessing", e);
        }

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.error("Attempted instantiation of livesystem for resourceGroupId: {}, but received status {}", sdkConfiguration.getResourceGroupId(), response.statusCode());
                throw new InstantiatorException("Attempted instantiation with response code: " + response.statusCode());
            }
        } catch (Exception e) {
            log.error("Attempted instantiation of livesystem failed", e);
            throw new InstantiatorException("Attempted instantiation with generic exception", e);
        }
    }

    private URI getLiveSystemUri() {
        return URI.create(sdkConfiguration.getLiveSystemEndpoint() + "/" + sdkConfiguration.getResourceGroupId() + "/livesystems");
    }
}
