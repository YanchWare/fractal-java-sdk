package com.yanchware.fractal.sdk.configuration;

public interface ServiceConfiguration {

    String getResourceGroupId();

    String getClientId();

    String getClientSecret();

    String getBlueprintEndpoint();

    String getLiveSystemEndpoint();
}
