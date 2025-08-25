package com.yanchware.fractal.sdk.domain.livesystem;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsElasticKubernetesService;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.LiveSystemComponentDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.LiveSystemKubernetesComponentDtoTest;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.domain.values.ComponentType;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupType;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import com.yanchware.fractal.sdk.utils.TestUtils;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.yanchware.fractal.sdk.utils.TestUtils.assertGenericComponent;
import static org.assertj.core.api.Assertions.assertThat;

class LiveSystemEksComponentDtoTest extends LiveSystemKubernetesComponentDtoTest {
    @Test
    public void liveSystemComponentDto_matches_liveSystemComponents_withCorrectTypeForLiveSystem_forEks() {
        var factory = new LiveSystemsFactory(
                HttpClient.newBuilder().build(),
                new LocalSdkConfiguration(""),
                RetryRegistry.ofDefaults());
        var eks = TestUtils.getEksExample();
        var postgres = TestUtils.getGcpPostgresExample();
        var liveSystem = factory.builder()
                .withId(new LiveSystemIdValue(new ResourceGroupId(ResourceGroupType.PERSONAL, UUID.randomUUID(), "rg"), "test"))
                .withStandardProvider(ProviderType.AWS)
                .withComponents(List.of(eks, postgres))
                .build();

        var lsDtoMap  = liveSystem.blueprintMapFromLiveSystemComponents();
        assertEks(eks, lsDtoMap);
        assertCaaSComponents(eks, lsDtoMap);
    }

    private void assertEks(AwsElasticKubernetesService eks, Map<String, LiveSystemComponentDto> lsDtoMap) {
        var dto = lsDtoMap.get(eks.getId().getValue());
        assertGenericComponent(dto, eks, ComponentType.PAAS_KUBERNETES.getId());
        assertThat(dto.getProvider()).isEqualTo(eks.getProvider());
        assertThat(dto.getParameters())
                .extracting(
                        "podIpRange",
                        "priorityClasses",
                        "awsRegion",
                        "serviceIpRange")
                .containsExactly(
                        eks.getPodIpRange(),
                        eks.getPriorityClasses(),
                        eks.getAwsRegion(),
                        eks.getServiceIpRange());
    }
}