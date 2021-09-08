package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureNodePool;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSContainerPlatform.TYPE;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureMachineType.STANDARD_B2S;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureOsType.LINUX;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureRegion.EUROPE_WEST;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KUBERNETES;

public class BlueprintComponentDtoTest {

    @Test
    public void blueprintComponentValid_when_liveSystemComponentConverted() {
        var aks = AzureKubernetesService.builder()
                .id(ComponentId.from("aks-1"))
                .type(KUBERNETES)
                .description("Test AKS cluster")
                .displayName("AKS #1")
                .region(EUROPE_WEST)
                .network("network-host")
                .subNetwork("compute-tier-1")
                .podsRange("tier-1-pods")
                .serviceRange("tier-1-services")
                .nodePool(AzureNodePool.builder().
                        name("aks-node-pool").
                        diskSizeGb(35).
                        machineType(STANDARD_B2S).
                        maxNodeCount(3).
                        maxSurge(1).
                        minNodeCount(1).
                        maxPodsPerNode(100).
                        osType(LINUX).
                        build())
                .build();

        BlueprintComponentDto blueprintComponentDto = BlueprintComponentDto.fromLiveSystemComponent(aks);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(blueprintComponentDto.getId()).as("Component ID").isEqualTo(aks.getId().getValue());
            softly.assertThat(blueprintComponentDto.getDisplayName()).as("Component Display Name").isEqualTo(aks.getDisplayName());
            softly.assertThat(blueprintComponentDto.getDescription()).as("Component Description").isEqualTo(aks.getDescription());
            softly.assertThat(blueprintComponentDto.getType()).as("Component Type").isEqualTo(TYPE);
            softly.assertThat(blueprintComponentDto.getVersion()).as("Component Version").isEqualTo("0.0.1");
            softly.assertThat(blueprintComponentDto.getParameters().values()).as("Component Parameters").contains(aks.getNetwork());
            softly.assertThat(blueprintComponentDto.getDependencies()).as("Component Dependencies").isEmpty();
            softly.assertThat(blueprintComponentDto.getLinks()).as("Component Links").isEmpty();
            softly.assertThat(blueprintComponentDto.getOutputFields()).as("Component Output Fields").isEmpty();
        });
    }

}