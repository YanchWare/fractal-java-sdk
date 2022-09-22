package com.yanchware.fractal.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.configuration.instantiation.InstantiationWaitConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.exceptions.ProviderException;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemMutationDto;
import com.yanchware.fractal.sdk.services.contracts.providerscontract.dtos.ProviderLiveSystemComponentDto;
import com.yanchware.fractal.sdk.services.contracts.providerscontract.responses.CurrentLiveSystemsResponse;
import com.yanchware.fractal.sdk.services.contracts.providerscontract.responses.LiveSystemMutationResponse;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentStatusDto.Active;
import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentStatusDto.Failed;
import static com.yanchware.fractal.sdk.utils.HttpUtils.*;
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

    CheckedFunction0<Throwable> callWithRetry = Retry.decorateCheckedSupplier(retry, () -> {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      ensureAcceptableResponse(response, requestName, acceptedResponses);

      return null;
    });

    Try<Throwable> result = Try.of(callWithRetry);

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

    Try<T> result = Try.of(callWithRetry);

    if (result.isFailure()) {
      log.error("Attempted {} failed all attempts", requestName, result.getCause());
      throw new InstantiatorException(
          String.format("All attempts for request %s failed with cause: %s", requestName, result.getCause()));
    }

    return result.get();
  }

}