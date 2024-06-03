package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.EnvironmentInitializationException;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands.CreateEnvironmentRequest;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands.SubscriptionInitializationRequest;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands.UpdateEnvironmentRequest;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.dtos.EnvironmentResponse;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.dtos.InitializationRunResponse;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.dtos.InitializationRunRoot;
import com.yanchware.fractal.sdk.services.contracts.environmentscontract.dtos.InitializationStepResponse;
import com.yanchware.fractal.sdk.utils.HttpUtils;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static com.yanchware.fractal.sdk.configuration.Constants.*;
import static com.yanchware.fractal.sdk.utils.ResiliencyUtils.executeRequestWithRetries;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@AllArgsConstructor
public class EnvironmentsService {
  private final Duration totalAllowedDuration = Duration.ofMinutes(55);
  
  private final HttpClient client;
  private final SdkConfiguration sdkConfiguration;
  private final RetryRegistry retryRegistry;

  public EnvironmentResponse createOrUpdateEnvironment(Environment environment) throws InstantiatorException {
    var environmentId = environment.getEnvironmentType() + "\\" + environment.getOwnerId() + "\\" + environment.getShortName();
    log.info("Starting createOrUpdateEnvironment for Environment [id: '{}']", environmentId);

    var existingEnvironment = fetchEnvironment(environment);
    if (existingEnvironment != null) {
      if (existingEnvironment.equalsTo(environment)) {
        log.info("No changes detected for Environment [id: '{}']. Update not required.", environmentId);
        return existingEnvironment;
      } else {
        return updateEnvironment(environment);
      }
    }
    
    return createEnvironment(environment);
  }

  protected EnvironmentResponse fetchEnvironment(Environment environment) throws InstantiatorException {
    log.info("Fetching environment details [id: '{}']", environment.getEnvironmentType() + "\\" + environment.getOwnerId() + "\\" + environment.getShortName());
    return executeRequestWithRetries(
        "fetchEnvironment",
        client,
        retryRegistry,
        HttpUtils.buildGetRequest(getEnvironmentsUri(environment), sdkConfiguration),
        new int[]{200, 404},
        EnvironmentResponse.class);
  }

  protected EnvironmentResponse createEnvironment(Environment environment) throws InstantiatorException {
    log.info("Environment does not exist, creating new environment [id: '{}']", 
        environment.getEnvironmentType() + "\\" + environment.getOwnerId() + "\\" + environment.getShortName());

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
    log.info("Environment [id: '{}'] exists, updating ...", environment.getEnvironmentType() + "\\" + environment.getOwnerId() + "\\" + environment.getShortName());
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
    InitializeSubscription(environment, Duration.ofSeconds(30));
  }

  protected void InitializeSubscription(Environment environment, Duration waitDurationBetweenChecks) throws InstantiatorException {
    var currentInitialization = fetchCurrentInitialization(environment);

    if (currentInitialization == null ||
        "Failed".equals(currentInitialization.getStatus()) ||
        "Cancelled".equals(currentInitialization.getStatus())) {
      startNewInitialization(environment);

      log.info("New initialization started, checking initialization status for environment [id: '{}']", environment.getEnvironmentType() + "\\" + environment.getOwnerId() + "\\" + environment.getShortName());
      checkInitializationStatus(environment, waitDurationBetweenChecks);
    } else {
      log.info("Checking initialization status for environment [id: '{}']", environment.getEnvironmentType() + "\\" + environment.getOwnerId() + "\\" + environment.getShortName());
      checkInitializationStatus(environment, waitDurationBetweenChecks);
    }
  }
  
  protected InitializationRunResponse fetchCurrentInitialization(Environment environment) throws InstantiatorException {
    log.info("Fetching current initialization status for environment [id: '{}']", environment.getEnvironmentType() + "\\" + environment.getOwnerId() + "\\" + environment.getShortName());

    var runRoot = executeRequestWithRetries(
        "fetchCurrentInitialization",
        client,
        retryRegistry,
        HttpUtils.buildGetRequest(getEnvironmentsUri(environment, "initializer/azure/status"), sdkConfiguration),
        new int[]{200, 404},
        InitializationRunRoot.class);

    return runRoot == null ? null : runRoot.getInitializationRun();
  }

