package com.yanchware.fractal.sdk.utils;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;

public class LocalSdkConfiguration implements SdkConfiguration {
    @Override
    public String getResourceGroupId() {
        return "resource-group";
    }

    @Override
    public String getClientId() {
        return "sdk";
    }

    @Override
    public String getClientSecret() {
        return "sdksecret";
    }

    @Override
    public String getBlueprintEndpoint() {
        return "https://local.yanchware.com:4443/blueprints";
    }

    @Override
    public String getLiveSystemEndpoint() {
        return "https://local.yanchware.com:4443/livesystems";
    }
}
