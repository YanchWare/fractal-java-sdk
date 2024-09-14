package com.yanchware.fractal.sdk.domain.environment.oci;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.oci.OciRegion;
import com.yanchware.fractal.sdk.domain.environment.CloudAgentEntity;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;

import java.util.Map;

import static com.yanchware.fractal.sdk.domain.entities.environment.EnvironmentConstants.REGION_PARAM_KEY;

@Getter
public class OciCloudAgent extends CloudAgentEntity {
    public static final String TENANCY_ID_PARAM_KEY = "tenancyId";
    public static final String COMPARTMENT_ID_PARAM_KEY = "compartmentId";

    private final OciRegion region;
    private final String tenancyId;
    private final String compartmentId;

    public OciCloudAgent(
            EnvironmentIdValue environmentId,
            EnvironmentService environmentService,
            OciRegion region,
            String tenancyId,
            String compartmentId,
            Map<String, String> tags)
    {
        super(environmentId, environmentService, tags);
        this.region = region;
        this.tenancyId = tenancyId;
        this.compartmentId = compartmentId;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.OCI;
    }

    @Override
    public void initialize() {
        // TODO: implement
    }

    @Override
    protected Map<String, Object> getConfigurationForEnvironmentParameters() {
        return Map.of(
                REGION_PARAM_KEY, region,
                TENANCY_ID_PARAM_KEY, tenancyId,
                COMPARTMENT_ID_PARAM_KEY, compartmentId
        );
    }
}