  private void startNewInitialization(Environment environment) throws InstantiatorException {
    log.info("Starting new initialization process for environment [id: '{}']", environment.getEnvironmentType() + "\\" + environment.getOwnerId() + "\\" + environment.getShortName());
    var payload = getPayload(environment, "START_INITIALIZATION");

    var azureSpClientId = sdkConfiguration.getAzureSpClientId();
    if (isBlank(azureSpClientId)) {
      throw new IllegalArgumentException(
          String.format("The environment variable %s is required and it has not been defined", AZURE_SP_CLIENT_ID_KEY));
    }

    var azureSpClientSecret = sdkConfiguration.getAzureSpClientSecret();
    if (isBlank(azureSpClientSecret)) {
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

  private void checkInitializationStatus(Environment environment, Duration waitDuration) throws InstantiatorException {
    log.info("Starting operation [checkInitializationStatus] for Environment [id: '{}']",
        environment.getEnvironmentType() + "\\" + environment.getOwnerId() + "\\" + environment.getShortName());

    int maxAttempts = (int) (totalAllowedDuration.toMillis() / waitDuration.toMillis());

    var retryConfig = RetryConfig.custom()
        .retryExceptions(InstantiatorException.class)
        .maxAttempts(maxAttempts)
        .waitDuration(waitDuration)
        .build();

    var retry = RetryRegistry.of(retryConfig).retry("checkInitializationStatus");

    try {
      Retry.decorateCheckedSupplier(retry, () -> {
        InitializationRunResponse currentInitialization = fetchCurrentInitialization(environment);
        printInitializationStatus(currentInitialization);
        validateInitializationStatus(currentInitialization);

        return currentInitialization;
      }).get();
    } catch (Throwable ex) {
      throw new InstantiatorException(ex.getLocalizedMessage());
    }
  }

  private void validateInitializationStatus(InitializationRunResponse initializationRun) throws InstantiatorException, EnvironmentInitializationException {
    var environmentId = initializationRun.getEnvironmentId().toString();
    switch (initializationRun.getStatus()) {
      case "Completed" -> log.info("Initialization for Environment [id: '{}'] completed", environmentId);
      case "Failed" -> {
        var messageToThrow = getFailedStepsMessageToThrow(environmentId, initializationRun);
        if (isBlank(messageToThrow)) {
          log.warn("Initialization for Environment [id: '{}'] failed but error message is not visible yet", environmentId);
          throw new InstantiatorException("Initialization failed, but the error message is not visible yet.");
        } else {
          log.error("Initialization for Environment [id: '{}'] failed", environmentId);
          throw new EnvironmentInitializationException(messageToThrow);
        }
      }
      case "Cancelled" -> {
        log.warn("Initialization for Environment [id: '{}'] cancelled", environmentId);
        throw new EnvironmentInitializationException("Initialization was cancelled.");
      }
      case "InProgress" -> {
        log.info("Initialization for Environment [id: '{}'] is in progress", environmentId);
        throw new InstantiatorException("Initialization is in progress, retrying...");
      }
      default -> throw new EnvironmentInitializationException(String.format("Unknown initialization status: [%s]", initializationRun.getStatus()));
    }
  }

  private String getFailedStepsMessageToThrow(String environmentId, InitializationRunResponse initializationRun) {
    var failedSteps = initializationRun.getSteps()
        .stream()
        .filter(step -> "Failed".equals(step.getStatus()))
        .toList();

    if (failedSteps.isEmpty()) {
      return "";
    } else {
      var messageToThrow = new StringBuilder();
      messageToThrow.append(
          String.format("Initialization for Environment [id: '%s'] failed. Failed steps -> ", environmentId));
      for (var step : failedSteps) {
        var errorMessage = step.getLastOperationStatusMessage();

        if (isBlank(errorMessage)) {
          return "";
        }

        messageToThrow.append(String.format("id: '%s', status: '%s', errorMessage: '%s' - ",
            step.getResourceName(),
            step.getStatus(),
            errorMessage));
      }

      return messageToThrow.substring(0, messageToThrow.length() - 3);
    }
  }

  private void printInitializationStatus(InitializationRunResponse initializationRun) {
    log.info("Initialization Run ID: {}", initializationRun.getId());
    
    initializationRun.getSteps().sort(Comparator.comparingInt(InitializationStepResponse::getOrder));
    
    initializationRun.getSteps()
        .forEach(step -> {
          String status = step.getStatus();
          String resourceName = step.getResourceName();
          String resourceType = step.getResourceType();
          
          if ("Failed".equalsIgnoreCase(status)) {
            log.error("Step - Name: '{}', Type: '{}', Status: '{}'",
                resourceName, resourceType, status);
          } else {
            log.info("Step - Name: '{}', Type: '{}', Status: '{}'",
                resourceName, resourceType, status);
          }
        });
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
