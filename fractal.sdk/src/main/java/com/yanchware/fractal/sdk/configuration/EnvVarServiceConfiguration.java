package com.yanchware.fractal.sdk.configuration;

import static com.yanchware.fractal.sdk.configuration.Constants.*;

public class EnvVarServiceConfiguration implements ServiceConfiguration {
    @Override
    public String getResourceGroupId() {
        return System.getenv(RESOURCE_GROUP_ID_KEY);
    }

    @Override
    public String getClientId() {
        return System.getenv(CLIENT_ID_KEY);
    }

    @Override
    public String getClientSecret() {
        return System.getenv(CLIENT_SECRET_KEY);
    }

    @Override
    public String getBlueprintEndpoint() {
        return System.getenv(BLUEPRINT_ENDPOINT_KEY);
    }

    @Override
    public String getLiveSystemEndpoint() {
        return System.getenv(LIVESYSTEM_ENDPOINT_KEY);
    }
}
