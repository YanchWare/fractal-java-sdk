package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos.BlueprintDto;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands.CreateEnvironmentRequest;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands.UpdateEnvironmentRequest;
import com.yanchware.fractal.sdk.utils.HttpUtils;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static com.yanchware.fractal.sdk.utils.ResiliencyUtils.executeRequestWithRetries;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;

@Slf4j
@AllArgsConstructor
public class EnvironmentsService {
  private final HttpClient client;
  private final SdkConfiguration sdkConfiguration;
  private final RetryRegistry retryRegistry;

  public void createOrUpdateEnvironment(Environment environment) throws InstantiatorException {
    if (fetchEnvironmentById(environment) != null) {
      updateEnvironment(environment);
      return;
    }

    createEnvironment(environment);
  }

  protected BlueprintDto fetchEnvironmentById(Environment environment) throws InstantiatorException {
    return executeRequestWithRetries(
        "fetchEnvironmentById",
        client,
        retryRegistry,
        HttpUtils.buildGetRequest(getEnvironmentsUri(environment), sdkConfiguration),
        new int[]{200, 404},
        BlueprintDto.class);
  }

  protected void createEnvironment(Environment environment) throws InstantiatorException {
    HttpRequest request;
    try {
      request = HttpUtils.buildPostRequest(getEnvironmentsUri(environment), 
          sdkConfiguration, 
          serialize(CreateEnvironmentRequest.fromEnvironment(environment)));
    } catch (JsonProcessingException e) {
      throw new InstantiatorException("Error processing CreateBlueprintCommandRequest because of JsonProcessing", e);
    }

    executeRequestWithRetries("createEnvironment", client, retryRegistry, request, new int[]{202});
  }

  protected void updateEnvironment(Environment environment) throws InstantiatorException {
    HttpRequest request;
    try {
      request = HttpUtils.buildPutRequest(getEnvironmentsUri(environment), 
          sdkConfiguration, 
          serialize(UpdateEnvironmentRequest.fromEnvironment(environment)));
    } catch (JsonProcessingException e) {
      throw new InstantiatorException("Error processing UpdateBlueprintCommandRequest because of JsonProcessing", e);
    }

    executeRequestWithRetries("updateEnvironment", client, retryRegistry, request, new int[]{202});
  }

  private URI getEnvironmentsUri(Environment environment) {
    return URI.create(sdkConfiguration.getEnvironmentsEndpoint() 
        + "/" + environment.getEnvironmentType()
        + "/" + environment.getOwnerId()
        + "/" + environment.getShortName());
  }
}
