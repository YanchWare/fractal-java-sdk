package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands.CreateEnvironmentRequest;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands.SubscriptionInitializationRequest;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands.UpdateEnvironmentRequest;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.dtos.EnvironmentResponse;
import com.yanchware.fractal.sdk.utils.HttpUtils;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

import static com.yanchware.fractal.sdk.configuration.Constants.*;
import static com.yanchware.fractal.sdk.utils.ResiliencyUtils.executeRequestWithRetries;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@AllArgsConstructor
public class EnvironmentsService {
  private final HttpClient client;
  private final SdkConfiguration sdkConfiguration;
  private final RetryRegistry retryRegistry;

  public EnvironmentResponse createOrUpdateEnvironment(Environment environment) throws InstantiatorException {
    if (fetchEnvironment(environment) != null) {
      return updateEnvironment(environment);
    }

    return createEnvironment(environment);
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

  public void InitializeSubscription(Environment environment) throws InstantiatorException {
    var payload = getPayload(environment, "START_INITIALIZATION");

    var azureSpClientId = sdkConfiguration.getAzureSpClientId();
    if (isBlank(azureSpClientId)) {
      throw new IllegalArgumentException(
          String.format("The environment variable %s is required and it has not been defined", AZURE_SP_CLIENT_ID_KEY));
    }

    var azureSpClientSecret = sdkConfiguration.getAzureSpClientSecret();
    if (isBlank(azureSpClientId)) {
      throw new IllegalArgumentException(
          String.format("The environment variable %s is required and it has not been defined", AZURE_SP_CLIENT_SECRET_KEY));
    }

    Map<String, String> additionalHeaders = new HashMap<>();
    additionalHeaders.put(X_AZURE_SP_CLIENT_ID_HEADER, azureSpClientId);
    additionalHeaders.put(X_AZURE_SP_CLIENT_SECRET_HEADER, azureSpClientSecret);

    executeRequestWithRetries(
        "InitializeSubscription",
        client,
        retryRegistry,
        HttpUtils.buildPostRequest(getEnvironmentsUri(environment, "initializer/azure/initialize"),
            sdkConfiguration,
            payload,
            additionalHeaders),
        new int[]{202},
        EnvironmentResponse.class);
  }

  private String getPayload(Environment environment, String requestType) throws InstantiatorException {
    try {
      return switch (requestType) {
        case "CREATE" -> serialize(CreateEnvironmentRequest.fromEnvironment(environment));
        case "UPDATE" -> serialize(UpdateEnvironmentRequest.fromEnvironment(environment));
        case "START_INITIALIZATION" -> serialize(SubscriptionInitializationRequest.fromEnvironment(environment));
        default -> throw new IllegalArgumentException("Unknown request type: " + requestType);
      };
    } catch (JsonProcessingException e) {
      throw new InstantiatorException("Error processing " + requestType + " request because of JsonProcessing", e);
    }
  }

  private URI getUriWithOptionalPath(String basePath, String path) {
    StringBuilder uriBuilder = new StringBuilder(basePath);
    if (!path.isEmpty()) {
      uriBuilder.append("/").append(path);
    }
    return URI.create(uriBuilder.toString());
  }


  private URI getEnvironmentsUri(Environment environment, String path) {
    var basePath = sdkConfiguration.getEnvironmentsEndpoint() +
        "/" + environment.getEnvironmentType() +
        "/" + environment.getOwnerId() +
        "/" + environment.getShortName();
    
    return getUriWithOptionalPath(basePath, path);
  }
  
  private URI getEnvironmentsUri(Environment environment) {
    return getEnvironmentsUri(environment, "");
  }
}
