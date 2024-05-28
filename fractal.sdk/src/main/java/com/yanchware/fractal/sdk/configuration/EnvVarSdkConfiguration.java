package com.yanchware.fractal.sdk.configuration;

import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;

import static com.yanchware.fractal.sdk.configuration.Constants.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Implementation of {@link SdkConfiguration} that retrieves configuration values from environment variables.
 */
@Slf4j
public class EnvVarSdkConfiguration implements SdkConfiguration {

  /**
   * The default URI for the blueprint endpoint.
   */
  public final URI DEFAULT_BLUEPRINT_ENDPOINT;

  /**
   * The default URI for the live system endpoint.
   */
  public final URI DEFAULT_LIVESYSTEM_ENDPOINT;

  /**
   * The default URI for the environments endpoint.
   */
  public final URI DEFAULT_ENVIRONMENTS_ENDPOINT;

  /**
   * Constructs an instance of {@link EnvVarSdkConfiguration}.
   *
   * @throws URISyntaxException if the default URIs are invalid
   */
  public EnvVarSdkConfiguration() throws URISyntaxException {
    DEFAULT_LIVESYSTEM_ENDPOINT = new URI("https://api.fractal.cloud/livesystems");
    DEFAULT_BLUEPRINT_ENDPOINT = new URI("https://api.fractal.cloud/blueprints");
    DEFAULT_ENVIRONMENTS_ENDPOINT = new URI("https://api.fractal.cloud/environments");
  }

  /**
   * Gets the BOT client ID or Cloud Agent Client ID from environment variables.
   * The environment variable key is {@value com.yanchware.fractal.sdk.configuration.Constants#CLIENT_ID_KEY}.
   *
   * @return the BOT client ID or Cloud Agent Client ID
   * @throws IllegalArgumentException if the client ID environment variable is not set
   */
  @Override
  public String getClientId() {
    var clientId = System.getenv(CLIENT_ID_KEY);
    if (isBlank(clientId)) {
      throw new IllegalArgumentException(
          String.format("The environment variable %s is required and it has not been defined", CLIENT_ID_KEY));
    }
    return clientId;
  }

  /**
   * Gets the BOT client secret or Cloud Agent Client secret from environment variables.
   * The environment variable key is {@value com.yanchware.fractal.sdk.configuration.Constants#CLIENT_SECRET_KEY}.
   *
   * @return the BOT client secret or Cloud Agent Client secret
   * @throws IllegalArgumentException if the client secret environment variable is not set
   */
  @Override
  public String getClientSecret() {
    var clientSecret = System.getenv(CLIENT_SECRET_KEY);
    if (isBlank(clientSecret)) {
      throw new IllegalArgumentException(
          String.format("The environment variable %s is required and it has not been defined", CLIENT_SECRET_KEY));
    }
    return clientSecret;
  }

  /**
   * Gets the Azure service principal client ID from environment variables.
   * The environment variable key is {@value com.yanchware.fractal.sdk.configuration.Constants#AZURE_SP_CLIENT_ID_KEY}.
   *
   * @return the Azure service principal client ID
   */
  @Override
  public String getAzureSpClientId() {
    return System.getenv(AZURE_SP_CLIENT_ID_KEY);
  }

  /**
   * Gets the Azure service principal client secret from environment variables.
   * The environment variable key is {@value com.yanchware.fractal.sdk.configuration.Constants#AZURE_SP_CLIENT_SECRET_KEY}.
   *
   * @return the Azure service principal client secret
   */
  @Override
  public String getAzureSpClientSecret() {
    return System.getenv(AZURE_SP_CLIENT_SECRET_KEY);
  }

  /**
   * Gets the URI of the blueprint endpoint from environment variables or default.
   * The environment variable key is {@value com.yanchware.fractal.sdk.configuration.Constants#BLUEPRINT_ENDPOINT_KEY}.
   *
   * @return the URI of the blueprint endpoint
   */
  @Override
  public URI getBlueprintEndpoint() {
    return checkAndReturnUri(BLUEPRINT_ENDPOINT_KEY, DEFAULT_BLUEPRINT_ENDPOINT);
  }

  /**
   * Gets the URI of the live system endpoint from environment variables or default.
   * The environment variable key is {@value com.yanchware.fractal.sdk.configuration.Constants#LIVESYSTEM_ENDPOINT_KEY}.
   *
   * @return the URI of the live system endpoint
   */
  @Override
  public URI getLiveSystemEndpoint() {
    return checkAndReturnUri(LIVESYSTEM_ENDPOINT_KEY, DEFAULT_LIVESYSTEM_ENDPOINT);
  }

  /**
   * Gets the URI of the environments endpoint from environment variables or default.
   * The environment variable key is {@value com.yanchware.fractal.sdk.configuration.Constants#ENVIRONMENTS_ENDPOINT_KEY}.
   *
   * @return the URI of the environments endpoint
   */
  @Override
  public URI getEnvironmentsEndpoint() {
    return checkAndReturnUri(ENVIRONMENTS_ENDPOINT_KEY, DEFAULT_ENVIRONMENTS_ENDPOINT);
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
