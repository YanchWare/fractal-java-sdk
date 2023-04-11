package com.yanchware.fractal.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.yanchware.fractal.sdk.utils.HttpUtils.ensureAcceptableResponse;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.deserialize;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
public class ResiliencyUtils {

  public static void executeRequestWithRetries(
      String requestName,
      HttpClient client,
      RetryRegistry retryRegistry,
      HttpRequest request,
      int[] acceptedResponses) throws InstantiatorException {

    Retry retry = retryRegistry.retry(requestName);

    try {
      var result = Retry.decorateCheckedSupplier(retry, () -> {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ensureAcceptableResponse(response, requestName, acceptedResponses);

        return null;
      });

      log.debug(serialize(result.get()));
    } catch (Throwable ex) {
      throw new InstantiatorException(
        String.format("All attempts for request %s failed", requestName),
        ex);
    }
  }

  public static <T> T executeRequestWithRetries(
      String requestName,
      HttpClient client,
      RetryRegistry retryRegistry,
      HttpRequest request,
      int[] acceptedResponses,
      Class<T> classRef) throws InstantiatorException {

    Retry retry = retryRegistry.retry(requestName);

    try {
      var result = Retry.decorateCheckedSupplier(retry, () -> {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ensureAcceptableResponse(response, requestName, acceptedResponses);

        if (response.statusCode() == 404) {
          log.info("Attempted {} has come up with a 404 Not Found. Will attempt to create it.", requestName);
          return null;
        }

        String bodyContents = response.body();
        if (isBlank(bodyContents)) {
          log.error("Attempted {} has come up with empty or null body contents: {}", requestName, bodyContents);
          return null;
        }

        try {
          return deserialize(bodyContents, classRef);
        } catch (JsonProcessingException e) {
          log.error("Attempted {} failed. Deserialization of {} failed.", requestName, bodyContents);
          return null;
        }
      });
      return result.get();
    } catch (Throwable ex) {
      log.error("Attempted {} failed all attempts", requestName, ex.getCause());
      throw new InstantiatorException(
        String.format("All attempts for request %s failed with cause: %s", requestName, ex));
    }
  }

}