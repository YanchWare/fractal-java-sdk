package com.yanchware.fractal.sdk.domain.environment.gcp;

import com.yanchware.fractal.sdk.domain.environment.service.dtos.InitializationRunResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.environment.CloudAgentEntity;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.yanchware.fractal.sdk.domain.environment.EnvironmentAggregate.REGION_PARAM_KEY;

@Slf4j
@Getter
public class GcpCloudAgent extends CloudAgentEntity {
    public static final String ORGANIZATION_ID_PARAM_KEY = "organizationId";
    public static final String PROJECT_ID_PARAM_KEY = "projectId";

    private final GcpRegion region;
    private final String organizationId;
    private final String projectId;

    public GcpCloudAgent(
            EnvironmentIdValue environmentId,
            GcpRegion region,
            String organizationId,
            String projectId,
            Map<String, String> tags)
    {
        super(environmentId, tags);
        this.region = region;
        this.organizationId = organizationId;
        this.projectId = projectId;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.GCP;
    }

    @Override
    public void initialize(EnvironmentService environmentService) throws InstantiatorException {
        initialize(environmentService, null);
    }

    @Override
    public void initialize(EnvironmentService environmentService, EnvironmentIdValue managementEnvironmentId) throws InstantiatorException {
        var currentInitialization = environmentService.fetchCurrentGcpInitialization(environmentId);

        if (currentInitialization == null ||
                "Failed".equals(currentInitialization.status()) ||
                "Cancelled".equals(currentInitialization.status())) {

            environmentService.startGcpCloudAgentInitialization(
                    environmentId,
                    organizationId,
                    projectId,
                    region,
                    tags);

            log.info("New initialization started, checking initialization status for environment [id: '{}']", environmentId);
            checkInitializationStatus(() -> fetchCurrentGcpInitialization(environmentService));
        } else {
            checkInitializationStatus(() -> fetchCurrentGcpInitialization(environmentService));
        }
    }

    @Override
    protected Map<String, Object> getConfigurationForEnvironmentParameters() {
        return Map.of(
                REGION_PARAM_KEY, region,
                ORGANIZATION_ID_PARAM_KEY, organizationId,
                PROJECT_ID_PARAM_KEY, projectId
        );
    }

    private InitializationRunResponse fetchCurrentGcpInitialization(EnvironmentService environmentService) {
        try {
            return environmentService.fetchCurrentGcpInitialization(environmentId);
        } catch (InstantiatorException e) {
            throw new RuntimeException(e);
        }
    }
}
