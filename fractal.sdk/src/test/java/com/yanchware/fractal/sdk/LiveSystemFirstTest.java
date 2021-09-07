package com.yanchware.fractal.sdk;

import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.KubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureNodePool;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.gcp.GcpNodePool;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.gcp.GoogleKubernetesEngine;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.Test;

import java.util.List;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureMachineType.STANDARD_B2S;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureOsType.LINUX;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureRegion.EUROPE_WEST;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CONTAINER_PLATFORM;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KUBERNETES;
import static org.assertj.core.api.Assertions.assertThat;

public class LiveSystemFirstTest {

    @Test
    public void PositiveTest() throws InstantiatorException {
        var gke = GoogleKubernetesEngine.builder()
                .service(KubernetesService.builder()
                        .id(ComponentId.from("caas-1"))
                        .type(CONTAINER_PLATFORM)
                        .build())
                .id(ComponentId.from("kube-1"))
                .type(KUBERNETES)
                .description("Test GKE cluster")
                .displayName("Kube 1")
                .nodePool(GcpNodePool.builder().name("gcp-node-pool-name").build())
                .build();

        var env = Environment.builder()
                .id("env-id")
                .displayName("PROD")
                .parentId("123456789")
                .parentType("folder")
                .build();

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


        LiveSystem liveSystem = LiveSystem.builder()
                .id("ls-id")
                .component(aks)
                .build();//check at build time if you have correct info, if not, generate it if possible

        assertThat(aks.validate()).isEmpty();
        assertThat(gke.validate()).isEmpty();
        assertThat(liveSystem.validate()).isEmpty();

        //CreateBlueprintCommandRequest.fromLiveSystem(liveSystem.getComponents());

        Automaton.instantiate(List.of(liveSystem));
    }

}