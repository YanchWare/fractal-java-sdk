package com.yanchware.fractal.sdk.domain.environment.aws;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.aws.AwsRegion;
import com.yanchware.fractal.sdk.domain.environment.CloudAgentEntity;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;

import java.util.Map;

import static com.yanchware.fractal.sdk.domain.entities.environment.EnvironmentConstants.REGION_PARAM_KEY;

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
        // TODO: implement
    }

    @Override
    protected Map<String, Object> getConfigurationForEnvironmentParameters() {
        return Map.of(
                REGION_PARAM_KEY, region,
                ORGANIZATION_ID_PARAM_KEY, organizationId,
                ACCOUNT_ID_PARAM_KEY, accountId
        );
    }
}
