package com.yanchware.fractal.sdk.utils;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureNodePool;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureMachineType.STANDARD_B2S;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureOsType.LINUX;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureRegion.EUROPE_WEST;

public class TestUtils {

    public static AzureKubernetesService getAksExample() {
        return AzureKubernetesService.builder()
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
    }

    public static void stubWireMockForLiveSystem(String url) {
        stubFor(post(urlPathMatching(url))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));
    }

    public static void stubWireMockForBlueprints(String url) {
        stubFor(get(urlPathMatching(url))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));
        stubFor(put(urlPathMatching(url))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));
        stubFor(post(urlPathMatching(url))
                .willReturn(aResponse()
                        .withStatus(202)
                        .withHeader("Content-Type", "application/json")));
    }
}
