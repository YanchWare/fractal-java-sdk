package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.aks.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.LiveSystemComponentDto;
import com.yanchware.fractal.sdk.utils.TestUtils;
import com.yanchware.fractal.sdk.domain.values.ComponentType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.TestUtils.assertGenericComponent;
import static org.assertj.core.api.Assertions.assertThat;

class LiveSystemAksComponentDtoTest extends LiveSystemKubernetesComponentDtoTest {

    @Test
    public void liveSystemComponentDto_matches_liveSystemComponents_withCorrectTypeForLiveSystem_forAks() {
        var aks = TestUtils.getAksExample();
        Map<String, LiveSystemComponentDto> lsDtoMap = LiveSystemComponentDto.fromLiveSystemComponents(List.of(aks));
        assertAks(aks, lsDtoMap);
        assertAksNodePool(aks, lsDtoMap);
        assertCaaSComponents(aks, lsDtoMap);
    }

    private void assertAksNodePool(AzureKubernetesService aks, Map<String, LiveSystemComponentDto> lsDtoMap) {
        var dto = lsDtoMap.get(aks.getId().getValue());
        assertThat(dto.getParameters().get("nodePools"))
                .asList().first()
                .hasFieldOrProperty("labels");
    }

    private void assertAks(AzureKubernetesService aks, Map<String, LiveSystemComponentDto> lsDtoMap) {
        var dto = lsDtoMap.get(aks.getId().getValue());
        assertGenericComponent(dto, aks, ComponentType.PAAS_KUBERNETES.getId());
        assertThat(dto.getProvider()).isEqualTo(aks.getProvider());
        assertThat(dto.getParameters())
                .extracting(
                        "addonProfiles",
                        "azureActiveDirectoryProfile",
                        "azureRegion",
                        "externalWorkspaceResourceId",
                        "kubernetesVersion",
                        "nodePools",
                        "outboundIps",
                        "podIpRange",
                        "podManagedIdentity",
                        "priorityClasses",
                        "roles",
                        "serviceIpRange",
                        "tags",
                        "vnetAddressSpaceIpRange",
                        "vnetSubnetAddressIpRange")
                .containsExactly(
                        aks.getAddonProfiles(),
                        aks.getAzureActiveDirectoryProfile(),
                        aks.getAzureRegion(),
                        aks.getExternalWorkspaceResourceId(),
                        aks.getKubernetesVersion(),
                        aks.getNodePools(),
                        aks.getOutboundIps(),
                        aks.getPodIpRange(),
                        aks.getPodManagedIdentity(),
                        aks.getPriorityClasses(),
                        aks.getRoles(),
                        aks.getServiceIpRange(),
                        aks.getTags(),
                        aks.getVnetAddressSpaceIpRange(),
                        aks.getVnetSubnetAddressIpRange());
    }
}