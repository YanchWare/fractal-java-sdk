package com.yanchware.fractal.sdk.domain.environment.hetzner;

import com.yanchware.fractal.sdk.domain.environment.CloudAgentEntity;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.InitializationRunResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.hetzner.HetznerRegion;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

import static com.yanchware.fractal.sdk.domain.environment.EnvironmentAggregate.REGION_PARAM_KEY;

@Slf4j
@Getter
public class HetznerCloudAgent extends CloudAgentEntity {
    public static final String PROJECT_ID_PARAM_KEY = "projectId";

    private final HetznerRegion region;
    private final String projectId;

    public HetznerCloudAgent(
            EnvironmentIdValue environmentId,
            HetznerRegion region,
            String projectId,
            Map<String, String> tags)
    {
        super(environmentId, tags);
        this.region = region;
        this.projectId = projectId;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.OCI;
    }

    @Override
    public void initialize(EnvironmentService environmentService) throws InstantiatorException {
        initialize(environmentService, null);
    }

    @Override
    public void initialize(EnvironmentService environmentService, EnvironmentIdValue managementEnvironmentId) throws InstantiatorException {
        var currentInitialization = environmentService.fetchCurrentHetznerInitialization(environmentId);

        if (currentInitialization == null ||
                "Failed".equals(currentInitialization.status()) ||
                "Cancelled".equals(currentInitialization.status())) {

            environmentService.startHetznerCloudAgentInitialization(
                    environmentId,
                    projectId,
                    region,
                    tags);

            log.info("New initialization started, checking initialization status for environment [id: '{}']", environmentId);
            checkInitializationStatus(() -> fetchCurrentHetznerInitialization(environmentService));
        } else {
            checkInitializationStatus(() -> fetchCurrentHetznerInitialization(environmentService));
        }
    }
    @Override
    protected Map<String, Object> getConfigurationForEnvironmentParameters() {
        return Map.of(
                PROVIDER_PARAM_KEY, getProvider(),
                REGION_PARAM_KEY, region,
                PROJECT_ID_PARAM_KEY, projectId);
    }

    private InitializationRunResponse fetchCurrentHetznerInitialization(EnvironmentService environmentService) {
        try {
            return environmentService.fetchCurrentHetznerInitialization(environmentId);
        } catch (InstantiatorException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HetznerCloudAgent member = (HetznerCloudAgent) o;

      return region.equals(member.region)
        && projectId.equals(member.projectId)
        && environmentId.equals(member.environmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(region, projectId, environmentId);
    }
}
