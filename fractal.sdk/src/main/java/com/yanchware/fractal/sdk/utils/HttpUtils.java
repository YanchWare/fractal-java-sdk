package com.yanchware.fractal.sdk.utils;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Map;

import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_ID_HEADER;
import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_SECRET_HEADER;
import static java.net.http.HttpRequest.BodyPublishers.noBody;
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
    return buildPostRequest(uri, sdkConfiguration, payload, null);
  }

  public static HttpRequest buildPostRequest(URI uri, SdkConfiguration sdkConfiguration,
                                             String payload,
                                             Map<String, String> additionalHeaders) {

    return getHttpRequestBuilder(uri, sdkConfiguration, additionalHeaders)
        .POST(StringUtils.isBlank(payload) ? noBody() : ofString(payload))
        .build();
  }

  public static HttpRequest.Builder getHttpRequestBuilder(URI uri, SdkConfiguration sdkConfiguration) {
    return getHttpRequestBuilder(uri, sdkConfiguration, null);
  }

  public static HttpRequest.Builder getHttpRequestBuilder(URI uri,
                                                          SdkConfiguration sdkConfiguration,
                                                          Map<String, String> additionalHeaders) {
    HttpRequest.Builder builder = EnvVarUtils.isLocalDebug() ?
        LocalDebugUtils.getHttpRequestBuilder(uri, sdkConfiguration) :
        HttpRequest.newBuilder()
            .uri(uri)
            .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
            .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
            .header("Content-Type", "application/json");

    if (additionalHeaders != null) {
      additionalHeaders.forEach(builder::header);
    }

    return builder;
  }

  public static void ensureAcceptableResponse(HttpResponse<String> response, String requestName, int[] acceptedResponses)
      throws InstantiatorException {
    
    
    if (Arrays.stream(acceptedResponses).noneMatch((x) -> x == response.statusCode())) {
      var requestNameWords = StringHelper.toWords(requestName);
      
      if (response.body().contains("Token validation failed")) {
        var errorMessage = String.format("Failed to %s. Token validation failed", requestNameWords);
        log.error(errorMessage);

        throw new InstantiatorException(errorMessage);
      } else {
        // For other errors, log the full response and throw InstantiatorException
        String errorMessage = String.format(
            "Failed to %s. %s",
            requestNameWords,
            response.body());
        log.error(errorMessage);
        throw new InstantiatorException(errorMessage);
      }
    }
  }

  
}
