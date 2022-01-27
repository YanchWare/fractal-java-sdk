package com.yanchware.fractal.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

import static com.yanchware.fractal.sdk.utils.SerializationUtils.deserialize;
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

    CheckedFunction0<Throwable> callWithRetry = Retry.decorateCheckedSupplier(retry, () -> {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if (Arrays.stream(acceptedResponses).noneMatch((x) -> x == response.statusCode())) {
        throw new InstantiatorException(
          String.format(
            "Attempted %s failed with response code: %s and body %s ",
            requestName,
            response.statusCode(),
            response.body()));
      }

      return null;
    });

    Try<Throwable> result = Try.of(callWithRetry).recover((throwable) -> throwable);

    if (result.isFailure()) {
      throw new InstantiatorException(
        String.format("All attempts for request %s failed", requestName),
        result.get());
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

    CheckedFunction0<T> callWithRetry = Retry.decorateCheckedSupplier(retry, () -> {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if (Arrays.stream(acceptedResponses).noneMatch((x) -> x == response.statusCode())) {
        String errorMessage = String.format(
          "Attempted %s failed with response code: %s and body %s ",
          requestName,
          response.statusCode(),
          response.body());
        log.error(errorMessage);
        throw new InstantiatorException(errorMessage);
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

    Try<T> result = Try.of(callWithRetry).recover((throwable) -> {
      log.error("Attempted {} failed all attempts", requestName, throwable);
      return null;
    });

    if (result.isFailure()) {
      throw new InstantiatorException(
        String.format("All attempts for request %s failed", requestName));
    }

    return result.get();
  }

}
