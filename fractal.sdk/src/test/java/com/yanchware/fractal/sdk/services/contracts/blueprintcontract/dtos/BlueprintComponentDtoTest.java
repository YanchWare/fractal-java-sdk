package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.*;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQL;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQLDB;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.KubernetesCluster;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.yanchware.fractal.sdk.utils.TestUtils.*;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class BlueprintComponentDtoTest {

    @Test
    public void blueprintComponentValid_when_aksWithPrometheus() {
        var aks = getAksBuilder().withPrometheus(getPrometheusExample()).build();
        var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

        var prometheus = aks.getPrometheusInstances().get(0);
        var prometheusDto = getBlueprintComponentDto(blueprintComponentDtoList, prometheus.getId().getValue());
        var aksId = aks.getId().getValue();

        assertGenericComponent(prometheusDto, prometheus, CaaSMonitoring.TYPE);
        assertSoftly(softly -> softly
                .assertThat(prometheusDto.getParameters().values())
                .as("Component Parameters")
                .containsExactlyInAnyOrder(
                        prometheus.getNamespace(),
                        aksId
                ));
    }

    @Test
    public void blueprintComponentValid_when_aksWithAmbassador() {
        var aks = getAksBuilder().withAmbassador(getAmbassadorExample()).build();
        var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

        var ambassador = aks.getAmbassadorInstances().get(0);
        var ambassadorDto = getBlueprintComponentDto(blueprintComponentDtoList, ambassador.getId().getValue());
        var aksId = aks.getId().getValue();

        assertGenericComponent(ambassadorDto, ambassador, CaaSAPIGateway.TYPE);
        assertSoftly(softly -> softly
                .assertThat(ambassadorDto.getParameters().values())
                .as("Component Parameters")
                .containsExactlyInAnyOrder(
                        ambassador.getNamespace(),
                        aksId
                ));
    }

    @Test
    public void blueprintComponentValid_when_aksWithOcelot() {
        var aks = getAksBuilder().withOcelot(getOcelotExample()).build();
        var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

        var ocelot = aks.getOcelotInstances().get(0);
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

    @Test
    public void blueprintComponentValid_when_aksWithJaeger() {
        var aks = getAksBuilder().withJaeger(getJaegerExample()).build();
        var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

        var jaeger = aks.getJaegerInstances().get(0);
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
    }

    @Test
    public void blueprintComponentValid_when_aksWithElasticLogging() {
        var aks = getAksBuilder().withElasticLogging(getElasticLoggingExample()).build();
        var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

        var elasticLogging = aks.getElasticLoggingInstances().get(0);
        var elasticLoggingDto = getBlueprintComponentDto(blueprintComponentDtoList, elasticLogging.getId().getValue());
        var aksId = aks.getId().getValue();

        assertGenericComponent(elasticLoggingDto, elasticLogging, CaaSLogging.TYPE);
        assertSoftly(softly -> softly
                .assertThat(elasticLoggingDto.getParameters().values())
                .as("Component Parameters")
                .containsExactlyInAnyOrder(
                        elasticLogging.getNamespace(),
                        elasticLogging.isAPMRequired(),
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
        var aks = getAksBuilder().withElasticDataStore(getElasticDataStoreExample()).build();
        var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

        var elasticDataStore = aks.getElasticDataStoreInstances().get(0);
        var elasticDataStoreDto = getBlueprintComponentDto(blueprintComponentDtoList, elasticDataStore.getId().getValue());
        var aksId = aks.getId().getValue();

        assertGenericComponent(elasticDataStoreDto, elasticDataStore, CaaSDocumentDB.TYPE);
        assertSoftly(softly -> softly
                .assertThat(elasticDataStoreDto.getParameters().values())
                .as("Component Parameters")
                .containsExactlyInAnyOrder(
                        elasticDataStore.getNamespace(),
                        elasticDataStore.isAPMRequired(),
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
        var aks = getAksBuilder().withWorkload(getK8sWorkloadExample()).build();
        var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

        var k8sWorkload = aks.getKubernetesWorkloads().get(0);
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
                        k8sWorkload.getPublicSSHKey(),
                        k8sWorkload.getSshRepositoryURI(),
                        k8sWorkload.getRoles(),
                        k8sWorkload.getRepoId(),
                        aksId
                ));
    }

    @Test
    public void blueprintComponentValid_when_aksWithKafka() {
        var aks = getAksBuilder().withKafkaCluster(getKafkaClusterExample()).build();
        var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

        var kafkaCluster = aks.getKafkaClusters().get(0);
        var kafkaClusterDto = getBlueprintComponentDto(blueprintComponentDtoList, kafkaCluster.getId().getValue());
        var aksId = aks.getId().getValue();

        assertGenericComponent(kafkaClusterDto, kafkaCluster, CaaSKafka.TYPE);
        assertSoftly(softly -> softly
                .assertThat(kafkaClusterDto.getParameters().values())
                .as("Component Parameters")
                .containsExactlyInAnyOrder(
                        kafkaCluster.getNamespace(),
                        aksId
                ));

        List<String> kafkaTopicIds = kafkaCluster.getKafkaTopics().stream().map(topic -> topic.getId().getValue()).collect(toList());
        var kafkaTopicDto = getBlueprintComponentDto(blueprintComponentDtoList, kafkaTopicIds.get(0));
        var kafkaTopicComp = kafkaCluster.getKafkaTopics().stream().filter(x -> x.getId().getValue().equals(kafkaTopicDto.getId())).findFirst().get();
        assertGenericComponent(kafkaTopicDto, kafkaTopicComp, CaaSKafkaTopic.TYPE);
        assertSoftly(softly -> softly
                .assertThat(kafkaTopicDto.getParameters().values())
                .as("Component Parameters")
                .containsExactlyInAnyOrder(
                        kafkaTopicComp.getNamespace(),
                        aksId,
                        kafkaTopicComp.getClusterName()
                ));

        List<String> kafkaUserIds = kafkaCluster.getKafkaUsers().stream().map(user -> user.getId().getValue()).collect(toList());
        var kafkaUserDto = getBlueprintComponentDto(blueprintComponentDtoList, kafkaUserIds.get(0));
        var kafkaUserComp = kafkaCluster.getKafkaUsers().stream().filter(x -> x.getId().getValue().equals(kafkaUserDto.getId())).findFirst().get();
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
    }

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
                        aks.getNodePools()
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

    private BlueprintComponentDto getBlueprintComponentDto(List<BlueprintComponentDto> blueprintComponentDtoList, String compId) {
        return blueprintComponentDtoList.stream().filter(dto -> dto.getId().equals(compId)).findFirst().get();
    }

    private void assertComponentSize(KubernetesCluster kubernetesCluster, List<BlueprintComponentDto> blueprintComponentDtos) {
        int componentSize = 1; //containerPlatform itself
        componentSize += kubernetesCluster.getKubernetesWorkloads().size();
        componentSize += kubernetesCluster.getKafkaClusters().size();
        componentSize += kubernetesCluster.getPrometheusInstances().size();
        componentSize += kubernetesCluster.getAmbassadorInstances().size();
        componentSize += kubernetesCluster.getOcelotInstances().size();
        componentSize += kubernetesCluster.getJaegerInstances().size();
        componentSize += kubernetesCluster.getElasticLoggingInstances().size();
        componentSize += kubernetesCluster.getElasticDataStoreInstances().size();
        componentSize += kubernetesCluster.getKafkaClusters().stream().mapToLong(x -> x.getKafkaTopics().size()).sum();
        componentSize += kubernetesCluster.getKafkaClusters().stream().mapToLong(x -> x.getKafkaUsers().size()).sum();

        assertThat(blueprintComponentDtos).hasSize(componentSize);
    }

}