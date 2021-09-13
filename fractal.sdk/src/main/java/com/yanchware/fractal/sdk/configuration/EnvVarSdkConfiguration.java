package com.yanchware.fractal.sdk.configuration;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static com.yanchware.fractal.sdk.configuration.Constants.*;

public class EnvVarSdkConfiguration implements SdkConfiguration {
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
        String blueprintEndpoint = System.getenv(BLUEPRINT_ENDPOINT_KEY);
        return isBlank(blueprintEndpoint)
          ? DEFAULT_BLUEPRINT_ENDPOINT
          : blueprintEndpoint;
    }

    @Override
    public String getLiveSystemEndpoint() {
        String blueprintEndpoint = System.getenv(LIVESYSTEM_ENDPOINT_KEY);
        return isBlank(blueprintEndpoint)
          ? DEFAULT_LIVESYSTEM_ENDPOINT
          : blueprintEndpoint;
    }
}
