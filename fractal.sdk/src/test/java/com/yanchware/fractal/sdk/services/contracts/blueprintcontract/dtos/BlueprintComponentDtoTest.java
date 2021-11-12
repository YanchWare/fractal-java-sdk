package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.ComponentLink;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.*;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQL;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQLDB;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.KubernetesCluster;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.yanchware.fractal.sdk.utils.TestUtils.assertGenericComponent;
import static org.assertj.core.api.Assertions.assertThat;

public class BlueprintComponentDtoTest {

    @Test
    public void blueprintComponentValid_when_AksLiveSystemComponentConverted() {
        var aks = TestUtils.getAksExample();
        var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

        assertComponentSize(aks, blueprintComponentDtoList);

        //assert aks
        var aksDto = blueprintComponentDtoList.stream().filter(dto -> dto.getId().equals(aks.getId().getValue())).findFirst().get();
        assertGenericComponent(aksDto, aks, CaaSContainerPlatform.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(aksDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    aks.getNetwork(),
                    aks.getPodsRange(),
                    aks.getRegion().getId(),
                    aks.getServiceRange(),
                    aks.getSubNetwork(),
                    aks.getNodePools()
            );
            softly.assertThat(aksDto.getDependencies()).as("Component Dependencies").isEmpty();
            softly.assertThat(aksDto.getLinks()).as("Component Links").isEmpty();
        });

