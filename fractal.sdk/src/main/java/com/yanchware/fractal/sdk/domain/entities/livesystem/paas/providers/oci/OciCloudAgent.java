package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.oci;

import com.yanchware.fractal.sdk.aggregates.CloudAgent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.entities.environment.EnvironmentConstants.CLOUD_AGENTS_PARAM_KEY;
import static com.yanchware.fractal.sdk.domain.entities.environment.EnvironmentConstants.REGION_PARAM_KEY;

public class OciCloudAgent extends CloudAgent {
    public static final String TENANCY_ID_PARAM_KEY = "tenancyId";
    public static final String COMPARTMENT_ID_PARAM_KEY = "compartmentId";

    private final OciRegion region;
    private final String tenancyId;
    private final String compartmentId;

    public OciCloudAgent(OciRegion region, String tenancyId, String compartmentId) {
        this.region = region;
        this.tenancyId = tenancyId;
        this.compartmentId = compartmentId;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.OCI;
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
                TENANCY_ID_PARAM_KEY, tenancyId,
                COMPARTMENT_ID_PARAM_KEY, compartmentId
        ));

    }
}
