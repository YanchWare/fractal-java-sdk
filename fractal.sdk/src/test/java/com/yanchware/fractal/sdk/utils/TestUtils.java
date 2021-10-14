package com.yanchware.fractal.sdk.utils;

import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureNodePool;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzurePostgreSQL;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzurePostgreSQLDB;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureMachineType.STANDARD_B2S;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureOsType.LINUX;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureSkuName.B_GEN5_1;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureRegion.EUROPE_WEST;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureStorageAutoGrow.ENABLED;

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

    public static AzurePostgreSQL getAzurePostgresExample() {
        return AzurePostgreSQL.builder()
                .id(ComponentId.from("dbpg"))
                .description("PostgreSQL")
                .displayName("PostgreSQL")
                .region(EUROPE_WEST)
                .rootUser("rootUser")
                .skuName(B_GEN5_1)
                .storageAutoGrow(ENABLED)
                .storageMB(5 * 1024)
                .backupRetentionDays(12)
                .database(AzurePostgreSQLDB.builder().id(ComponentId.from("db-1")).name("db").build())
                .database(AzurePostgreSQLDB.builder().id(ComponentId.from("db-2")).name("db2").build())
                .build();
    }

    public static Environment getEnvExample() {
        return Environment.builder()
                .id("2251bad7-45a2-4202-a233-cc021be0b1f9")
                .displayName("Business Platform Test")
                .parentId("2e114308-14ec-4d77-b610-490324fa1844")
                .parentType("tenant")
                .build();
    }

    public static LiveSystem getLiveSystemExample() {
        return LiveSystem.builder()
                .name("business-platform-test")
                .description("Business platform")
                .resourceGroupId("xxx")
                .component(getAksExample())
                .component(getAzurePostgresExample())
                .environment(getEnvExample())
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
