package com.yanchware.fractal.sdk.configuration;

import java.net.URI;

public interface SdkConfiguration {
  String getClientId();
  String getClientSecret();
  String getAzureSpClientId();
  String getAzureSpClientSecret();
  URI getBlueprintEndpoint();
  URI getLiveSystemEndpoint();
  URI getEnvironmentsEndpoint();
}
