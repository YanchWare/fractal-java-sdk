package com.yanchware.fractal.sdk;

import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureNodePool;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

import java.net.http.HttpClient;
import java.util.List;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureMachineType.STANDARD_B2S;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureOsType.LINUX;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureRegion.EUROPE_WEST;

public class LiveSystemFirstTest {

    //@Rule public WireMockRule wireMockRule = new WireMockRule(8090);

    @BeforeEach
    public void setUp() {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        SdkConfiguration sdkConfiguration = new LocalSdkConfiguration();
        Automaton.initializeAutomaton(httpClient, sdkConfiguration);
    }

    @Test
    @SetEnvironmentVariable(key = "CLIENT_ID", value = "xxx")
    @SetEnvironmentVariable(key = "CLIENT_SECRET", value = "xxxx")
    public void liveSystemInstantiated_when_AutomatonCalledWithValidLiveSystemInformation() throws InstantiatorException {
        var env = Environment.builder()
          .id("2251bad7-45a2-4202-a233-cc021be0b1f9")
          .displayName("Business Platform Test")
          .parentId("2e114308-14ec-4d77-b610-490324fa1844")
          .parentType("tenant")
          .build();

        var aks = AzureKubernetesService.builder()
          .id(ComponentId.from("aks-1"))
          .description("Business Platform AKS cluster")
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
          .name("business-platform-test")
          .description("Athena Business platform")
          .resourceGroupId("xxx")
          .component(aks)
          .environment(env)
          .build();

        Automaton.instantiate(List.of(liveSystem));
    }

}