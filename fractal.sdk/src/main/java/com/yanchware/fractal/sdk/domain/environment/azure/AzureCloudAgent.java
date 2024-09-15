package com.yanchware.fractal.sdk.domain.environment.azure;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.environment.CloudAgentEntity;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.yanchware.fractal.sdk.domain.environment.EnvironmentAggregate.REGION_PARAM_KEY;

@Slf4j
@Getter
public class AzureCloudAgent extends CloudAgentEntity {
    public static final String TENANT_ID_PARAM_KEY = "tenantId";
    public static final String SUBSCRIPTION_ID_PARAM_KEY = "subscriptionId";

    private final AzureRegion region;
    private final UUID tenantId;
    private final UUID subscriptionId;

    public AzureCloudAgent(
            EnvironmentIdValue environmentId,
            EnvironmentService environmentService,
            AzureRegion region,
            UUID tenantId,
            UUID subscriptionId,
            Map<String, String> tags)
    {
        super(environmentId, environmentService, tags);
        this.region = region;
        this.tenantId = tenantId;
        this.subscriptionId = subscriptionId;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.AZURE;
    }

    @Override
    public void initialize() throws InstantiatorException {
        var currentInitialization = environmentService.fetchCurrentAzureInitialization(environmentId);

        if (currentInitialization == null ||
                "Failed".equals(currentInitialization.status()) ||
                "Cancelled".equals(currentInitialization.status())) {

            environmentService.startAzureCloudAgentInitialization(
                    environmentId,
                    tenantId,
                    subscriptionId,
                    region,
                    tags);

            log.info("New initialization started, checking initialization status for environment [id: '{}']", environmentId);
            checkInitializationStatus();
        } else {
            log.info("Checking initialization status for environment [id: '{}']", environmentId);
            checkInitializationStatus();
        }
    }

    @Override
    public Map<String, Object> getConfigurationForEnvironmentParameters() {
        return Map.of(
                REGION_PARAM_KEY, region,
                TENANT_ID_PARAM_KEY, tenantId,
                SUBSCRIPTION_ID_PARAM_KEY, subscriptionId);
    }
}
