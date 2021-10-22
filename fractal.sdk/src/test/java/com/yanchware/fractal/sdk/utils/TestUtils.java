package com.yanchware.fractal.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.domain.entities.ComponentLink;
import com.yanchware.fractal.sdk.domain.entities.livesystem.KafkaCluster;
import com.yanchware.fractal.sdk.domain.entities.livesystem.KafkaTopic;
import com.yanchware.fractal.sdk.domain.entities.livesystem.KafkaUser;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureNodePool;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzurePostgreSQL;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzurePostgreSQLDB;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureMachineType.STANDARD_B2S;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureOsType.LINUX;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureRegion.EUROPE_WEST;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureSkuName.B_GEN5_1;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureStorageAutoGrow.ENABLED;

@Slf4j
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
                .withNodePool(AzureNodePool.builder().
                        name("aks-node-pool").
                        diskSizeGb(35).
                        machineType(STANDARD_B2S).
                        maxNodeCount(3).
                        maxSurge(1).
                        minNodeCount(1).
                        maxPodsPerNode(100).
                        osType(LINUX).
                        build())
                .withKafkaCluster(getKafkaClusterExample())
                .build();
    }

    private static KafkaCluster getKafkaClusterExample() {
        return KafkaCluster.builder()
                .id(ComponentId.from("azure-kafka"))
                .description("Kafka for Azure")
                .displayName("AzureKafka #1")
                .namespace("namespace")
                .withKafkaTopics(List.of(
                        KafkaTopic.builder().id(ComponentId.from("topic")).displayName("kafka-topic").build(),
                        KafkaTopic.builder().id(ComponentId.from("topic-2")).displayName("kafka-topic-2").build()))
                .withKafkaUsers(List.of(
                        KafkaUser.builder().id(ComponentId.from("user-1")).displayName("kafka-user").withTopicReadACL("svcName").build(),
                        KafkaUser.builder().id(ComponentId.from("user-2")).displayName("kafka-user-2").build()))
                .build();
    }

    public static AzurePostgreSQL getAzurePostgresExample() {
        HashMap<String, Object> linkSettings = new HashMap<>();
        linkSettings.put("roles", List.of("roles/micro", "roles/service"));
        linkSettings.put("subscribe", true);

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
                .withDatabase(AzurePostgreSQLDB.builder().id(ComponentId.from("db-1")).displayName("db-1").name("db").build())
                .withDatabase(AzurePostgreSQLDB.builder()
                        .id(ComponentId.from("db-2"))
                        .displayName("db-2")
                        .name("db2")
                        .withLink(getComponentLink())
                        .build())
                .build();
    }

    public static Environment getEnvExample() {
        return Environment.builder()
                .withId("2251bad7-45a2-4202-a233-cc021be0b1f9")
                .withDisplayName("Business Platform Test")
                .withParentId("2e114308-14ec-4d77-b610-490324fa1844")
                .withParentType("tenant")
                .build();
    }

    public static LiveSystem getLiveSystemExample() {
        return LiveSystem.builder()
                .name("business-platform-test")
                .description("Business platform")
                .resourceGroupId("xxx")
                .withComponent(getAksExample())
                .withComponent(getAzurePostgresExample())
                .environment(getEnvExample())
                .build();
    }

    public static ComponentLink getComponentLink() {
        HashMap<String, Object> linkSettings = new HashMap<>();
        linkSettings.put("roles", List.of("roles/micro", "roles/service"));
        linkSettings.put("subscribe", true);

        return ComponentLink.builder()
                .withComponentId("microservices")
                .withSettings(linkSettings)
                .build();
    }

    public static String getJsonRepresentation(Collection<? extends ComponentDto> components) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonRep = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(components);
            log.debug("Json Map: {}", jsonRep);
            return jsonRep;
        } catch (JsonProcessingException e) {
            log.error("Error when trying to process component: {}", components, e);
        }
        return null;
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
