package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands.CreateEnvironmentRequest;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands.UpdateEnvironmentRequest;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands.dtos.EnvironmentResponse;
import com.yanchware.fractal.sdk.utils.HttpUtils;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;

import static com.yanchware.fractal.sdk.utils.ResiliencyUtils.executeRequestWithRetries;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;

@Slf4j
@AllArgsConstructor
public class EnvironmentsService {
  private final HttpClient client;
  private final SdkConfiguration sdkConfiguration;
  private final RetryRegistry retryRegistry;

  public void createOrUpdateEnvironment(Environment environment) throws InstantiatorException {
    if (fetchEnvironment(environment) != null) {
      updateEnvironment(environment);
      return;
    }

    createEnvironment(environment);
  }

  protected EnvironmentResponse fetchEnvironment(Environment environment) throws InstantiatorException {
    return executeRequestWithRetries(
        "fetchEnvironment",
        client,
        retryRegistry,
        HttpUtils.buildGetRequest(getEnvironmentsUri(environment), sdkConfiguration),
        new int[]{200, 404},
        EnvironmentResponse.class);
  }

  protected EnvironmentResponse createEnvironment(Environment environment) throws InstantiatorException {
    var payload = getPayload(environment, "CREATE");

    return executeRequestWithRetries(
        "createEnvironment",
        client,
        retryRegistry,
        HttpUtils.buildPostRequest(getEnvironmentsUri(environment), sdkConfiguration, payload),
        new int[]{201},
        EnvironmentResponse.class);
  }

  protected EnvironmentResponse updateEnvironment(Environment environment) throws InstantiatorException {
    var payload = getPayload(environment, "UPDATE");

    return executeRequestWithRetries(
        "updateEnvironment",
        client,
        retryRegistry,
        HttpUtils.buildPutRequest(getEnvironmentsUri(environment), sdkConfiguration, payload),
        new int[]{200},
        EnvironmentResponse.class);
  }

  private String getPayload(Environment environment, String requestType) throws InstantiatorException {
    try {
      return switch (requestType) {
        case "CREATE" -> serialize(CreateEnvironmentRequest.fromEnvironment(environment));
        case "UPDATE" -> serialize(UpdateEnvironmentRequest.fromEnvironment(environment));
        default -> throw new IllegalArgumentException("Unknown request type: " + requestType);
      };
    } catch (JsonProcessingException e) {
      throw new InstantiatorException("Error processing " + requestType + " request because of JsonProcessing", e);
    }
  }

  private URI getEnvironmentsUri(Environment environment) {
    return URI.create(sdkConfiguration.getEnvironmentsEndpoint() 
        + "/" + environment.getEnvironmentType()
        + "/" + environment.getOwnerId()
        + "/" + environment.getShortName());
  }
}
