package com.yanchware.fractal.sdk.domain.environment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.Service;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.environment.Secret;
import com.yanchware.fractal.sdk.domain.environment.service.commands.*;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.*;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci.OciRegion;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.HttpUtils;
import com.yanchware.fractal.sdk.utils.StringHelper;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.yanchware.fractal.sdk.configuration.Constants.*;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
public class RestEnvironmentService extends Service implements EnvironmentService {
  public RestEnvironmentService(
      HttpClient client,
      SdkConfiguration sdkConfiguration,
      RetryRegistry retryRegistry) {
    super(client, sdkConfiguration, retryRegistry);
  }

  @Override
  public EnvironmentResponse create(
      EnvironmentIdValue managementEnvironmentId,
      EnvironmentIdValue environmentId,
      String name,
      Collection<UUID> resourceGroups,
      Map<String, Object> parameters) throws InstantiatorException {
    return executeRequestWithRetries(
        "createEnvironment",
        environmentId.toString(),
        client,
        retryRegistry,
        HttpUtils.buildPostRequest(
            getEnvironmentsUri(environmentId),
            sdkConfiguration,
            serializeSafely(new CreateEnvironmentRequest(
                managementEnvironmentId,
                name,
                resourceGroups,
                parameters))),
        new int[]{201},
        EnvironmentResponse.class);
  }

  @Override
  public EnvironmentResponse update(
      EnvironmentIdValue managementEnvironmentId,
      EnvironmentIdValue environmentId,
      String name,
      Collection<UUID> resourceGroups,
      Map<String, Object> parameters,
      String defaultCiCdProfileShortName) throws InstantiatorException {
    return executeRequestWithRetries(
        "updateEnvironment",
        environmentId.toString(),
        client,
        retryRegistry,
        HttpUtils.buildPutRequest(
            getEnvironmentsUri(environmentId),
            sdkConfiguration,
            serializeSafely(new UpdateEnvironmentRequest(managementEnvironmentId, name, resourceGroups, parameters, defaultCiCdProfileShortName))),
        new int[]{200},
        EnvironmentResponse.class);
  }

  @Override
  public EnvironmentResponse fetch(EnvironmentIdValue environmentId) {
    try {

      return executeRequestWithRetries(
          "fetchEnvironment",
          environmentId.toString(),
          client,
          retryRegistry,
          HttpUtils.buildGetRequest(
              getEnvironmentsUri(environmentId),
              sdkConfiguration),
          new int[]{200, 404},
          EnvironmentResponse.class);
    } catch (Exception e) {
      log.error("An unexpected error occurred while fetching the environment [id: '{}']. " +
              "Please try again later or contact Fractal Cloud support if the issue persists.",
          environmentId);
      
      System.exit(1);
      
      return null;
      
    }
  }

  @Override
  public EnvironmentResponse tryGetById(EnvironmentIdValue environmentId) {
      try {
          return executeRequestWithRetries(
                  "fetchEnvironmentById",
                  environmentId.toString(),
                  client,
                  retryRegistry,
                  HttpUtils.buildGetRequest(
                          getEnvironmentsUri(environmentId),
                          sdkConfiguration),
                  new int[]{200, 400, 404},
                  EnvironmentResponse.class);
      } catch (InstantiatorException e) {
          return null;
      }
  }

  @Override
  public void startAzureCloudAgentInitialization(
      EnvironmentIdValue managementEnvironmentId,
      EnvironmentIdValue environmentId,
      UUID tenantId,
      UUID subscriptionId,
      AzureRegion region,
      Map<String, String> tags) throws InstantiatorException {
    var azureSpClientId = sdkConfiguration.getAzureSpClientId();
    if (isBlank(azureSpClientId)) {
      throw new IllegalArgumentException(
          String.format("The environment variable %s is required and it has not been defined", AZURE_SP_CLIENT_ID_KEY));
    }

    var azureSpClientSecret = sdkConfiguration.getAzureSpClientSecret();
    if (isBlank(azureSpClientSecret)) {
      throw new IllegalArgumentException(
          String.format("The environment variable %s is required and it has not been defined", AZURE_SP_CLIENT_SECRET_KEY));
    }

    Map<String, String> additionalHeaders = new HashMap<>();
    additionalHeaders.put(X_AZURE_SP_CLIENT_ID_HEADER, azureSpClientId);
    additionalHeaders.put(X_AZURE_SP_CLIENT_SECRET_HEADER, azureSpClientSecret);

    executeRequestWithRetries(
        "InitializeAzureSubscription",
        environmentId.toString(),
        client,
        retryRegistry,
        HttpUtils.buildPostRequest(
            getEnvironmentsUri(environmentId, "initializer/azure/initialize"),
            sdkConfiguration,
            serializeSafely(new AzureSubscriptionInitializationRequest(
                managementEnvironmentId,
                tenantId,
                subscriptionId,
                region.toString(),
                tags)),
            additionalHeaders),
        new int[]{202},
        EnvironmentResponse.class);
  }

