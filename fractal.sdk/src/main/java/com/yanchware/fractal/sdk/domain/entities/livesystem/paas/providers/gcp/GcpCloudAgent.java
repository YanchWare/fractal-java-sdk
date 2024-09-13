package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.gcp;

import com.yanchware.fractal.sdk.aggregates.CloudAgent;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.entities.environment.EnvironmentConstants.CLOUD_AGENTS_PARAM_KEY;
import static com.yanchware.fractal.sdk.domain.entities.environment.EnvironmentConstants.REGION_PARAM_KEY;

public class GcpCloudAgent extends CloudAgent {
    public static final String ORGANIZATION_ID_PARAM_KEY = "organizationId";
    public static final String PROJECT_ID_PARAM_KEY = "projectId";

    private final GcpRegion region;
    private final String organizationId;
    private final String projectId;

    public GcpCloudAgent(GcpRegion region, String organizationId, String projectId) {
        this.region = region;
        this.organizationId = organizationId;
        this.projectId = projectId;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.GCP;
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
                ORGANIZATION_ID_PARAM_KEY, organizationId,
                PROJECT_ID_PARAM_KEY, projectId
        ));

    }
}
