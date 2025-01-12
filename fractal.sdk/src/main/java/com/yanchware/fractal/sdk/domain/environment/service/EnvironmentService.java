package com.yanchware.fractal.sdk.domain.environment.service;

import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.EnvironmentResponse;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.InitializationRunResponse;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.SecretResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci.OciRegion;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface EnvironmentService {
    EnvironmentResponse create(
            EnvironmentIdValue managementEnvironmentId,
            EnvironmentIdValue environmentId,
            String name,
            Collection<UUID> resourceGroups,
            Map<String, Object> parameters) throws InstantiatorException;

    EnvironmentResponse update(
            EnvironmentIdValue environmentId,
            String name,
            Collection<UUID> resourceGroups,
            Map<String, Object> parameters) throws InstantiatorException;

    EnvironmentResponse fetch(EnvironmentIdValue environmentId) throws InstantiatorException;

    void startAzureCloudAgentInitialization(
            EnvironmentIdValue managementEnvironmentId,
            EnvironmentIdValue environmentId,
            UUID tenantId,
            UUID subscriptionId,
            AzureRegion region,
            Map<String, String> tags) throws InstantiatorException;

    void startAwsCloudAgentInitialization(
            EnvironmentIdValue environmentId,
            String organizationId,
            String accountId,
            AwsRegion region,
            Map<String, String> tags) throws InstantiatorException;

    void startGcpCloudAgentInitialization(
            EnvironmentIdValue environmentId,
            String organizationId,
            String projectId,
            GcpRegion region,
            Map<String, String> tags) throws InstantiatorException;

    void startOciCloudAgentInitialization(
            EnvironmentIdValue environmentId,
            String tenancyId,
            String compartmentId,
            OciRegion region,
            Map<String, String> tags) throws InstantiatorException;

    InitializationRunResponse fetchCurrentAwsInitialization(EnvironmentIdValue environmentId) throws InstantiatorException;
    InitializationRunResponse fetchCurrentAzureInitialization(EnvironmentIdValue environmentId) throws InstantiatorException;
    InitializationRunResponse fetchCurrentGcpInitialization(EnvironmentIdValue environmentId) throws InstantiatorException;
    InitializationRunResponse fetchCurrentOciInitialization(EnvironmentIdValue environmentId) throws InstantiatorException;
    SecretResponse[] getSecrets(EnvironmentIdValue environmentId) throws InstantiatorException;
    void createSecret(EnvironmentIdValue environmentId, String secretName, String secretValue) throws InstantiatorException;
    void updateSecret(EnvironmentIdValue environmentId, String secretName, String secretValue) throws InstantiatorException;
    void deleteSecret(EnvironmentIdValue environmentId, String secretName) throws InstantiatorException;
}