        //assert kafka
        var kafkaDto = blueprintComponentDtoList.stream().filter(dto -> dto.getId().equals(aks.getKafkaClusters().get(0).getId().getValue())).findFirst().get();
        var kafkaComp = aks.getKafkaClusters().get(0);
        assertGenericComponent(kafkaDto, kafkaComp, CaaSKafka.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(kafkaDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    kafkaComp.getNamespace(),
                    aks.getId().getValue()
            );
            softly.assertThat(kafkaDto.getDependencies()).as("Component Dependencies").containsExactly(aks.getId().getValue());
            softly.assertThat(kafkaDto.getLinks()).as("Component Links").isEmpty();
        });

        //assert kafka topic#1
        List<String> kafkaTopicIds = aks.getKafkaClusters().get(0).getKafkaTopics().stream().map(topic -> topic.getId().getValue()).collect(Collectors.toList());
        var kafkaTopicDto1 = blueprintComponentDtoList.stream().filter(dto -> dto.getId().equals(kafkaTopicIds.get(0))).findFirst().get();
        var kafkaTopicComp1 = kafkaComp.getKafkaTopics().stream().filter(x -> x.getId().getValue().equals(kafkaTopicDto1.getId())).findFirst().get();
        assertGenericComponent(kafkaTopicDto1, kafkaTopicComp1, CaaSKafkaTopic.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(kafkaTopicDto1.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    kafkaTopicComp1.getNamespace(),
                    aks.getId().getValue(),
                    kafkaTopicComp1.getClusterName()
            );
            softly.assertThat(kafkaTopicDto1.getDependencies()).as("Component Dependencies").containsExactly(kafkaComp.getId().getValue());
            softly.assertThat(kafkaTopicDto1.getLinks()).as("Component Links").isEmpty();
        });

        //assert kafka topic#2
        var kafkaTopicDto2 = blueprintComponentDtoList.stream().filter(dto -> dto.getId().equals(kafkaTopicIds.get(1))).findFirst().get();
        var kafkaTopicComp2 = kafkaComp.getKafkaTopics().stream().filter(x -> x.getId().getValue().equals(kafkaTopicDto2.getId())).findFirst().get();
        assertGenericComponent(kafkaTopicDto2, kafkaTopicComp2, CaaSKafkaTopic.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(kafkaTopicDto2.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    kafkaTopicComp2.getNamespace(),
                    aks.getId().getValue(),
                    kafkaTopicComp2.getClusterName()
            );
            softly.assertThat(kafkaTopicDto2.getDependencies()).as("Component Dependencies").containsExactly(kafkaComp.getId().getValue());
            softly.assertThat(kafkaTopicDto2.getLinks()).as("Component Links").isEmpty();
        });

        //assert kafka user#1
        List<String> kafkaUserIds = aks.getKafkaClusters().get(0).getKafkaUsers().stream().map(topic -> topic.getId().getValue()).collect(Collectors.toList());
        var kafkaUserDto = blueprintComponentDtoList.stream().filter(dto -> dto.getId().equals(kafkaUserIds.get(0))).findFirst().get();
        var kafkaUserComp = kafkaComp.getKafkaUsers().stream().filter(x -> x.getId().getValue().equals(kafkaUserDto.getId())).findFirst().get();
        assertGenericComponent(kafkaUserDto, kafkaUserComp, CaaSKafkaUser.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(kafkaUserDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    kafkaUserComp.getNamespace(),
                    aks.getId().getValue(),
                    kafkaUserComp.getClusterName(),
                    kafkaUserComp.getAcls()
            );
            softly.assertThat(kafkaUserDto.getDependencies()).as("Component Dependencies").containsExactly(kafkaComp.getId().getValue());
            softly.assertThat(kafkaUserDto.getLinks()).as("Component Links").isEmpty();
        });

        //assert kafka user#2
        var kafkaUserDto2 = blueprintComponentDtoList.stream().filter(dto -> dto.getId().equals(kafkaUserIds.get(1))).findFirst().get();
        var kafkaUserComp2 = kafkaComp.getKafkaUsers().stream().filter(x -> x.getId().getValue().equals(kafkaUserDto2.getId())).findFirst().get();
        assertGenericComponent(kafkaUserDto2, kafkaUserComp2, CaaSKafkaUser.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(kafkaUserDto2.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    kafkaUserComp2.getNamespace(),
                    aks.getId().getValue(),
                    kafkaUserComp2.getClusterName(),
                    kafkaUserComp2.getAcls()
            );
            softly.assertThat(kafkaUserDto2.getDependencies()).as("Component Dependencies").containsExactly(kafkaComp.getId().getValue());
            softly.assertThat(kafkaUserDto2.getLinks()).as("Component Links").isEmpty();
        });

        //assert prometheus
        var prometheusDto = blueprintComponentDtoList.stream().filter(dto -> dto.getId().equals(aks.getPrometheusInstances().get(0).getId().getValue())).findFirst().get();
        var prometheusInstance = aks.getPrometheusInstances().get(0);
        assertGenericComponent(prometheusDto, prometheusInstance, CaaSMonitoring.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(prometheusDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    prometheusInstance.getNamespace(),
                    aks.getId().getValue()
            );
            softly.assertThat(prometheusDto.getDependencies()).as("Component Dependencies").containsExactly(aks.getId().getValue());
            softly.assertThat(prometheusDto.getLinks()).as("Component Links").isEmpty();
        });

        //assert ambassador
        var ambassadorDto = blueprintComponentDtoList.stream().filter(dto -> dto.getId().equals(aks.getAmbassadorInstances().get(0).getId().getValue())).findFirst().get();
        var ambassador = aks.getAmbassadorInstances().get(0);
        assertGenericComponent(ambassadorDto, ambassador, CaaSAPIGateway.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(ambassadorDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    ambassador.getNamespace(),
                    aks.getId().getValue()
            );
            softly.assertThat(ambassadorDto.getDependencies()).as("Component Dependencies").containsExactly(aks.getId().getValue());
            softly.assertThat(ambassadorDto.getLinks()).as("Component Links").isEmpty();
        });

        //assert k8s workload
        var k8sWorkloadDto = blueprintComponentDtoList.stream().filter(dto -> dto.getId().equals(aks.getKubernetesWorkloads().get(0).getId().getValue())).findFirst().get();
        var k8sWorkload = aks.getKubernetesWorkloads().get(0);
        assertGenericComponent(k8sWorkloadDto, k8sWorkload, CaaSWorkload.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(k8sWorkloadDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    k8sWorkload.getNamespace(),
                    k8sWorkload.getPrivateSSHKeyPassphraseSecretId(),
                    k8sWorkload.getPrivateSSHKeySecretId(),
                    k8sWorkload.getPublicSSHKey(),
                    k8sWorkload.getSshRepositoryURI(),
                    k8sWorkload.getRoles(),
                    k8sWorkload.getRepoId(),
                    aks.getId().getValue()
            );
            softly.assertThat(k8sWorkloadDto.getDependencies()).as("Component Dependencies").containsExactly(aks.getId().getValue());
            softly.assertThat(k8sWorkloadDto.getLinks()).as("Component Links").isEmpty();
        });

        //assert ocelot
        var ocelotDto = blueprintComponentDtoList.stream().filter(dto -> dto.getId().equals(aks.getOcelotInstances().get(0).getId().getValue())).findFirst().get();
        var ocelot = aks.getOcelotInstances().get(0);
        assertGenericComponent(ocelotDto, ocelot, CaaSServiceMeshSecurity.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(ocelotDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    ocelot.getNamespace(),
                    ocelot.getHost(),
                    ocelot.getHostOwnerEmail(),
                    ocelot.getCorsOrigins(),
                    ocelot.getCookieMaxAgeSec(),
                    ocelot.getPathPrefix(),
                    aks.getId().getValue()
            );
            softly.assertThat(ocelotDto.getDependencies()).as("Component Dependencies").containsExactly(aks.getId().getValue());
            softly.assertThat(ocelotDto.getLinks().stream().map(ComponentLink::getComponentId).collect(Collectors.toSet())).as("Component Links").contains("db-1");
        });
    }

    @Test
    public void blueprintComponentValid_when_azurePostgresLiveSystemComponentConverted() {
        var apg = TestUtils.getAzurePostgresExample();
        var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(apg));

        assertThat(blueprintComponentDtoList).hasSize(3);

        //assert postgres server
        var azurePgBlueprintCompDto = blueprintComponentDtoList.get(0);
        assertGenericComponent(azurePgBlueprintCompDto, apg, PaaSPostgreSQL.TYPE);
        SoftAssertions.assertSoftly(softly -> {
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
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(azurePgDbBlueprintCompDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(db1.getName());
            softly.assertThat(azurePgDbBlueprintCompDto.getDependencies()).as("Component Dependencies").containsExactly(apg.getId().getValue());
            softly.assertThat(azurePgDbBlueprintCompDto.getLinks()).as("Component Links").isEmpty();
        });

        //assert postgres db#2
        var azurePgDbBlueprintCompDto2 = blueprintComponentDtoList.get(2);
        var db2 = apg.getDatabases().stream().filter(x -> x.getId().getValue().equals(azurePgDbBlueprintCompDto2.getId())).findFirst().get();
        assertGenericComponent(azurePgDbBlueprintCompDto2, db2, PaaSPostgreSQLDB.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(azurePgDbBlueprintCompDto2.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(db2.getName());
            softly.assertThat(azurePgDbBlueprintCompDto2.getDependencies()).as("Component Dependencies").containsExactly(apg.getId().getValue());
            softly.assertThat(azurePgDbBlueprintCompDto2.getLinks()).as("Component Links").containsAll(apg.getLinks());
        });
    }

    private void assertComponentSize(KubernetesCluster kubernetesCluster, List<BlueprintComponentDto> blueprintComponentDtos) {
        int componentSize = 1; //containerPlatform itself
        componentSize += kubernetesCluster.getKubernetesWorkloads().size();
        componentSize += kubernetesCluster.getKafkaClusters().size();
        componentSize += kubernetesCluster.getPrometheusInstances().size();
        componentSize += kubernetesCluster.getAmbassadorInstances().size();
        componentSize += kubernetesCluster.getOcelotInstances().size();
        componentSize += kubernetesCluster.getKafkaClusters().stream().mapToLong(x -> x.getKafkaTopics().size()).sum();
        componentSize += kubernetesCluster.getKafkaClusters().stream().mapToLong(x -> x.getKafkaUsers().size()).sum();

        assertThat(blueprintComponentDtos).hasSize(componentSize);
    }

}