package com.yanchware.fractal.sdk.domain.environment.oci;

import com.yanchware.fractal.sdk.domain.environment.service.dtos.InitializationRunResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci.OciRegion;
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
public class OciCloudAgent extends CloudAgentEntity {
    public static final String TENANCY_ID_PARAM_KEY = "tenancyId";
    public static final String COMPARTMENT_ID_PARAM_KEY = "compartmentId";

    private final OciRegion region;
    private final String tenancyId;
    private final String compartmentId;

    public OciCloudAgent(
            EnvironmentIdValue environmentId,
            OciRegion region,
            String tenancyId,
            String compartmentId,
            Map<String, String> tags)
    {
        super(environmentId, tags);
        this.region = region;
        this.tenancyId = tenancyId;
        this.compartmentId = compartmentId;
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
        var currentInitialization = environmentService.fetchCurrentOciInitialization(environmentId);

        if (currentInitialization == null ||
                "Failed".equals(currentInitialization.status()) ||
                "Cancelled".equals(currentInitialization.status())) {

            environmentService.startOciCloudAgentInitialization(
                    environmentId,
                    tenancyId,
                    compartmentId,
                    region,
                    tags);

            log.info("New initialization started, checking initialization status for environment [id: '{}']", environmentId);
            checkInitializationStatus(() -> fetchCurrentOciInitialization(environmentService));
        } else {
            checkInitializationStatus(() -> fetchCurrentOciInitialization(environmentService));
        }
    }
    @Override
    protected Map<String, Object> getConfigurationForEnvironmentParameters() {
        return Map.of(
                PROVIDER_PARAM_KEY, getProvider(),
                REGION_PARAM_KEY, region,
                TENANCY_ID_PARAM_KEY, tenancyId,
                COMPARTMENT_ID_PARAM_KEY, compartmentId
        );
    }

    private InitializationRunResponse fetchCurrentOciInitialization(EnvironmentService environmentService) {
        try {
            return environmentService.fetchCurrentOciInitialization(environmentId);
        } catch (InstantiatorException e) {
            throw new RuntimeException(e);
        }
    }
}