  @Override
  public void startAwsCloudAgentInitialization(
      EnvironmentIdValue environmentId,
      String organizationId,
      String accountId,
      AwsRegion region,
      Map<String, String> tags) throws InstantiatorException {
    var awsAccessKeyId = sdkConfiguration.getAwsAccessKeyId();
    if (isBlank(awsAccessKeyId)) {
      throw new IllegalArgumentException(
          String.format("The environment variable %s is required and it has not been defined", AWS_ACCESS_KEY_ID_KEY));
    }

    var awsSecretAccessKey = sdkConfiguration.getAwsSecretAccessKey();
    if (isBlank(awsSecretAccessKey)) {
      throw new IllegalArgumentException(
          String.format("The environment variable %s is required and it has not been defined", AWS_SECRET_ACCESS_KEY));
    }

    var awsSessionToken = sdkConfiguration.getAwsSessionToken();
    if (isBlank(awsSessionToken)) {
      throw new IllegalArgumentException(
          String.format("The environment variable %s is required and it has not been defined", AWS_SESSION_TOKEN_KEY));
    }

    Map<String, String> additionalHeaders = new HashMap<>();
    additionalHeaders.put(X_AWS_ACCESS_KEY_ID_HEADER, awsAccessKeyId);
    additionalHeaders.put(X_AWS_SECRET_ACCESS_KEY_HEADER, awsSecretAccessKey);
    additionalHeaders.put(X_AWS_SESSION_TOKEN_HEADER, awsSessionToken);

    executeRequestWithRetries(
        "InitializeAwsAccount",
        environmentId.toString(),
        client,
        retryRegistry,
        HttpUtils.buildPostRequest(
            getEnvironmentsUri(environmentId, "initializer/aws/initialize"),
            sdkConfiguration,
            serializeSafely(new AwsAccountInitializationRequest(
                organizationId,
                accountId,
                region.toString(),
                tags)),
            additionalHeaders),
        new int[]{202},
        EnvironmentResponse.class);
  }

  @Override
  public void startGcpCloudAgentInitialization(
      EnvironmentIdValue environmentId,
      String organizationId,
      String projectId,
      GcpRegion region,
      Map<String, String> tags) throws InstantiatorException {
    var gcpServiceAccountEMail = sdkConfiguration.getGcpServiceAccountEMail();
    if (isBlank(gcpServiceAccountEMail)) {
      throw new IllegalArgumentException(
          String.format("The environment variable %s is required and it has not been defined", GCP_SERVICE_ACCOUNT_EMAIL_KEY));
    }

    var gcpServiceAccountCredentials = sdkConfiguration.getGcpServiceAccountCredentials();
    if (isBlank(gcpServiceAccountCredentials)) {
      throw new IllegalArgumentException(
          String.format("The environment variable %s is required and it has not been defined", GCP_SERVICE_ACCOUNT_CREDENTIALS_KEY));
    }

    Map<String, String> additionalHeaders = new HashMap<>();
    additionalHeaders.put(X_GCP_SERVICE_ACCOUNT_EMAIL_HEADER, gcpServiceAccountEMail);
    additionalHeaders.put(X_GCP_SERVICE_ACCOUNT_CREDENTIALS_HEADER, gcpServiceAccountCredentials);

    executeRequestWithRetries(
        "InitializeGcpProject",
        environmentId.toString(),
        client,
        retryRegistry,
        HttpUtils.buildPostRequest(
            getEnvironmentsUri(environmentId, "initializer/gcp/initialize"),
            sdkConfiguration,
            serializeSafely(new GcpProjectInitializationRequest(
                organizationId,
                projectId,
                region.toString(),
                tags)),
            additionalHeaders),
        new int[]{202},
        EnvironmentResponse.class);
  }

  @Override
  public void startOciCloudAgentInitialization(
      EnvironmentIdValue environmentId,
      String tenancyId,
      String compartmentId,
      OciRegion region,
      Map<String, String> tags) throws InstantiatorException {
    var ociServiceAccountId = sdkConfiguration.getOciServiceAccountId();
    if (isBlank(ociServiceAccountId)) {
      throw new IllegalArgumentException(
          String.format("The environment variable %s is required and it has not been defined", OCI_SERVICE_ACCOUNT_ID_KEY));
    }

    var ociServiceAccountCredentials = sdkConfiguration.getOciServiceAccountCredentials();
    if (isBlank(ociServiceAccountCredentials)) {
      throw new IllegalArgumentException(
          String.format("The environment variable %s is required and it has not been defined", OCI_SERVICE_ACCOUNT_CREDENTIALS_KEY));
    }

    Map<String, String> additionalHeaders = new HashMap<>();
    additionalHeaders.put(X_OCI_SERVICE_ACCOUNT_ID_HEADER, ociServiceAccountId);
    additionalHeaders.put(X_OCI_SERVICE_ACCOUNT_CREDENTIALS_HEADER, ociServiceAccountCredentials);

    executeRequestWithRetries(
        "InitializeOciProject",
        environmentId.toString(),
        client,
        retryRegistry,
        HttpUtils.buildPostRequest(
            getEnvironmentsUri(environmentId, "initializer/oci/initialize"),
            sdkConfiguration,
            serializeSafely(new GcpProjectInitializationRequest(
                tenancyId,
                compartmentId,
                region.toString(),
                tags)),
            additionalHeaders),
        new int[]{202},
        EnvironmentResponse.class);
  }

