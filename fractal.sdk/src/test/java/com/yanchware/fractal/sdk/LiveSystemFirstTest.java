package com.yanchware.fractal.sdk;

import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.KubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureNodePool;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.gcp.GcpNodePool;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.gcp.GoogleKubernetesEngine;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.http.HttpClient;
import java.util.List;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureMachineType.STANDARD_B2S;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureOsType.LINUX;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureRegion.EUROPE_WEST;
import static org.assertj.core.api.Assertions.assertThat;

public class LiveSystemFirstTest {

    //@Rule public WireMockRule wireMockRule = new WireMockRule(8090);

    @Before
    public void setUp() {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        SdkConfiguration sdkConfiguration = new LocalSdkConfiguration();
        Automaton.initializeAutomaton(httpClient, sdkConfiguration);
    }

    @Test
    @Ignore
    public void liveSystemInstantiated_when_AutomatonCalledWithValidLiveSystemInformation() throws InstantiatorException {
        var gke = GoogleKubernetesEngine.builder()
                .service(KubernetesService.builder()
                        .id(ComponentId.from("caas-1"))
                        .build())
                .id(ComponentId.from("kube-1"))
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
                .description("LiveSystemDescription")
                .resourceGroupId("rsGroupId")
                .component(aks)
                .environment(env)
                .build();//check at build time if you have correct info, if not, generate it if possible

        assertThat(liveSystem.validate()).isEmpty();

        //TestUtils.stubWireMockForBlueprints("/blueprint/rsGroupId/ls-id/1.0");
        //TestUtils.stubWireMockForLiveSystem("/livesystem/resource-group/livesystems");
        Automaton.instantiate(List.of(liveSystem));
    }

}