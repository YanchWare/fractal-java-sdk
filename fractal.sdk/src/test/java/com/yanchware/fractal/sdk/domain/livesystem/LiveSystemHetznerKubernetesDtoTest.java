package com.yanchware.fractal.sdk.domain.livesystem;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.hetzner.HetznerKubernetes;
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

class LiveSystemHetznerKubernetesDtoTest extends LiveSystemKubernetesComponentDtoTest {
    @Test
    public void liveSystemComponentDto_matches_liveSystemComponents_withCorrectTypeForLiveSystem_forHetznerKubernetes() {
        var factory = new LiveSystemsFactory(
                HttpClient.newBuilder().build(),
                new LocalSdkConfiguration(""),
                RetryRegistry.ofDefaults());
        var hetznerKubernetes = TestUtils.getHetznerKubernetesExample();
        var postgres = TestUtils.getGcpPostgresExample();
        var liveSystem = factory.builder()
                .withId(new LiveSystemIdValue(new ResourceGroupId(ResourceGroupType.PERSONAL, UUID.randomUUID(), "rg"), "test"))
                .withStandardProvider(ProviderType.HETZNER)
                .withComponents(List.of(hetznerKubernetes, postgres))
                .build();

        var lsDtoMap  = liveSystem.blueprintMapFromLiveSystemComponents();
        assertHetznerKubernetes(hetznerKubernetes, lsDtoMap);
        assertCaaSComponents(hetznerKubernetes, lsDtoMap);
    }

    private void assertHetznerKubernetes(HetznerKubernetes hetznerKubernetes, Map<String, LiveSystemComponentDto> lsDtoMap) {
        var dto = lsDtoMap.get(hetznerKubernetes.getId().getValue());
        assertGenericComponent(dto, hetznerKubernetes, ComponentType.PAAS_KUBERNETES.getId());
        assertThat(dto.getProvider()).isEqualTo(hetznerKubernetes.getProvider());
        assertThat(dto.getParameters())
                .extracting(
                        "podIpRange",
                        "priorityClasses",
                        "hetznerRegion",
                        "serviceIpRange")
                .containsExactly(
                        hetznerKubernetes.getPodIpRange(),
                        hetznerKubernetes.getPriorityClasses(),
                        hetznerKubernetes.getHetznerRegion(),
                        hetznerKubernetes.getServiceIpRange());
    }
}