  @Override
  public InitializationRunResponse fetchCurrentAwsInitialization(EnvironmentIdValue environmentId) throws InstantiatorException {
    return fetchCurrentInitialization(environmentId, ProviderType.AWS);
  }

  @Override
  public InitializationRunResponse fetchCurrentAzureInitialization(EnvironmentIdValue environmentId) throws InstantiatorException {
    return fetchCurrentInitialization(environmentId, ProviderType.AZURE);
  }

  @Override
  public InitializationRunResponse fetchCurrentGcpInitialization(EnvironmentIdValue environmentId) throws InstantiatorException {
    return fetchCurrentInitialization(environmentId, ProviderType.GCP);
  }

  @Override
  public InitializationRunResponse fetchCurrentOciInitialization(EnvironmentIdValue environmentId) throws InstantiatorException {
    return fetchCurrentInitialization(environmentId, ProviderType.OCI);
  }

  @Override
  public CiCdProfileResponse[] manageCiCdProfiles(EnvironmentIdValue environmentId, Collection<CreateCiCdProfileRequest> ciCdProfiles) throws InstantiatorException {
    return executeRequestWithRetries(
            "manageCiCdProfiles",
            environmentId.toString(),
            client,
            retryRegistry,
            HttpUtils.buildPostRequest(
                    getCiCdProfilesBulkUri(environmentId),
                    sdkConfiguration,
                    serializeSafely(ciCdProfiles)),
            new int[]{201, 404},
            CiCdProfileResponse[].class);
  }

  @Override
  public SecretResponse[] manageSecrets(EnvironmentIdValue environmentId, Collection<Secret> secrets) throws InstantiatorException {
    return executeRequestWithRetries(
            "manageSecrets",
            environmentId.toString(),
            client,
            retryRegistry,
            HttpUtils.buildPostRequest(
                    getSecretsBulkUri(environmentId),
                    sdkConfiguration,
                    serializeSafely(secrets)),
            new int[]{201, 404},
            SecretResponse[].class);
  }

  private InitializationRunResponse fetchCurrentInitialization(
      EnvironmentIdValue environmentId,
      ProviderType provider) throws InstantiatorException {
    var providerStr = StringHelper.convertToTitleCase(provider.toString());
    var runRoot = executeRequestWithRetries(
        String.format("fetchCurrent%sInitialization", providerStr),
        environmentId.toString(),
        client,
        retryRegistry,
        HttpUtils.buildGetRequest(
            getEnvironmentsUri(environmentId, String.format("initializer/%s/status", providerStr.toLowerCase())),
            sdkConfiguration),
        new int[]{200, 404},
        InitializationRunRoot.class);

    return runRoot == null ? null : runRoot.initializationRun();
  }

  private String serializeSafely(Object command) throws InstantiatorException {
    try {
      return serialize(command);
    } catch (JsonProcessingException e) {
      throw new InstantiatorException("Error serializing command because of JsonProcessing error", e);
    }
  }

  private URI getUriWithOptionalPath(String basePath, String path) {
    StringBuilder uriBuilder = new StringBuilder(basePath);
    if (!path.isEmpty()) {
      uriBuilder.append("/").append(path);
    }
    return URI.create(uriBuilder.toString());
  }


  private URI getEnvironmentsUri(EnvironmentIdValue environmentId, String path) {
    var basePath = String.format("%s/%s/%s/%s",
        sdkConfiguration.getEnvironmentsEndpoint(),
        environmentId.type(),
        environmentId.ownerId(),
        environmentId.shortName());

    return getUriWithOptionalPath(basePath, path);
  }

  private URI getEnvironmentsUri(EnvironmentIdValue environmentId) {
    return getEnvironmentsUri(environmentId, "");
  }



  private URI getSecretsBulkUri(EnvironmentIdValue environmentId) {
    var basePath = String.format("%s/%s/%s/%s/secrets",
            sdkConfiguration.getEnvironmentsEndpoint(),
            environmentId.type(),
            environmentId.ownerId(),
            environmentId.shortName());

    return getUriWithOptionalPath(basePath, "bulk");
  }

  private URI getCiCdProfilesBulkUri(EnvironmentIdValue environmentId) {
    var ciCdProfileEndpoint = String.format("%s/%s/%s/%s/ci-cd-profiles",
            sdkConfiguration.getEnvironmentsEndpoint(),
            environmentId.type(),
            environmentId.ownerId(),
            environmentId.shortName());

    return getUriWithOptionalPath(ciCdProfileEndpoint, "bulk");
  }
}
