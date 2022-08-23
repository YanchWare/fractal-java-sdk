package com.yanchware.fractal.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.ComponentLink;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.*;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureKubernetesService.AzureKubernetesServiceBuilder;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureNodePool;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzurePostgreSQL;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.gcp.GcpNodePool;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.gcp.GcpProgreSQL;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.gcp.GoogleKubernetesEngine;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.gcp.GoogleKubernetesEngine.GoogleKubernetesEngineBuilder;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.PreemptionPolicy.NEVER;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.PreemptionPolicy.PREEMPT_LOWER_PRIORITY;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureMachineType.STANDARD_B2S;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureOsType.LINUX;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureRegion.EUROPE_WEST;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureSkuName.B_GEN5_1;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureStorageAutoGrow.ENABLED;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.gcp.GcpMachine.E2_STANDARD2;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.gcp.GcpRegion.EU_WEST1;
import static java.util.stream.Collectors.toSet;

@Slf4j
public class TestUtils {

  public static AzureKubernetesServiceBuilder getAksBuilder() {
    return AzureKubernetesService.builder()
        .withId("aks-1")
        .withDescription("Test AKS cluster")
        .withDisplayName("AKS #1")
        .withRegion(EUROPE_WEST)
        .withNetwork("network-host")
        .withSubNetwork("compute-tier-1")
        .withPodsRange("tier-1-pods")
        .withServiceRange("tier-1-services")
        .withServiceIpMask("10.2.0.0/16")
        .withPodIpMask("10.3.0.0/16")
        .withVnetAddressSpaceIpMask("10.1.0.0/22")
        .withVnetSubnetAddressIpMask("10.1.0.0/22")
        .withNodePool(AzureNodePool.builder()
            .withName("akslinux")
            .withDiskSizeGb(35)
            .withMachineType(STANDARD_B2S)
            .withMaxNodeCount(3)
            .withInitialNodeCount(1)
            .withMaxSurge(1)
            .withMinNodeCount(1)
            .withMaxPodsPerNode(100)
            .withOsType(LINUX)
            .withAutoscalingEnabled(true)
            .build())
        .withPriorityClass(PriorityClass.builder()
            .withName("fractal-critical")
            .withDescription("Used for Fractal Components")
            .withPreemptionPolicy(PREEMPT_LOWER_PRIORITY)
            .withValue(1_000_000_000)
            .build())
        .withPriorityClass(PriorityClass.builder()
            .withName("fractal-critical.2")
            .withDescription("Used for Fractal Components")
            .withPreemptionPolicy(NEVER)
            .withValue(999_999_000)
            .build())
        .withPodManagedIdentity(PodManagedIdentity.builder()
            .withName("azure-pod-identity")
            .withNamespace("kube-system")
            .withExceptionPodLabels(Map.of("app", "mic", "component", "mic"))
            .withEnable(true)
            .withAllowNetworkPluginKubeNet(true)
            .build());
  }

  public static GoogleKubernetesEngineBuilder getGkeBuilder() {
    return GoogleKubernetesEngine.builder()
        .withId("aks-1")
        .withDescription("Test AKS cluster")
        .withDisplayName("AKS #1")
        .withRegion(EU_WEST1)
        .withNetwork("network-host")
        .withSubNetwork("compute-tier-1")
        .withPodsRange("tier-1-pods")
        .withServiceRange("tier-1-services")
        .withServiceIpMask("10.2.0.0/16")
        .withPodIpMask("10.3.0.0/16")
        .withVnetAddressSpaceIpMask("10.1.0.0/22")
        .withVnetSubnetAddressIpMask("10.1.0.0/22")
        .withNodePool(GcpNodePool.builder()
            .withName("gcpnode")
            .withDiskSizeGb(35)
            .withInitialNodeCount(1)
            .withMachineType(E2_STANDARD2)
            .withMaxNodeCount(3)
            .withMaxSurge(1)
            .withMinNodeCount(1)
            .build())
        .withPriorityClass(PriorityClass.builder()
            .withName("fractal-critical")
            .withDescription("Used for Fractal Components")
            .withPreemptionPolicy(PREEMPT_LOWER_PRIORITY)
            .withValue(1_000_000_000)
            .build())
        .withPodManagedIdentity(PodManagedIdentity.builder()
            .withName("azure-pod-identity")
            .withNamespace("kube-system")
            .withExceptionPodLabels(Map.of("app", "mic", "component", "mic"))
            .withEnable(true)
            .withAllowNetworkPluginKubeNet(true)
            .build());
  }

  public static AzureKubernetesService getAksExample() {
    return getAksBuilder()
        .withK8sWorkload(getK8sWorkloadExample())
        //.withMessageBroker(getKafkaClusterExample())
        .withMonitoring(getPrometheusExample())
        .withAPIGateway(getAmbassadorExample())
        .withServiceMeshSecurity(getOcelotExample())
        //.withTracing(getJaegerExample())
        .withLogging(getElasticLoggingExample())
        .withDocumentDB(getElasticDataStoreExample())
        .build();
  }

  public static GoogleKubernetesEngine getGkeExample() {
    return getGkeBuilder()
        .withK8sWorkload(getK8sWorkloadExample())
        //.withMessageBroker(getKafkaClusterExample())
        .withMonitoring(getPrometheusExample())
        .withAPIGateway(getAmbassadorExample())
        .withServiceMeshSecurity(getOcelotExample())
        //.withTracing(getJaegerExample())
        .withLogging(getElasticLoggingExample())
        .withDocumentDB(getElasticDataStoreExample())
        .build();
  }

