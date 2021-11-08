package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.*;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQL;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQLDB;
import com.yanchware.fractal.sdk.domain.entities.livesystem.Prometheus;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.yanchware.fractal.sdk.utils.TestUtils.assertGenericComponent;
import static org.assertj.core.api.Assertions.assertThat;

public class BlueprintComponentDtoTest {

    @Test
    public void blueprintComponentValid_when_AksLiveSystemComponentConverted() {
        var aks = TestUtils.getAksExample();
        var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

        assertThat(blueprintComponentDtoList).hasSize(8);

        //assert aks
        var blueprintComponentDto = blueprintComponentDtoList.get(0);
        assertGenericComponent(blueprintComponentDto, aks, CaaSContainerPlatform.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(blueprintComponentDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    aks.getNetwork(),
                    aks.getPodsRange(),
                    aks.getRegion().getId(),
                    aks.getServiceRange(),
                    aks.getSubNetwork(),
                    aks.getNodePools()
            );
            softly.assertThat(blueprintComponentDto.getDependencies()).as("Component Dependencies").isEmpty();
            softly.assertThat(blueprintComponentDto.getLinks()).as("Component Links").isEmpty();
        });

        //assert kafka
        var kafkaDto = blueprintComponentDtoList.get(1);
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
        var kafkaTopicDto1 = blueprintComponentDtoList.get(2);
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
        var kafkaTopicDto2 = blueprintComponentDtoList.get(3);
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
        var kafkaUserDto = blueprintComponentDtoList.get(4);
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
        var kafkaUserDto2 = blueprintComponentDtoList.get(5);
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
        var prometheusDto = blueprintComponentDtoList.get(6);
        Prometheus prometheusInstance = aks.getPrometheusInstances().get(0);
        assertGenericComponent(prometheusDto, prometheusInstance, CaaSPrometheus.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(prometheusDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    prometheusInstance.getNamespace(),
                    aks.getId().getValue()
            );
            softly.assertThat(prometheusDto.getDependencies()).as("Component Dependencies").containsExactly(aks.getId().getValue());
            softly.assertThat(prometheusDto.getLinks()).as("Component Links").isEmpty();
        });

        //assert ambassador
        var ambassadorDto = blueprintComponentDtoList.get(7);
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

}