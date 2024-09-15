package com.yanchware.fractal.sdk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentAggregate;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentType;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystem;
import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.ComponentLink;
import com.yanchware.fractal.sdk.domain.livesystem.caas.*;
import com.yanchware.fractal.sdk.domain.livesystem.paas.PodManagedIdentity;
import com.yanchware.fractal.sdk.domain.livesystem.paas.RoleAssignment;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsElasticKubernetesService;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureOsSku;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzurePostgreSqlDatabase;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzurePostgreSqlDbms;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks.*;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks.AzureKubernetesService.AzureKubernetesServiceBuilder;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpNodePool;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpPostgreSqlDatabase;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpPostgreSqlDbms;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GoogleKubernetesEngine;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GoogleKubernetesEngine.GoogleKubernetesEngineBuilder;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci.OciContainerEngineForKubernetes;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci.OciContainerEngineForKubernetes.OciContainerEngineForKubernetesBuilder;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsElasticKubernetesService.AwsElasticKubernetesServiceBuilder;
import com.yanchware.fractal.sdk.domain.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.domain.values.ComponentId;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;
import static com.yanchware.fractal.sdk.domain.livesystem.caas.PreemptionPolicy.NEVER;
import static com.yanchware.fractal.sdk.domain.livesystem.caas.PreemptionPolicy.PREEMPT_LOWER_PRIORITY;
import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsRegion.EU_NORTH_1;
import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureMachineType.STANDARD_B2S;
import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureOsType.LINUX;
import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion.WEST_EUROPE;
import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureStorageAutoGrow.ENABLED;
import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice.valueobjects.AzureSkuName.B_GEN5_1;
import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpMachine.E2_STANDARD2;
import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion.EU_WEST1;
import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci.OciRegion.EU_ZURICH_1;
import static java.util.stream.Collectors.toSet;

@Slf4j
public class TestUtils {

