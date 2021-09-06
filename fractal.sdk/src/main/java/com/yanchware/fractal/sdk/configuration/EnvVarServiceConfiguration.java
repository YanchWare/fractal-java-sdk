package com.yanchware.fractal.sdk.configuration;

import static com.yanchware.fractal.sdk.configuration.Constants.RESOURCE_GROUP_ID_KEY;

public class EnvVarServiceConfiguration implements ServiceConfiguration {
    @Override
    public String getResourceGroupId() {
        return System.getenv(RESOURCE_GROUP_ID_KEY);
    }
}
