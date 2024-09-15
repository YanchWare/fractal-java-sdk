package com.yanchware.fractal.sdk.domain.environment.aws;

import com.yanchware.fractal.sdk.domain.environment.service.dtos.InitializationRunResponse;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsRegion;
import com.yanchware.fractal.sdk.domain.environment.CloudAgentEntity;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.yanchware.fractal.sdk.domain.environment.EnvironmentAggregate.REGION_PARAM_KEY;

@Slf4j
@Getter
public class AwsCloudAgent extends CloudAgentEntity {
    public static final String ORGANIZATION_ID_PARAM_KEY = "organizationId";
    public static final String ACCOUNT_ID_PARAM_KEY = "accountId";

    private final AwsRegion region;
    private final String organizationId;
    private final String accountId;

    public AwsCloudAgent(
            EnvironmentIdValue environmentId,
            EnvironmentService environmentService,
            AwsRegion region,
            String organizationId,
            String accountId,
            Map<String, String> tags)
    {
        super(environmentId, environmentService, tags);
        this.region = region;
        this.organizationId = organizationId;
        this.accountId = accountId;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.AWS;
    }

    @Override
    public void initialize() throws InstantiatorException {
        var currentInitialization = environmentService.fetchCurrentAwsInitialization(environmentId);

        if (currentInitialization == null ||
                "Failed".equals(currentInitialization.status()) ||
                "Cancelled".equals(currentInitialization.status())) {

            environmentService.startAwsCloudAgentInitialization(
                    environmentId,
                    organizationId,
                    accountId,
                    region,
                    tags);

            log.info("New initialization started, checking initialization status for environment [id: '{}']", environmentId);
            checkInitializationStatus(this::fetchCurrentAwsInitialization);
        } else {
            log.info("Checking initialization status for environment [id: '{}']", environmentId);
            checkInitializationStatus(this::fetchCurrentAwsInitialization);
        }
    }

    @Override
    protected Map<String, Object> getConfigurationForEnvironmentParameters() {
        return Map.of(
                REGION_PARAM_KEY, region,
                ORGANIZATION_ID_PARAM_KEY, organizationId,
                ACCOUNT_ID_PARAM_KEY, accountId
        );
    }

    private InitializationRunResponse fetchCurrentAwsInitialization() {
        try {
            return environmentService.fetchCurrentAwsInitialization(environmentId);
        } catch (InstantiatorException e) {
            throw new RuntimeException(e);
        }
    }
}
