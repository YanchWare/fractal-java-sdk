package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.services.livesystemcontract.command.InstantiateCommandRequest;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpRequest;

import static com.yanchware.fractal.sdk.configuration.Constants.ENDPOINT;
import static java.net.http.HttpRequest.BodyPublishers.ofString;

@Slf4j
public class LiveSystemService {
    /*public LiveSystemDto Retrieve(RetrieveQuery query) {

    }

    public void Update(UpdateCommand command) {

    }*/

    public void instantiate(InstantiateCommandRequest command) {
        var objectMapper = new ObjectMapper();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ENDPOINT + "/" + "resource-group-id" + "/livesystems"))
                    .header("X-ClientID", "clientId")
                    .header("X-ClientSecret", "clientSecret")
                    .POST(ofString(objectMapper.writeValueAsString(command)))
                    .build();
        } catch (JsonProcessingException e) {
            log.error("Error processing command: {}", command, e);
        }
        throw new UnsupportedOperationException("Instantiate not implemented");
    }
}
