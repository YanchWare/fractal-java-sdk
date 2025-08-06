package com.yanchware.fractal.sdk.configuration;

import java.net.URI;

/**
 * Interface for SDK configuration.
 * Provides methods to access various configuration parameters.
 */
public interface SdkConfiguration {
  /**
   * Gets the BOT client ID or Cloud Agent Client ID.
   *
   * @return the BOT client ID or Cloud Agent Client ID
   */
  String getClientId();

  /**
   * Gets the BOT client secret or Cloud Agent Client secret.
   *
   * @return the BOT client secret or Cloud Agent Client secret
   */
  String getClientSecret();

  /**
   * Gets the Azure service principal client ID.
   *
   * @return the Azure service principal client ID
   */
  String getAzureSpClientId();

  /**
   * Gets the Azure service principal client secret.
   *
   * @return the Azure service principal client secret
   */
  String getAzureSpClientSecret();

  /**
   * Gets the URI of the blueprint endpoint.
   *
   * @return the URI of the blueprint endpoint
   */
  URI getBlueprintEndpoint();

  /**
   * Gets the URI of the live system endpoint.
   *
   * @return the URI of the live system endpoint
   */
  URI getLiveSystemEndpoint();

  /**
   * Gets the URI of the environments endpoint.
   *
   * @return the URI of the environments endpoint
   */
  URI getEnvironmentsEndpoint();

  /**
   * Gets the AWS Access Key ID.
   *
   * @return the AWS Access Key ID
   */
  String getAwsAccessKeyId();

  /**
   * Gets the AWS Secret Access Key.
   *
   * @return the AWS Secret Access Key
   */
  String getAwsSecretAccessKey();

  /**
   * Gets the AWS Session Token.
   *
   * @return the AWS Session Token
   */
  String getAwsSessionToken();

  /**
   * Gets the GCP Service Account eMail.
   *
   * @return the GCP Service Account eMail
   */
  String getGcpServiceAccountEMail();

  /**
   * Gets the GCP Service Account Credentials.
   *
   * @return the GCP Service Account Credentials
   */
  String getGcpServiceAccountCredentials();

  /**
   * Gets the OCI Service Account ID.
   *
   * @return the OCI Service Account ID
   */
  String getOciServiceAccountId();

  /**
   * Gets the OCI Service Account Credentials.
   *
   * @return the OCI Service Account Credentials
   */
  String getOciServiceAccountCredentials();

  /**
   * Gets the Hetzner Service Account ID.
   *
   * @return the Hetzner Service Account ID
   */
  String getHetznerServiceAccountId();

  /**
   * Gets the Hetzner Service Account Credentials.
   *
   * @return the Hetzner Service Account Credentials
   */
  String getHetznerServiceAccountCredentials();
}
