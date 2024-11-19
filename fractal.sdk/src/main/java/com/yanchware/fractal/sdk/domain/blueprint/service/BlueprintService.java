package com.yanchware.fractal.sdk.domain.blueprint.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.Service;
import com.yanchware.fractal.sdk.domain.blueprint.FractalIdValue;
import com.yanchware.fractal.sdk.domain.blueprint.service.dtos.BlueprintComponentDto;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.blueprint.service.commands.CreateBlueprintCommandRequest;
import com.yanchware.fractal.sdk.domain.blueprint.service.commands.UpdateBlueprintCommandRequest;
import com.yanchware.fractal.sdk.domain.blueprint.service.dtos.BlueprintDto;
import com.yanchware.fractal.sdk.utils.HttpUtils;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Collection;

import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;

@Slf4j
public class BlueprintService extends Service {

  public BlueprintService(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {
    super(client, sdkConfiguration, retryRegistry);
  }

  public void createOrUpdate(
          FractalIdValue fractalId,
          String description,
          boolean isPrivate,
          Collection<BlueprintComponentDto> components) throws InstantiatorException
  {
    var command = new CreateBlueprintCommandRequest(description, isPrivate, components);
    log.info("Create or update blueprint [FractalId: '{}']", fractalId);

    if (retrieve(fractalId) != null) {
      update(UpdateBlueprintCommandRequest.fromCreateCommand(command, fractalId), fractalId);
      return;
    }

    create(command, fractalId);
  }

  protected void create(CreateBlueprintCommandRequest command, FractalIdValue fractalId) throws InstantiatorException {
    HttpRequest request;
    try {
      request = HttpUtils.buildPostRequest(getBlueprintsUri(fractalId), sdkConfiguration, serialize(command));
    } catch (JsonProcessingException e) {
      throw new InstantiatorException("Error processing CreateBlueprintCommandRequest because of JsonProcessing", e);
    }

    executeRequestWithRetries("createBlueprint", client, retryRegistry, request, new int[]{202});
  }

  protected void update(UpdateBlueprintCommandRequest command, FractalIdValue fractalId) throws InstantiatorException {
    HttpRequest request;
    try {
      request = HttpUtils.buildPutRequest(getBlueprintsUri(fractalId), sdkConfiguration, serialize(command));
    } catch (JsonProcessingException e) {
      throw new InstantiatorException("Error processing UpdateBlueprintCommandRequest because of JsonProcessing", e);
    }

    executeRequestWithRetries("updateBlueprint", client, retryRegistry, request, new int[]{202});
  }

  protected BlueprintDto retrieve(FractalIdValue fractalId) throws InstantiatorException {

    return executeRequestWithRetries(
        "retrieveBlueprint",
        fractalId.toString(),
        client,
        retryRegistry,
        HttpUtils.buildGetRequest(getBlueprintsUri(fractalId), sdkConfiguration),
        new int[]{200, 404},
        BlueprintDto.class);
  }

  private URI getBlueprintsUri(FractalIdValue fractalId) {
    return URI.create(sdkConfiguration.getBlueprintEndpoint() + "/" + fractalId.toString().replace(":", "/"));
  }
}
