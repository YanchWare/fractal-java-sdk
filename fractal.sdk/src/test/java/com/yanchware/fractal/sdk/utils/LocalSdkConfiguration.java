package com.yanchware.fractal.sdk.utils;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;

public class LocalSdkConfiguration implements SdkConfiguration {
    @Override
    public String getResourceGroupId() {
        return "resource-group";
    }

    @Override
    public String getClientId() {
        return "aa574c7e-8adb-4ab8-a783-b0b0db62acf2";
    }

    @Override
    public String getClientSecret() {
        return "MnBBt&ySyZjpa%88KfC_S";
    }

    @Override
    public String getBlueprintEndpoint() {
        return "https://api.fractal-arch.org/blueprints";
    }

    @Override
    public String getLiveSystemEndpoint() {
        return "https://api.fractal-arch.org/livesystems";
    }
}
