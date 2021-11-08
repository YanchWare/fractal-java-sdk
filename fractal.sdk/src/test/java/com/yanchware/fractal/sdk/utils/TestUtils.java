package com.yanchware.fractal.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.ComponentLink;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.*;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureNodePool;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzurePostgreSQL;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzurePostgreSQLDB;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.KafkaCluster;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.KafkaTopic;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.KafkaUser;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;

import java.util.HashMap;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureMachineType.STANDARD_B2S;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureOsType.LINUX;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureRegion.EUROPE_WEST;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureSkuName.B_GEN5_1;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureStorageAutoGrow.ENABLED;

@Slf4j
public class TestUtils {

    public static AzureKubernetesService getAksExample() {
        return AzureKubernetesService.builder()
                .withId("aks-1")
                .withDescription("Test AKS cluster")
                .withDisplayName("AKS #1")
                .region(EUROPE_WEST)
                .network("network-host")
                .subNetwork("compute-tier-1")
                .podsRange("tier-1-pods")
                .serviceRange("tier-1-services")
                .withNodePool(AzureNodePool.builder()
                        .name("aks-node-pool")
                        .diskSizeGb(35)
                        .machineType(STANDARD_B2S)
                        .maxNodeCount(3)
                        .maxSurge(1)
                        .minNodeCount(1)
                        .maxPodsPerNode(100)
                        .osType(LINUX)
                        .build())
                .withService(getK8sServiceExample())
                .withKafkaCluster(getKafkaClusterExample())
                .withPrometheus(getPrometheusExample())
                .withAmbassador(getAmbassadorExample())
                .build();
    }

    public static KubernetesService getK8sServiceExample() {
        return KubernetesService.builder()
                .withId("fractal-svc")
                .withDescription("Fractal Service on K8S")
                .withDisplayName("Fractal SVC")
                .withNamespace("fractal")
                .withPrivateSSHKeyPassphraseSecretId("fractal-private-passphrase")
                .withPrivateSSHKeySecretId("fractal-private-ssh")
                .withPublicSSHKey("public-ssh")
                .withSshRepositoryURI("ssh-uri")
                .withRepoId("fractal-svc-id")
                .withRoles(List.of("roles/datastore.user", "roles/pubsub.editor"))
                .build();
    }

    public static Prometheus getPrometheusExample() {
        return Prometheus.builder()
                .withId("prometheus")
                .withDescription("Prometheus monitoring")
                .withDisplayName("Prometheus")
                .withNamespace("monitoring")
                .build();
    }

    public static Ambassador getAmbassadorExample() {
        return Ambassador.builder()
                .withId("ambassador")
                .withDescription("Ambassador")
                .withDisplayName("Ambassador")
                .withNamespace("ambassador")
                .build();
    }

    public static KafkaCluster getKafkaClusterExample() {
        return KafkaCluster.builder()
                .withId("azure-kafka")
                .withDescription("Kafka for Azure")
                .withDisplayName("AzureKafka #1")
                .withNamespace("namespace")
                .withKafkaTopics(List.of(
                        KafkaTopic.builder().withId("topic").withDisplayName("kafka-topic").build(),
                        KafkaTopic.builder().withId("topic-2").withDisplayName("kafka-topic-2").build()))
                .withKafkaUsers(List.of(
                        KafkaUser.builder().withId("user-1").withDisplayName("kafka-user").withTopicReadACL("svcName").build(),
                        KafkaUser.builder().withId("user-2").withDisplayName("kafka-user-2").build()))
                .build();
    }

    public static AzurePostgreSQL getAzurePostgresExample() {
        return AzurePostgreSQL.builder()
                .withId("dbpg")
                .withDescription("PostgreSQL")
                .withDisplayName("PostgreSQL")
                .region(EUROPE_WEST)
                .rootUser("rootUser")
                .skuName(B_GEN5_1)
                .storageAutoGrow(ENABLED)
                .storageMB(5 * 1024)
                .backupRetentionDays(12)
                .withDatabase(AzurePostgreSQLDB.builder().withId("db-1").withDisplayName("db-1").name("db").build())
                .withDatabase(AzurePostgreSQLDB.builder()
                        .withId("db-2")
                        .withDisplayName("db-2")
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
                .withName("business-platform-test")
                .withDescription("Business platform")
                .withResourceGroupId("xxx")
                .withComponent(getAksExample())
                .withComponent(getAzurePostgresExample())
                .withEnvironment(getEnvExample())
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

    public static String getJsonRepresentation(Object obj) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Error when trying to process: {}", obj, e);
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

    public static void assertGenericComponent(ComponentDto componentDto, Component comp, String type) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(componentDto.getId()).as("Component ID").isEqualTo(comp.getId().getValue());
            softly.assertThat(componentDto.getDisplayName()).as("Component Display Name").isEqualTo(comp.getDisplayName());
            softly.assertThat(componentDto.getDescription()).as("Component Description").contains(comp.getDescription());
            softly.assertThat(componentDto.getType()).as("Component Type").isEqualTo(type);
            softly.assertThat(componentDto.getVersion()).as("Component Version").isEqualTo(DEFAULT_VERSION);
        });
    }
}
