package com.yanchware.fractal.sdk.utils;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_ID_HEADER;
import static com.yanchware.fractal.sdk.configuration.Constants.X_CLIENT_SECRET_HEADER;

@Slf4j
public class LocalDebugUtils {

  public static HttpRequest.Builder getHttpRequestBuilder(URI uri, SdkConfiguration sdkConfiguration) {
    return HttpRequest.newBuilder()
        .uri(uri)
        .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
        .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
        .header("X-JWT", getJwtToken(sdkConfiguration, uri.getPath()))
        .header("Content-Type", "application/json");
  }

  public static String getJwtToken(SdkConfiguration sdkConfiguration, String uriPath) {
    try {
      var client = HttpClient.newHttpClient();

      // create a request
      var request = HttpRequest.newBuilder(
              URI.create(String.format("http://api.local.fractal.cloud:8080%s", uriPath)))
          .header(X_CLIENT_ID_HEADER, sdkConfiguration.getClientId())
          .header(X_CLIENT_SECRET_HEADER, sdkConfiguration.getClientSecret())
          .header("Content-Type", "application/json")
          .POST(HttpRequest.BodyPublishers.noBody())
          .build();


      var response = client.send(request, HttpResponse.BodyHandlers.ofString());
      var jwtOptional = response.headers().firstValue("X-JWT");

      if (jwtOptional.isPresent()) {
        return jwtOptional.get();
      } else {
        throw new Exception("Unable to get JWT Token");
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
