package com.yanchware.fractal.sdk.utils;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_ID_HEADER;
import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_SECRET_HEADER;
import static java.net.http.HttpRequest.BodyPublishers.ofString;

@Slf4j
public class HttpUtils {
  public static HttpRequest buildGetRequest(URI uri, SdkConfiguration sdkConfiguration) {
    return getHttpRequestBuilder(uri, sdkConfiguration)
        .GET()
        .build();
  }

  public static HttpRequest buildPutRequest(URI uri, SdkConfiguration sdkConfiguration, String payload) {
    return getHttpRequestBuilder(uri, sdkConfiguration)
        .PUT(ofString(payload))
        .build();
  }

  public static HttpRequest buildPostRequest(URI uri, SdkConfiguration sdkConfiguration, String payload) {
    return getHttpRequestBuilder(uri, sdkConfiguration)
        .POST(ofString(payload))
        .build();
  }

  public static HttpRequest.Builder getHttpRequestBuilder(URI uri, SdkConfiguration sdkConfiguration) {
    if (EnvVarUtils.isLocalDebug()) {
      return LocalDebugUtils.getHttpRequestBuilder(uri, sdkConfiguration);
    } else {
      return HttpRequest.newBuilder()
          .uri(uri)
          .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
          .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
          .header("Content-Type", "application/json");
    }
  }

  public static void ensureAcceptableResponse(HttpResponse<String> response, String requestName, int[] acceptedResponses)
      throws InstantiatorException {
    if (Arrays.stream(acceptedResponses).noneMatch((x) -> x == response.statusCode())) {
      String errorMessage = String.format(
          "Attempted %s failed with response code: %s and body %s ",
          requestName,
          response.statusCode(),
          response.body());
      log.error(errorMessage);
      throw new InstantiatorException(errorMessage);
    }
  }
}
