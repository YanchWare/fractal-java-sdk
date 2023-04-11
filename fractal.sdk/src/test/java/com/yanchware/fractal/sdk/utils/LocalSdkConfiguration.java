package com.yanchware.fractal.sdk.utils;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import lombok.SneakyThrows;

import java.net.URI;

public class LocalSdkConfiguration implements SdkConfiguration {

  private final String httpBaseUrl;

  public LocalSdkConfiguration(String httpBaseUrl) {
    this.httpBaseUrl = httpBaseUrl;
  }

  @Override
  public String getClientId() {
    return "test-client-id";
  }

  @Override
  public String getClientSecret()
  {
    return "test-client-secret";
  }

  @Override
  public String getProviderName() {
    return "test-client-id";
  }

  @SneakyThrows
  @Override
  public URI getBlueprintEndpoint() {
    return new URI(httpBaseUrl + "/blueprints");
  }

  @SneakyThrows
  @Override
  public URI getLiveSystemEndpoint() {
    return new URI(httpBaseUrl + "/livesystems");
  }

  @SneakyThrows
  @Override
  public URI getProviderEndpoint() {
    return new URI(httpBaseUrl + "/providers");
  }
}
