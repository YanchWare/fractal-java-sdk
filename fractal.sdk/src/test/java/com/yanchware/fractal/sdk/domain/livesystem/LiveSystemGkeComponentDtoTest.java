package com.yanchware.fractal.sdk.domain.livesystem;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GoogleKubernetesEngine;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.LiveSystemComponentDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.LiveSystemKubernetesComponentDtoTest;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import com.yanchware.fractal.sdk.utils.TestUtils;
import com.yanchware.fractal.sdk.domain.values.ComponentType;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.TestUtils.assertGenericComponent;
import static org.assertj.core.api.Assertions.assertThat;

class LiveSystemGkeComponentDtoTest extends LiveSystemKubernetesComponentDtoTest {
    @Test
    public void liveSystemComponentDto_matches_liveSystemComponents_withCorrectTypeForLiveSystem_forGke() {
        var factory = new LiveSystemsFactory(
                HttpClient.newBuilder().build(),
                new LocalSdkConfiguration(""),
                RetryRegistry.ofDefaults());
        var gke = TestUtils.getGkeExample();
        var postgres = TestUtils.getGcpPostgresExample();
        var liveSystem = factory.builder()
                .withResourceGroupId("test")
                .withName("test")
                .withComponents(List.of(gke, postgres))
                .build();

        var lsDtoMap  = liveSystem.blueprintMapFromLiveSystemComponents();
        assertGke(gke, lsDtoMap);
        assertCaaSComponents(gke, lsDtoMap);
    }

    private void assertGke(GoogleKubernetesEngine gke, Map<String, LiveSystemComponentDto> lsDtoMap) {
        var dto = lsDtoMap.get(gke.getId().getValue());
        assertGenericComponent(dto, gke, ComponentType.PAAS_KUBERNETES.getId());
        assertThat(dto.getProvider()).isEqualTo(gke.getProvider());
        assertThat(dto.getParameters())
                .extracting(
                        "networkName",
                        "nodePools",
                        "podIpRange",
                        "podsRangeName",
                        "priorityClasses",
                        "region",
                        "serviceIpRange",
                        "servicesRangeName",
                        "subnetworkIpRange",
                        "subnetworkName")
                .containsExactly(
                        gke.getNetworkName(),
                        gke.getNodePools(),
                        gke.getPodIpRange(),
                        gke.getPodsRangeName(),
                        gke.getPriorityClasses(),
                        gke.getRegion().getId(),
                        gke.getServiceIpRange(),
                        gke.getServicesRangeName(),
                        gke.getSubnetworkIpRange(),
                        gke.getSubnetworkName());
    }
}