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
  public String getClientSecret() {
    return "test-client-secret";
  }

  @Override
  public String getAzureSpClientId() {
    return "xxx";
  }

  @Override
  public String getAzureSpClientSecret() {
    return "xxx";
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
  public URI getEnvironmentsEndpoint() { return new URI(httpBaseUrl + "/environments"); }

  @Override
  public String getAwsAccessKeyId() {
    return "xxx";
  }

  @Override
  public String getAwsSecretAccessKey() {
    return "xxx";
  }

  @Override
  public String getAwsSessionToken() {
    return "xxx";
  }

  @Override
  public String getGcpServiceAccountEMail() {
    return "xxx";
  }

  @Override
  public String getGcpServiceAccountCredentials() {
    return "xxx";
  }

  @Override
  public String getOciServiceAccountId() {
    return "xxx";
  }

  @Override
  public String getOciServiceAccountCredentials() {
    return "xxx";
  }
}
