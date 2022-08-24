package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.*;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQL;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQLDB;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.*;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.yanchware.fractal.sdk.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class BlueprintComponentDtoTest {

  @Test
  public void blueprintComponentValid_when_aksWithPrometheus() {
    var aks = getAksBuilder().withMonitoring(getPrometheusExample()).build();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    var prometheus = (Prometheus) aks.getMonitoringInstances().get(0);
    var prometheusDto = getBlueprintComponentDto(blueprintComponentDtoList, prometheus.getId().getValue());
    var aksId = aks.getId().getValue();

    assertGenericComponent(prometheusDto, prometheus, CaaSMonitoring.TYPE);
    assertSoftly(softly -> softly
        .assertThat(prometheusDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            prometheus.getApiGatewayUrl(),
            prometheus.getNamespace(),
            aksId
        ));
  }

  @Test
  public void blueprintComponentValid_when_aksWithAmbassador() {
    var aks = getAksBuilder().withAPIGateway(getAmbassadorExample()).build();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    var ambassador = (Ambassador) aks.getApiGatewayInstances().get(0);
    var ambassadorDto = getBlueprintComponentDto(blueprintComponentDtoList, ambassador.getId().getValue());
    var aksId = aks.getId().getValue();

    assertGenericComponent(ambassadorDto, ambassador, CaaSAPIGateway.TYPE);
    assertSoftly(softly -> softly
        .assertThat(ambassadorDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            ambassador.getHost(),
            ambassador.getHostOwnerEmail(),
            ambassador.getAcmeProviderAuthority(),
            ambassador.getTlsSecretName(),
            ambassador.getNamespace(),
            aksId
        ));
  }

  @Test
  public void blueprintComponentValid_when_aksWithOcelot() {
    var aks = getAksBuilder().withServiceMeshSecurity(getOcelotExample()).build();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    var ocelot = (Ocelot) aks.getServiceMeshSecurityInstances().get(0);
    var ocelotDto = getBlueprintComponentDto(blueprintComponentDtoList, ocelot.getId().getValue());
    var aksId = aks.getId().getValue();

    assertGenericComponent(ocelotDto, ocelot, CaaSServiceMeshSecurity.TYPE);
    assertSoftly(softly -> softly
        .assertThat(ocelotDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            ocelot.getNamespace(),
            ocelot.getHost(),
            ocelot.getHostOwnerEmail(),
            ocelot.getCorsOrigins(),
            ocelot.getCookieMaxAgeSec(),
            ocelot.getPathPrefix(),
            aksId
        ));
  }

  /*@Test
  public void blueprintComponentValid_when_aksWithJaeger() {
    var aks = getAksBuilder().withTracing(getJaegerExample()).build();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    var jaeger = (Jaeger) aks.getTracingInstances().get(0);
    var jaegerDto = getBlueprintComponentDto(blueprintComponentDtoList, jaeger.getId().getValue());
    var aksId = aks.getId().getValue();

    assertGenericComponent(jaegerDto, jaeger, CaaSTracing.TYPE);
    assertSoftly(softly -> softly
        .assertThat(jaegerDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            jaeger.getNamespace(),
            jaeger.getStorageSettings(),
            aksId
        ));
  }*/

  @Test
  public void blueprintComponentValid_when_aksWithElasticLogging() {
    var aks = getAksBuilder().withLogging(getElasticLoggingExample()).build();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    var elasticLogging = (ElasticLogging) aks.getLoggingInstances().get(0);
    var elasticLoggingDto = getBlueprintComponentDto(blueprintComponentDtoList, elasticLogging.getId().getValue());
    var aksId = aks.getId().getValue();

    assertGenericComponent(elasticLoggingDto, elasticLogging, CaaSLogging.TYPE);
    assertSoftly(softly -> softly
        .assertThat(elasticLoggingDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            elasticLogging.getNamespace(),
            elasticLogging.isApmRequired(),
            elasticLogging.isKibanaRequired(),
            elasticLogging.getElasticVersion(),
            elasticLogging.getElasticInstances(),
            elasticLogging.getStorage(),
            elasticLogging.getStorageClassName(),
            elasticLogging.getMemory(),
            elasticLogging.getCpu(),
            aksId
        ));
  }

  @Test
  public void blueprintComponentValid_when_aksWithElasticDataStore() {
    var aks = getAksBuilder().withDocumentDB(getElasticDataStoreExample()).build();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    var elasticDataStore = (ElasticDataStore) aks.getDocumentDBInstances().get(0);
    var elasticDataStoreDto = getBlueprintComponentDto(blueprintComponentDtoList, elasticDataStore.getId().getValue());
    var aksId = aks.getId().getValue();

    assertGenericComponent(elasticDataStoreDto, elasticDataStore, CaaSDocumentDB.TYPE);
    assertSoftly(softly -> softly
        .assertThat(elasticDataStoreDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            elasticDataStore.getNamespace(),
            elasticDataStore.isKibanaRequired(),
            elasticDataStore.getElasticVersion(),
            elasticDataStore.getElasticInstances(),
            elasticDataStore.getStorage(),
            elasticDataStore.getStorageClassName(),
            elasticDataStore.getMemory(),
            elasticDataStore.getCpu(),
            aksId
        ));
  }

  @Test
  public void blueprintComponentValid_when_aksWithK8sWorkload() {
    var aks = getAksBuilder().withK8sWorkload(getK8sWorkloadExample()).build();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    var k8sWorkload = aks.getK8sWorkloadInstances().get(0);
    var k8sWorkloadDto = getBlueprintComponentDto(blueprintComponentDtoList, k8sWorkload.getId().getValue());
    var aksId = aks.getId().getValue();

    assertGenericComponent(k8sWorkloadDto, k8sWorkload, CaaSWorkload.TYPE);
    assertSoftly(softly -> softly
        .assertThat(k8sWorkloadDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            k8sWorkload.getNamespace(),
            k8sWorkload.getPrivateSSHKeyPassphraseSecretId(),
            k8sWorkload.getPrivateSSHKeySecretId(),
            k8sWorkload.getSshRepositoryURI(),
            k8sWorkload.getRoles(),
            k8sWorkload.getRepoId(),
            k8sWorkload.getBranchName(),
            aksId
        ));
  }

  /*@Test
  public void blueprintComponentValid_when_aksWithMessageBroker() {
    var aks = getAksBuilder().withMessageBroker(getKafkaClusterExample()).build();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    var messageBroker = (KafkaCluster) aks.getMessageBrokerInstances().get(0);
    var messageBrokerDto = getBlueprintComponentDto(blueprintComponentDtoList, messageBroker.getId().getValue());
    var aksId = aks.getId().getValue();

    assertGenericComponent(messageBrokerDto, messageBroker, CaaSMessageBroker.TYPE);
    assertSoftly(softly -> softly
        .assertThat(messageBrokerDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            messageBroker.getNamespace(),
            aksId
        ));

    List<String> kafkaTopicIds = messageBroker.getKafkaTopics().stream().map(topic -> topic.getId().getValue()).toList();
    var kafkaTopicDto = getBlueprintComponentDto(blueprintComponentDtoList, kafkaTopicIds.get(0));
    var kafkaTopicComp = messageBroker.getKafkaTopics().stream().filter(x -> x.getId().getValue().equals(kafkaTopicDto.getId())).findFirst().get();
    assertGenericComponent(kafkaTopicDto, kafkaTopicComp, CaaSKafkaTopic.TYPE);
    assertSoftly(softly -> softly
        .assertThat(kafkaTopicDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            kafkaTopicComp.getNamespace(),
            aksId,
            kafkaTopicComp.getClusterName()
        ));

    List<String> kafkaUserIds = messageBroker.getKafkaUsers().stream().map(user -> user.getId().getValue()).toList();
    var kafkaUserDto = getBlueprintComponentDto(blueprintComponentDtoList, kafkaUserIds.get(0));
    var kafkaUserComp = messageBroker.getKafkaUsers().stream().filter(x -> x.getId().getValue().equals(kafkaUserDto.getId())).findFirst().get();
    assertGenericComponent(kafkaUserDto, kafkaUserComp, CaaSKafkaUser.TYPE);
    assertSoftly(softly -> softly
        .assertThat(kafkaUserDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            kafkaUserComp.getNamespace(),
            aksId,
            kafkaUserComp.getClusterName(),
            kafkaUserComp.getAcls()
        ));
  }*/

  @Test
  public void blueprintComponentValid_when_AksLiveSystemComponentConverted() {
    var aks = TestUtils.getAksExample();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    assertComponentSize(aks, blueprintComponentDtoList);
    var aksDto = getBlueprintComponentDto(blueprintComponentDtoList, aks.getId().getValue());
    assertGenericComponent(aksDto, aks, CaaSContainerPlatform.TYPE);
    assertSoftly(softly -> softly
        .assertThat(aksDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            aks.getNetwork(),
            aks.getPodsRange(),
            aks.getRegion().getId(),
            aks.getServiceRange(),
            aks.getSubNetwork(),
            aks.getNodePools(),
            aks.getServiceIpMask(),
            aks.getPodIpMask(),
            aks.getVnetAddressSpaceIpMask(),
            aks.getVnetSubnetAddressIpMask(),
            aks.getPriorityClasses(),
            aks.getPodManagedIdentity()
        ));
  }

  @Test
  public void blueprintComponentValid_when_GkeLiveSystemComponentConverted() {
    var gke = TestUtils.getGkeExample();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(gke));

    assertComponentSize(gke, blueprintComponentDtoList);
    var gkeDto = getBlueprintComponentDto(blueprintComponentDtoList, gke.getId().getValue());
    assertGenericComponent(gkeDto, gke, CaaSContainerPlatform.TYPE);
    assertSoftly(softly -> softly
        .assertThat(gkeDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            gke.getNetwork(),
            gke.getPodsRange(),
            gke.getRegion().getId(),
            gke.getServiceRange(),
            gke.getSubNetwork(),
            gke.getNodePools(),
            gke.getServiceIpMask(),
            gke.getPodIpMask(),
            gke.getVnetAddressSpaceIpMask(),
            gke.getVnetSubnetAddressIpMask(),
            gke.getPriorityClasses(),
            gke.getPodManagedIdentity()
        ));
  }

  @Test
  public void blueprintComponentValid_when_azurePostgresLiveSystemComponentConverted() {
    var apg = TestUtils.getAzurePostgresExample();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(apg));

    assertThat(blueprintComponentDtoList).hasSize(3);

    //assert postgres server
    var azurePgBlueprintCompDto = blueprintComponentDtoList.get(0);
    assertGenericComponent(azurePgBlueprintCompDto, apg, PaaSPostgreSQL.TYPE);
    assertSoftly(softly -> {
      softly.assertThat(azurePgBlueprintCompDto.getParameters().values()).as("Component Parameters")
          .containsExactlyInAnyOrder(
              apg.getRegion().getId(),
              apg.getRootUser(),
              apg.getSkuName().getId(),
              apg.getStorageAutoGrow().getId(),
              apg.getStorageMB(),
              apg.getBackupRetentionDays()
          );
      softly.assertThat(azurePgBlueprintCompDto.getDependencies()).as("Component Dependencies").isEmpty();
      softly.assertThat(azurePgBlueprintCompDto.getLinks()).as("Component Links").isEmpty();
    });

    //assert postgres db#1
    var azurePgDbBlueprintCompDto = blueprintComponentDtoList.get(1);
    var db1 = apg.getDatabases().stream().filter(x -> x.getId().getValue().equals(azurePgDbBlueprintCompDto.getId())).findFirst().get();
    assertGenericComponent(azurePgDbBlueprintCompDto, db1, PaaSPostgreSQLDB.TYPE);
    assertSoftly(softly -> {
      softly.assertThat(azurePgDbBlueprintCompDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(db1.getName());
      softly.assertThat(azurePgDbBlueprintCompDto.getDependencies()).as("Component Dependencies").containsExactly(apg.getId().getValue());
      softly.assertThat(azurePgDbBlueprintCompDto.getLinks()).as("Component Links").isEmpty();
    });

    //assert postgres db#2
    var azurePgDbBlueprintCompDto2 = blueprintComponentDtoList.get(2);
    var db2 = apg.getDatabases().stream().filter(x -> x.getId().getValue().equals(azurePgDbBlueprintCompDto2.getId())).findFirst().get();
    assertGenericComponent(azurePgDbBlueprintCompDto2, db2, PaaSPostgreSQLDB.TYPE);
    assertSoftly(softly -> {
      softly.assertThat(azurePgDbBlueprintCompDto2.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(db2.getName());
      softly.assertThat(azurePgDbBlueprintCompDto2.getDependencies()).as("Component Dependencies").containsExactly(apg.getId().getValue());
      softly.assertThat(azurePgDbBlueprintCompDto2.getLinks()).as("Component Links").containsAll(apg.getLinks());
    });
  }

  @Test
  public void blueprintComponentValid_when_gcpPostgresLiveSystemComponentConverted() {
    var gcpPostgres = TestUtils.getGcpPostgresExample();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(gcpPostgres));

    assertThat(blueprintComponentDtoList).hasSize(2);

    var gcpPgBlueprintCompDto = blueprintComponentDtoList.get(0);
    assertGenericComponent(gcpPgBlueprintCompDto, gcpPostgres, PaaSPostgreSQL.TYPE);
    assertSoftly(softly -> {
      softly.assertThat(gcpPgBlueprintCompDto.getParameters().values()).as("Component Parameters")
          .containsExactlyInAnyOrder(
              gcpPostgres.getRegion().getId(),
              gcpPostgres.getNetwork(),
              gcpPostgres.getPeeringNetworkAddress(),
              gcpPostgres.getPeeringNetworkAddressDescription(),
              gcpPostgres.getPeeringNetworkName(),
              gcpPostgres.getPeeringNetworkPrefix()
          );
      softly.assertThat(gcpPgBlueprintCompDto.getDependencies()).as("Component Dependencies").isEmpty();
      softly.assertThat(gcpPgBlueprintCompDto.getLinks()).as("Component Links").isEmpty();
    });
  }

  private BlueprintComponentDto getBlueprintComponentDto(List<BlueprintComponentDto> blueprintComponentDtoList, String compId) {
    return blueprintComponentDtoList.stream().filter(dto -> dto.getId().equals(compId)).findFirst().get();
  }

  private void assertComponentSize(KubernetesCluster kubernetesCluster, List<BlueprintComponentDto> blueprintComponentDtos) {
    int componentSize = 1; //containerPlatform itself
    componentSize += kubernetesCluster.getK8sWorkloadInstances().size();
    componentSize += kubernetesCluster.getMonitoringInstances().size();
    componentSize += kubernetesCluster.getApiGatewayInstances().size();
    componentSize += kubernetesCluster.getServiceMeshSecurityInstances().size();
    componentSize += kubernetesCluster.getLoggingInstances().size();
    componentSize += kubernetesCluster.getDocumentDBInstances().size();
    /*componentSize += kubernetesCluster.getTracingInstances().size();
    componentSize += kubernetesCluster.getMessageBrokerInstances().size();
    var messageBrokerInstance = (KafkaCluster) kubernetesCluster.getMessageBrokerInstances().get(0);
    componentSize += messageBrokerInstance.getKafkaTopics().size();
    componentSize += messageBrokerInstance.getKafkaUsers().size();*/

    assertThat(blueprintComponentDtos).hasSize(componentSize);
  }

}