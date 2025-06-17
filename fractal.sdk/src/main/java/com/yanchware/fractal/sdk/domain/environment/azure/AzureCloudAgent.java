package com.yanchware.fractal.sdk.domain.environment.azure;

import com.yanchware.fractal.sdk.domain.environment.CloudAgentEntity;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.environment.oci.OciCloudAgent;
import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.InitializationRunResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.yanchware.fractal.sdk.domain.environment.EnvironmentAggregate.REGION_PARAM_KEY;

@Slf4j
@Getter
public class AzureCloudAgent extends CloudAgentEntity {
    public static final String TENANT_ID_PARAM_KEY = "tenantId";
    public static final String SUBSCRIPTION_ID_PARAM_KEY = "subscriptionId";
    public static final UUID EMPTY_UUID = new UUID(0L, 0L);

    private final AzureRegion region;
    private final UUID tenantId;
    private final UUID subscriptionId;

    public AzureCloudAgent(
            EnvironmentIdValue environmentId,
            AzureRegion region,
            UUID tenantId,
            UUID subscriptionId,
            Map<String, String> tags) {
        super(environmentId, tags);
        this.region = region;
        this.tenantId = tenantId;
        this.subscriptionId = subscriptionId;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.AZURE;
    }

    @Override
    public void initialize(EnvironmentService environmentService) throws InstantiatorException {
        initialize(environmentService, null);
    }

    @Override
    public void initialize(EnvironmentService environmentService, EnvironmentIdValue managementEnvironmentId) throws InstantiatorException {
        var currentInitialization = environmentService.fetchCurrentAzureInitialization(environmentId);

        if (currentInitialization != null && currentInitialization.status().equals("Completed")) {
            log.info("{} Cloud Agent initialization for environment '{}' completed successfully.",
                    currentInitialization.cloudProvider(), currentInitialization.environmentId());
            return;
        }

        if (currentInitialization == null ||
                currentInitialization.id().equals(EMPTY_UUID) ||
                "Failed".equals(currentInitialization.status()) ||
                "Cancelled".equals(currentInitialization.status())) {

            environmentService.startAzureCloudAgentInitialization(
                    managementEnvironmentId,
                    environmentId,
                    tenantId,
                    subscriptionId,
                    region,
                    tags);

            checkInitializationStatus(() -> fetchCurrentAzureInitialization(environmentService));
        } else {
            checkInitializationStatus(() -> fetchCurrentAzureInitialization(environmentService));
        }
    }

    @Override
    public Map<String, Object> getConfigurationForEnvironmentParameters() {
        return Map.of(
                PROVIDER_PARAM_KEY, getProvider(),
                REGION_PARAM_KEY, region,
                TENANT_ID_PARAM_KEY, tenantId,
                SUBSCRIPTION_ID_PARAM_KEY, subscriptionId);
    }

    private InitializationRunResponse fetchCurrentAzureInitialization(EnvironmentService environmentService) {
        try {
            return environmentService.fetchCurrentAzureInitialization(environmentId);
        } catch (InstantiatorException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AzureCloudAgent member = (AzureCloudAgent) o;

        return region.equals(member.region)
          && tenantId.equals(member.tenantId)
          && subscriptionId.equals(member.subscriptionId)
          && environmentId.equals(member.environmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, tenantId, subscriptionId,  environmentId);
    }
}
