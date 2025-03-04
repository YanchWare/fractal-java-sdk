package com.yanchware.fractal.sdk.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.EnvironmentException;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.utils.StringHelper;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.yanchware.fractal.sdk.configuration.Constants.CI_CD_SERVICE_ACCOUNT_NAME_KEY;
import static com.yanchware.fractal.sdk.configuration.Constants.CI_CD_SERVICE_ACCOUNT_SECRET_KEY;
import static com.yanchware.fractal.sdk.utils.HttpUtils.ensureAcceptableResponse;
import static com.yanchware.fractal.sdk.utils.HttpUtils.formatErrorMessage;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.deserialize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
public abstract class Service {
  protected final HttpClient client;
  protected final SdkConfiguration sdkConfiguration;
  protected final RetryRegistry retryRegistry;

  protected Service(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {
    this.client = client;
    this.sdkConfiguration = sdkConfiguration;
    this.retryRegistry = retryRegistry;
  }

  protected static void executeRequestWithRetries(
      String requestName,
      HttpClient client,
      RetryRegistry retryRegistry,
      HttpRequest request,
      int[] acceptedResponses) throws InstantiatorException {

    Retry retry = retryRegistry.retry(requestName);

    try {
      Retry.decorateCheckedSupplier(retry, () -> {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ensureAcceptableResponse(response, requestName, acceptedResponses);

        return null;
      }).get();
    } catch (Throwable ex) {
      throw new InstantiatorException(
          String.format("All attempts to %s failed", StringHelper.toWords(requestName)),
          ex);
    }
  }

  protected static <T> T executeRequestWithRetries(
      String requestName,
      String entityId,
      HttpClient client,
      RetryRegistry retryRegistry,
      HttpRequest request,
      int[] acceptedResponses,
      Class<T> classRef) throws InstantiatorException {

    Retry retry = retryRegistry.retry(requestName);

    var requestNameLog = StringHelper.toWords(requestName);

    try {
      var result = Retry.decorateCheckedSupplier(retry, () -> {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 401) {
          var message = String.format("Invalid credentials provided. Please ensure the '%s' and '%s' environment variables are correctly set. Contact Fractal Cloud support if you need assistance with these credentials.",
              CI_CD_SERVICE_ACCOUNT_NAME_KEY, CI_CD_SERVICE_ACCOUNT_SECRET_KEY);
          
          String errorMessage = formatErrorMessage(requestName, message, entityId);

          log.error(errorMessage);

          System.exit(1);
        }

        ensureAcceptableResponse(response, requestName, entityId, acceptedResponses);

        if (response.statusCode() == 404) {
          return null;
        }

        String bodyContents = response.body();
        if (isBlank(bodyContents)) {
          log.error("Attempted {} has come up with empty or null body contents", requestNameLog);
          return null;
        }
        
        if(requestName.equals("fetchEnvironmentById")) {
          if(bodyContents.contains("Guid should contain 32 digits")) {
            return null;
          } else {
            throw new EnvironmentException(String.format("Environment [id: '%s'] not found", entityId));
          }
        }
       
        try {
          return deserialize(bodyContents, classRef);
        } catch (JsonProcessingException e) {
          log.error("Attempted {} failed. Deserialization of {} failed", requestNameLog, bodyContents);
          return null;
        }
      });
      return result.get();
    } catch (Throwable ex) {
      log.error("All attempts failed. {}", ex.getMessage()); // Improved log message
      throw new InstantiatorException(
          String.format("%s. Please try again later or contact Fractal Cloud support if the issue persists.",
              ex.getMessage())); // Improved exception message
    }
  }
}
