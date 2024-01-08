package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.gcp.GoogleKubernetesEngine;
import com.yanchware.fractal.sdk.utils.TestUtils;
import com.yanchware.fractal.sdk.valueobjects.ComponentType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.TestUtils.assertGenericComponent;
import static org.assertj.core.api.Assertions.assertThat;

class LiveSystemGkeComponentDtoTest extends LiveSystemKubernetesComponentDtoTest {
    @Test
    public void liveSystemComponentDto_matches_liveSystemComponents_withCorrectTypeForLiveSystem_forGke() {
        var gke = TestUtils.getGkeExample();
        var postgres = TestUtils.getGcpPostgresExample();
        Map<String, LiveSystemComponentDto> lsDtoMap = LiveSystemComponentDto.fromLiveSystemComponents(List.of(gke, postgres));
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