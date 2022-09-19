package com.yanchware.fractal.sdk.configuration;

import java.net.URI;

public interface SdkConfiguration {
  String getClientId();
  String getClientSecret();
  String getProviderName();
  URI getBlueprintEndpoint();
  URI getLiveSystemEndpoint();
  URI getProviderEndpoint();
}