  public static AzureKubernetesServiceBuilder getAksBuilder() {
    return AzureKubernetesService.builder()
        .withId("aks-1")
        .withDescription("Test AKS cluster")
        .withDisplayName("AKS #1")
        .withRegion(WEST_EUROPE)
        .withServiceIpRange("10.2.0.0/16")
        .withPodIpRange("10.3.0.0/16")
        .withVnetAddressSpaceIpRange("10.1.0.0/22")
        .withVnetSubnetAddressIpRange("10.1.0.0/22")
        .withExternalWorkspaceResourceId("workplaceResourceId")
        .withOutboundIp(AzureOutboundIp.builder()
            .withName("fractal")
            .withAzureResourceGroup(AzureResourceGroup.builder()
                .withName("group")
                .withRegion(WEST_EUROPE)
                .build())
            .build())
        .withAddonProfiles(List.of(
            AzureKubernetesAddonProfile.builder()
                .withAddonToEnable(AzureKubernetesAddon.AZURE_POLICY)
                .build(),
            AzureKubernetesAddonProfile.builder()
                .withAddonToEnable(AzureKubernetesAddon.AZURE_KEYVAULT_SECRETS_PROVIDER)
                .build()))
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
            .withOsSku(AzureOsSku.AZURE_LINUX)
            .withAutoscalingEnabled(true)
            .withLabels(Map.of("env", "test"))
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
            .build())
        .withRole(RoleAssignment.builder()
            .withRoleName("AcrPull")
            .withScope("Role scope to ACR")
            .build())
        .withAddonProfile(AzureKubernetesAddonProfile.builder()
            .withAddonToEnable(AzureKubernetesAddon.MONITORING)
            .build())
        .withKubernetesVersion("1.1.1")
        .withActiveDirectoryProfile(AzureActiveDirectoryProfile.builder()
            .withAdminGroupObjectIDs(List.of(UUID.randomUUID().toString()))
            .build())
        .withWindowsAdminUsername("unit-test")
        .withWorkloadIdentityEnabled(true)
        .withTag("tag1", "tag1Value")
        .withTag("tag2", "tag2Value");
  }

  public static GoogleKubernetesEngineBuilder getGkeBuilder() {
    return GoogleKubernetesEngine.builder()
        .withId("gke-1")
        .withDescription("Test GKE cluster")
        .withDisplayName("GKE #1")
        .withRegion(EU_WEST1)
        .withNetworkName("network-host")
        .withSubnetworkName("compute-tier-1")
        .withPodsRangeName("tier-1-pods")
        .withPodIpRange("10.3.0.0/16")
        .withServicesRangeName("tier-1-services")
        .withServiceIpRange("10.2.0.0/16")
        .withSubnetworkIpRange("10.0.4.0/22")
        .withNodePool(GcpNodePool.builder()
            .withName("gcpnode")
            .withDiskSizeGb(35)
            .withInitialNodeCount(1)
            .withMachineType(E2_STANDARD2)
            .withMaxNodeCount(3)
            .withMaxSurge(1)
            .withMinNodeCount(1)
            .build());
  }

  public static AzureKubernetesService getAksExample() {
    return getAksBuilder()
        .withK8sWorkload(getK8sWorkloadExample())
        .withMonitoring(getPrometheusExample())
        .withAPIGateway(getAmbassadorExample())
        .withServiceMeshSecurity(getOcelotExample())
        .withLogging(getElasticLoggingExample())
        .withDocumentDB(getElasticDataStoreExample())
        .build();
  }

  public static GoogleKubernetesEngine getGkeExample() {
    return getGkeBuilder()
        .withK8sWorkload(getK8sWorkloadExample())
        .withMonitoring(getPrometheusExample())
        .withAPIGateway(getAmbassadorExample())
        .withServiceMeshSecurity(getOcelotExample())
        .withLogging(getElasticLoggingExample())
        .withDocumentDB(getElasticDataStoreExample())
        .build();
  }

  public static CaaSKubernetesWorkload getK8sWorkloadExample() {
    return CaaSKubernetesWorkload.builder()
        .withId("fractal-svc")
        .withDescription("Fractal Service on K8S")
        .withDisplayName("Fractal SVC")
        .withNamespace("fractal")
        .withPrivateSSHKeyPassphraseSecretId("fractal-private-passphrase")
        .withPrivateSSHKeySecretId("fractal-private-ssh")
        .withSSHRepositoryURI("ssh-uri")
        .withRepoId("fractal-svc-id")
        .withBranchName("env/fractal-test")
        .withRoles(List.of(
            CustomWorkloadRole.builder().withName("datastore").withScope("user").build(),
            CustomWorkloadRole.builder().withName("pubsub").withScope("editor").build(),
            CustomWorkloadRole.builder().withName("ocelot").withScope("user").withRoleType(RoleType.OCELOT_ROLE).build()))
        .build();
  }

  public static CaaSPrometheus getPrometheusExample() {
    return CaaSPrometheus.builder()
        .withId("prometheus")
        .withDescription("Prometheus monitoring")
        .withDisplayName("Prometheus")
        .withNamespace("monitoring")
        .withApiGatewayUrl("apiGatewayUrl")
        .build();
  }

  public static CaaSAmbassador getAmbassadorExample() {
    return CaaSAmbassador.builder()
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

  public static CaaSOcelot getOcelotExample() {
    return CaaSOcelot.builder()
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

  public static CaaSElasticLogging getElasticLoggingExample() {
    return CaaSElasticLogging.builder()
        .withId("elastic-logging")
        .withDescription("Elastic Logging")
        .withDisplayName("Elastic Logging")
        .withNamespace("logging")
        .withAPM(true)
        .withKibana(true)
        .withElasticVersion("1")
        .withInstances(3)
        .withStorage("250Gi")
        .withStorageClassName("standard")
        .withMemory(3)
        .withCpu(3)
        .build();
  }

  public static CaaSElasticDataStore getElasticDataStoreExample() {
    return CaaSElasticDataStore.builder()
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

  public static AzurePostgreSqlDbms getAzurePostgresExample() {
    var postgreSqlDatabase = AzurePostgreSqlDatabase.builder()
        .withId("db-1")
        .withDisplayName("db-1")
        .withName("db")
        .withSchema("test")
        .build();
    
    return AzurePostgreSqlDbms.builder()
        .withId("dbpg")
        .withName("db-name")
        .withDescription("PostgreSQL")
        .withDisplayName("PostgreSQL")
        .withRegion(WEST_EUROPE)
        .withRootUser("rootUser")
        .withSkuName(B_GEN5_1)
        .withStorageAutoGrow(ENABLED)
        .withStorageMB(5 * 1024)
        .withBackupRetentionDays(12)
        .withDatabase(postgreSqlDatabase)
        .withDatabase(getAzurePostgresDbExample())
        .build();
  }

  public static GcpPostgreSqlDbms getGcpPostgresExample() {
    return GcpPostgreSqlDbms.builder()
        .withId("dbpg")
        .withDescription("PostgreSQL")
        .withDisplayName("PostgreSQL")
        .withRegion(EU_WEST1)
        .withNetwork("network")
        .withPeeringNetworkAddress("address")
        .withPeeringNetworkAddressDescription("address-desc")
        .withPeeringNetworkName("network-name")
        .withPeeringNetworkPrefix("network-prefix")
        .withDatabase(getGcpPostgresDbExample())
        .lock()
        .build();
  }

  public static AzurePostgreSqlDatabase getAzurePostgresDbExample() {
    return AzurePostgreSqlDatabase.builder()
        .withId("db-2")
        .withDisplayName("db-2")
        .withName("db2")
        .withSchema("test")
        .withLink(getComponentLink())
        .build();
  }

  public static GcpPostgreSqlDatabase getGcpPostgresDbExample() {
    return GcpPostgreSqlDatabase.builder()
        .withId("db-2")
        .withDisplayName("db-2")
        .withName("db2")
        .withSchema("test")
        .withLink(getComponentLink())
        .build();
  }

  public static EnvironmentAggregate getEnvExample() {
    return EnvironmentAggregate.builder()
        .withEnvironmentType(EnvironmentType.PERSONAL)
        .withOwnerId(UUID.fromString("2e114308-14ec-4d77-b610-490324fa1844"))
        .withResourceGroup(UUID.randomUUID())
        .withShortName("test")
        .build();
  }

  public static LiveSystem getLiveSystemExample() {
    return LiveSystem.builder()
        .withName("business-platform-test")
        .withDescription("Business platform")
        .withResourceGroupId("test-resource-group")
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

  public static AzureKubernetesServiceBuilder getDefaultAks() {
    return AzureKubernetesService.builder()
        .withId(ComponentId.from("test"))
        .withRegion(WEST_EUROPE)
        .withNodePool(AzureNodePool.builder()
            .withMachineType(STANDARD_B2S)
            .withName("azure")
            .withDiskSizeGb(30)
            .withInitialNodeCount(1)
            .withAutoscalingEnabled(false)
            .build());
  }

  public static GoogleKubernetesEngineBuilder getDefaultGke() {
    return GoogleKubernetesEngine.builder()
        .withId(ComponentId.from("test"))
        .withRegion(EU_WEST1)
        .withNodePool(GcpNodePool.builder()
            .withName("gke")
            .withMachineType(E2_STANDARD2)
            .build());
  }

  public static OciContainerEngineForKubernetesBuilder getDefaultOke() {
    return OciContainerEngineForKubernetes.builder()
            .withId(ComponentId.from("test"))
            .withRegion(EU_ZURICH_1);
  }

  public static AwsElasticKubernetesServiceBuilder getDefaultEks() {
    return AwsElasticKubernetesService.builder()
            .withId(ComponentId.from("test"))
            .withRegion(EU_NORTH_1);
  }

  public static void assertGenericComponent(ComponentDto componentDto, Component comp, String type) {
    SoftAssertions.assertSoftly(softly -> softly.assertThat(componentDto)
      .extracting(
        ComponentDto::getId,
        ComponentDto::getDisplayName,
        ComponentDto::getDescription,
        ComponentDto::getType,
        ComponentDto::getVersion,
        ComponentDto::isLocked,
        ComponentDto::getDependencies,
        ComponentDto::getLinks)
      .containsExactly(
        comp.getId().getValue(),
        comp.getDisplayName(),
        comp.getDescription(),
        type,
        DEFAULT_VERSION,
        comp.isLocked(),
        comp.getDependencies().stream().map(ComponentId::getValue).collect(toSet()),
        comp.getLinks()));
  }

  public static String getJsonRepresentation(Object obj) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      var json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
      log.debug(json);

      return json;
    } catch (JsonProcessingException e) {
      log.error("Error when trying to process: {}", obj, e);
      return null;
    }
  }

  public static JsonNode getJsonNodeRepresentation(Object obj) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      var json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
      return objectMapper.readTree(json);
    } catch (JsonProcessingException e) {
      log.error("Error when trying to process: {}", obj, e);
      return null;
    }
  }
}
