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
    public final URI DEFAULT_PROVIDER_ENDPOINT;

    public EnvVarSdkConfiguration() throws URISyntaxException {
        DEFAULT_LIVESYSTEM_ENDPOINT = new URI("https://api.fractal.cloud/livesystems");
        DEFAULT_BLUEPRINT_ENDPOINT = new URI("https://api.fractal.cloud/blueprints");
        DEFAULT_PROVIDER_ENDPOINT = new URI("https://api.fractal.cloud/providers");
    }

    @Override
    public String getClientId() {
        var clientId = System.getenv(CLIENT_ID_KEY);
        if(isBlank(clientId)) {
            throw new IllegalArgumentException(
              String.format("The environment variable %s is required and it has not been defined", CLIENT_ID_KEY));
        }
        return clientId;
    }

    @Override
    public String getClientSecret() {
        var clientSecret = System.getenv(CLIENT_SECRET_KEY);
        if(isBlank(clientSecret)) {
            throw new IllegalArgumentException(
              String.format("The environment variable %s is required and it has not been defined", CLIENT_SECRET_KEY));
        }
        return clientSecret;
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
