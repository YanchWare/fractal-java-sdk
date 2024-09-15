package com.yanchware.fractal.sdk.domain.environment.gcp;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.environment.CloudAgentEntity;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;

import java.util.Map;

import static com.yanchware.fractal.sdk.domain.environment.EnvironmentAggregate.REGION_PARAM_KEY;

@Getter
public class GcpCloudAgent extends CloudAgentEntity {
    public static final String ORGANIZATION_ID_PARAM_KEY = "organizationId";
    public static final String PROJECT_ID_PARAM_KEY = "projectId";

    private final GcpRegion region;
    private final String organizationId;
    private final String projectId;

    public GcpCloudAgent(
            EnvironmentIdValue environmentId,
            EnvironmentService environmentService,
            GcpRegion region,
            String organizationId,
            String projectId,
            Map<String, String> tags)
    {
        super(environmentId, environmentService, tags);
        this.region = region;
        this.organizationId = organizationId;
        this.projectId = projectId;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.GCP;
    }

    @Override
    public void initialize() {
        // TODO: implement
    }

    @Override
    protected Map<String, Object> getConfigurationForEnvironmentParameters() {
        return Map.of(
                REGION_PARAM_KEY, region,
                ORGANIZATION_ID_PARAM_KEY, organizationId,
                PROJECT_ID_PARAM_KEY, projectId
        );
    }
}
