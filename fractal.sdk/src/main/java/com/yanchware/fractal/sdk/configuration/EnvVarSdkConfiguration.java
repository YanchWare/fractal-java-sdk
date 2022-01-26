package com.yanchware.fractal.sdk.configuration;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;

import static com.yanchware.fractal.sdk.configuration.Constants.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
public class EnvVarSdkConfiguration implements SdkConfiguration {

    public final URI DEFAULT_BLUEPRINT_ENDPOINT;
    public final URI DEFAULT_LIVESYSTEM_ENDPOINT;

    public EnvVarSdkConfiguration() throws URISyntaxException {
        DEFAULT_LIVESYSTEM_ENDPOINT = new URI("https://api.fractal-arch.org/livesystems");
        DEFAULT_BLUEPRINT_ENDPOINT = new URI("https://api.fractal-arch.org/blueprints");
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
    public URI getBlueprintEndpoint() {
        return checkAndReturnUri(BLUEPRINT_ENDPOINT_KEY, DEFAULT_BLUEPRINT_ENDPOINT);
    }

    @Override
    public URI getLiveSystemEndpoint() {
        return checkAndReturnUri(LIVESYSTEM_ENDPOINT_KEY, DEFAULT_LIVESYSTEM_ENDPOINT);
    }

    private URI checkAndReturnUri(String endpointEnvKey, URI defaultValue) {
        String endpoint = System.getenv(endpointEnvKey);
        if (isBlank(endpoint)) {
            return defaultValue;
        }

        try {
            return new URI(endpoint);
        } catch (URISyntaxException e) {
            log.warn("Tried to override endpoint {} with a non valid URI {}. Fallback to standard", endpointEnvKey, endpoint);
            return defaultValue;
        }
    }
}
