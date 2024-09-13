package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.aws;

import com.yanchware.fractal.sdk.aggregates.CloudAgent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.oci.OciRegion;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.entities.environment.EnvironmentConstants.CLOUD_AGENTS_PARAM_KEY;
import static com.yanchware.fractal.sdk.domain.entities.environment.EnvironmentConstants.REGION_PARAM_KEY;

public class AwsCloudAgent extends CloudAgent {
    public static final String ORGANIZATION_ID_PARAM_KEY = "organizationId";
    public static final String ACCOUNT_ID_PARAM_KEY = "accountId";

    private final AwsRegion region;
    private final String organizationId;
    private final String accountId;

    public AwsCloudAgent(AwsRegion region, String organizationId, String accountId) {
        this.region = region;
        this.organizationId = organizationId;
        this.accountId = accountId;
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.AWS;
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
                ACCOUNT_ID_PARAM_KEY, accountId
        ));

    }
}
