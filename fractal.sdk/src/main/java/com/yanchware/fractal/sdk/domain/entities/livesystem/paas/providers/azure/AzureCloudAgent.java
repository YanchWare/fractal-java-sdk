package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.aggregates.CloudAgent;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;

import java.util.*;

import static com.yanchware.fractal.sdk.domain.entities.environment.EnvironmentConstants.*;

@Getter
public class AzureCloudAgent extends CloudAgent {
    public static final String TENANT_ID_PARAM_KEY = "tenantId";
    public static final String SUBSCRIPTION_ID_PARAM_KEY = "subscriptionId";

    private final AzureRegion region;
    private final UUID tenantId;
    private final UUID subscriptionId;

    public AzureCloudAgent(AzureRegion region, UUID tenantId, UUID subscriptionId) {
        this.region = region;
        this.tenantId = tenantId;
        this.subscriptionId = subscriptionId;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.AZURE;
    }

    @Override
    public void injectIntoEnvironmentParameters(Map<String, Object> environmentParameters) {
        Collection<Map<String, Object>> existingAgents = (Collection<Map<String, Object>>) environmentParameters.get(CLOUD_AGENTS_PARAM_KEY);
        if (existingAgents == null) {
            existingAgents = new ArrayList<>();
            environmentParameters.put(CLOUD_AGENTS_PARAM_KEY, existingAgents);
        }
        existingAgents.add(Map.of(
                REGION_PARAM_KEY, region,
                TENANT_ID_PARAM_KEY, tenantId,
                SUBSCRIPTION_ID_PARAM_KEY, subscriptionId
        ));
    }
}
