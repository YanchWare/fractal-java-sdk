package com.yanchware.fractal.sdk.domain.livesystem;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci.OciContainerEngineForKubernetes;
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

class LiveSystemOkeComponentDtoTest extends LiveSystemKubernetesComponentDtoTest {
    @Test
    public void liveSystemComponentDto_matches_liveSystemComponents_withCorrectTypeForLiveSystem_forOke() {
        var factory = new LiveSystemsFactory(
                HttpClient.newBuilder().build(),
                new LocalSdkConfiguration(""),
                RetryRegistry.ofDefaults());
        var oke = TestUtils.getOkeExample();
        var postgres = TestUtils.getGcpPostgresExample();
        var liveSystem = factory.builder()
                .withId(new LiveSystemIdValue(new ResourceGroupId(ResourceGroupType.PERSONAL, UUID.randomUUID(), "rg"), "test"))
                .withStandardProvider(ProviderType.OCI)
                .withComponents(List.of(oke, postgres))
                .build();

        var lsDtoMap  = liveSystem.blueprintMapFromLiveSystemComponents();
        assertOke(oke, lsDtoMap);
        assertCaaSComponents(oke, lsDtoMap);
    }

    private void assertOke(OciContainerEngineForKubernetes oke, Map<String, LiveSystemComponentDto> lsDtoMap) {
        var dto = lsDtoMap.get(oke.getId().getValue());
        assertGenericComponent(dto, oke, ComponentType.PAAS_KUBERNETES.getId());
        assertThat(dto.getProvider()).isEqualTo(oke.getProvider());
        assertThat(dto.getParameters())
                .extracting(
                        "podIpRange",
                        "priorityClasses",
                        "ociRegion",
                        "serviceIpRange")
                .containsExactly(
                        oke.getPodIpRange(),
                        oke.getPriorityClasses(),
                        oke.getOciRegion(),
                        oke.getServiceIpRange());
    }
}