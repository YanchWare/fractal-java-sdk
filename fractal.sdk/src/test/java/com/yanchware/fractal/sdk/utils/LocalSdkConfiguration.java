package com.yanchware.fractal.sdk.utils;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import lombok.SneakyThrows;

import java.net.URI;

public class LocalSdkConfiguration implements SdkConfiguration {
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
    return new URI("https://api.local.fractal.cloud:8443/blueprints");
  }

  @SneakyThrows
  @Override
  public URI getLiveSystemEndpoint() {
    return new URI("https://api.local.fractal.cloud:8443/livesystems");
  }

  @SneakyThrows
  @Override
  public URI getProviderEndpoint() {
    return new URI("https://api.local.fractal.cloud:8443/providers");
  }
}
