package com.yanchware.fractal.sdk.utils;

import com.yanchware.fractal.sdk.configuration.ServiceConfiguration;

public class LocalServiceConfiguration implements ServiceConfiguration {
    @Override
    public String getResourceGroupId() {
        return "resource-group";
    }

    @Override
    public String getClientId() {
        return "client-id";
    }

    @Override
    public String getClientSecret() {
        return "client-secret";
    }

    @Override
    public String getBlueprintEndpoint() {
        return "http://localhost:8090/blueprint";
    }

    @Override
    public String getLiveSystemEndpoint() {
        return "http://localhost:8090/livesystem";
    }
}