  public static KubernetesWorkload getK8sWorkloadExample() {
    return KubernetesWorkload.builder()
        .withId("fractal-svc")
        .withDescription("Fractal Service on K8S")
        .withDisplayName("Fractal SVC")
        .withNamespace("fractal")
        .withPrivateSSHKeyPassphraseSecretId("fractal-private-passphrase")
        .withPrivateSSHKeySecretId("fractal-private-ssh")
        .withSSHRepositoryURI("ssh-uri")
        .withRepoId("fractal-svc-id")
        .withBranchName("env/fractal-test")
        .withRoles(List.of("roles/datastore.user", "roles/pubsub.editor"))
        .build();
  }

  public static Prometheus getPrometheusExample() {
    return Prometheus.builder()
        .withId("prometheus")
        .withDescription("Prometheus monitoring")
        .withDisplayName("Prometheus")
        .withNamespace("monitoring")
        .withApiGatewayUrl("apiGatewayUrl")
        .build();
  }

  public static Ambassador getAmbassadorExample() {
    return Ambassador.builder()
        .withId("ambassador")
        .withDescription("Ambassador")
        .withDisplayName("Ambassador")
        .withNamespace("ambassador")
        .withHost("host")
        .withHostOwnerEmail("hostOwnerEmail")
        .withAcmeProviderAuthority("authority")
        .withTlsSecretName("tls")
        .build();
  }

  public static Ocelot getOcelotExample() {
    return Ocelot.builder()
        .withId("ocelot")
        .withDescription("Security with Ocelot")
        .withDisplayName("Ocelot")
        .withNamespace("security")
        .withHost("api.fractal-arch.org")
        .withHostOwnerEmail("hello@fractal-arch.org")
        .withCookieMaxAgeSec(3600)
        .withCorsOrigins("https://fractal-arch.org")
        .withPathPrefix("/api/*")
        .withLink(ComponentLink.builder()
            .withComponentId("db-1")
            .build())
        .build();
  }

  public static ElasticLogging getElasticLoggingExample() {
    return ElasticLogging.builder()
        .withId("elastic-logging")
        .withDescription("Elastic Logging")
        .withDisplayName("Elastic Logging")
        .withNamespace("logging")
        .withAPM(true)
        . withKibana(true)
        .withElasticVersion("1")
        .withInstances(3)
        .withStorage("250Gi")
        .withStorageClassName("standard")
        .withMemory(3)
        .withCpu(3)
        .build();
  }

  public static ElasticDataStore getElasticDataStoreExample() {
    return ElasticDataStore.builder()
        .withId("elastic-data")
        .withDescription("Elastic Data Store")
        .withDisplayName("Elastic Data Store")
        .withNamespace("elastic-data")
        .withKibana(false)
        .withElasticVersion("1")
        .withInstances(3)
        .withStorage("250Gi")
        .withStorageClassName("ssd")
        .withMemory(3)
        .withCpu(3)
        .build();
  }

  /*public static Jaeger getJaegerExample() {
    return Jaeger.builder()
        .withId("jaeger")
        .withDescription("Jaeger Tracing")
        .withDisplayName("Jaeger")
        .withNamespace("tracing")
        .withStorageCpu(1)
        .withStorageMemory(2)
        .withStorageInstances(1)
        .withStorageStorageClassName("standard")
        .build();
  }*/

  /*public static KafkaCluster getKafkaClusterExample() {
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
  }*/

  public static AzurePostgreSQL getAzurePostgresExample() {
    return AzurePostgreSQL.builder()
        .withId("dbpg")
        .withDescription("PostgreSQL")
        .withDisplayName("PostgreSQL")
        .withRegion(EUROPE_WEST)
        .withRootUser("rootUser")
        .withSkuName(B_GEN5_1)
        .withStorageAutoGrow(ENABLED)
        .withStorageMB(5 * 1024)
        .withBackupRetentionDays(12)
        .withDatabase(PostgreSQLDB.builder().withId("db-1").withDisplayName("db-1").withName("db").build())
        .withDatabase(getPostgresDbExample())
        .build();
  }

  public static GcpProgreSQL getGcpPostgresExample() {
    return GcpProgreSQL.builder()
        .withId("dbpg")
        .withDescription("PostgreSQL")
        .withDisplayName("PostgreSQL")
        .withRegion(EU_WEST1)
        .withNetwork("network")
        .withPeeringNetworkAddress("address")
        .withPeeringNetworkAddressDescription("address-desc")
        .withPeeringNetworkName("network-name")
        .withPeeringNetworkPrefix("network-prefix")
        .withDatabase(getPostgresDbExample())
        .build();
  }

  public static PostgreSQLDB getPostgresDbExample() {
    return PostgreSQLDB.builder()
        .withId("db-2")
        .withDisplayName("db-2")
        .withName("db2")
        .withLink(getComponentLink())
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
        .withResourceGroupId("ge12616")
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

  public static void printJsonRepresentation(Object obj) {
    try {
      log.debug(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj));
    } catch (JsonProcessingException e) {
      log.error("Error when trying to process: {}", obj, e);
    }
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
      softly.assertThat(componentDto.getDependencies()).as("Component Dependencies").containsAll(comp.getDependencies().stream().map(ComponentId::getValue).collect(toSet()));
      softly.assertThat(componentDto.getLinks()).as("Component Links").containsAll(comp.getLinks());
    });
  }
}
