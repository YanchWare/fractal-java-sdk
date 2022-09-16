package com.yanchware.fractal.sdk.utils;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import lombok.SneakyThrows;

import java.net.URI;

public class LocalSdkConfiguration implements SdkConfiguration {

  private final String FRACTAL_ENDPOINT = "https://api.local.fractal.cloud:8443"

  @Override
  public String getClientId() {
    return "test-client-id";
  }

  @Override
  public String getClientSecret() {
    return "test-client-secret";
  }

  @SneakyThrows
  @Override
  public URI getBlueprintEndpoint() {
    return new URI(FRACTAL_ENDPOINT + "/blueprints");
  }

  @SneakyThrows
  @Override
  public URI getLiveSystemEndpoint() {
    return new URI(FRACTAL_ENDPOINT + "/livesystems");
  }

  @SneakyThrows
  @Override
  public URI getProviderEndpoint() {
    return new URI(FRACTAL_ENDPOINT + "/providers");
  }
}
