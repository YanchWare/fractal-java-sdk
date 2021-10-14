package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSContainerPlatform;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSKafka;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSKafkaTopic;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSKafkaUser;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQL;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQLDB;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;
import static org.assertj.core.api.Assertions.assertThat;

public class BlueprintComponentDtoTest {

    @Test
    public void blueprintComponentValid_when_AksLiveSystemComponentConverted() {
        var aks = TestUtils.getAksExample();
        var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

        assertThat(blueprintComponentDtoList).hasSize(6);

        //assert aks
        var blueprintComponentDto = blueprintComponentDtoList.get(0);
        assertGenericBlueprintComponent(blueprintComponentDto, aks, CaaSContainerPlatform.TYPE);
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
        });

        //assert kafka
        var kafkaDto = blueprintComponentDtoList.get(1);
        var kafkaComp = aks.getKafkaClusters().get(0);
        assertGenericBlueprintComponent(kafkaDto, kafkaComp, CaaSKafka.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(kafkaDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    kafkaComp.getNamespace(),
                    kafkaComp.getContainerPlatform()
            );
            softly.assertThat(kafkaDto.getDependencies()).as("Component Dependencies").containsExactly(aks.getId().getValue());
        });

        //assert kafka topic#1
        var kafkaTopicDto1 = blueprintComponentDtoList.get(2);
        var kafkaTopicComp1 = kafkaComp.getKafkaTopics().stream().filter(x -> x.getId().getValue().equals(kafkaTopicDto1.getId())).findFirst().get();
        assertGenericBlueprintComponent(kafkaTopicDto1, kafkaTopicComp1, CaaSKafkaTopic.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(kafkaTopicDto1.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    kafkaTopicComp1.getNamespace(),
                    kafkaTopicComp1.getContainerPlatform(),
                    kafkaTopicComp1.getClusterName()
            );
            softly.assertThat(kafkaTopicDto1.getDependencies()).as("Component Dependencies").containsExactly(kafkaComp.getId().getValue());
        });

        //assert kafka topic#2
        var kafkaTopicDto2 = blueprintComponentDtoList.get(3);
        var kafkaTopicComp2 = kafkaComp.getKafkaTopics().stream().filter(x -> x.getId().getValue().equals(kafkaTopicDto2.getId())).findFirst().get();
        assertGenericBlueprintComponent(kafkaTopicDto2, kafkaTopicComp2, CaaSKafkaTopic.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(kafkaTopicDto2.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    kafkaTopicComp2.getNamespace(),
                    kafkaTopicComp2.getContainerPlatform(),
                    kafkaTopicComp2.getClusterName()
            );
            softly.assertThat(kafkaTopicDto2.getDependencies()).as("Component Dependencies").containsExactly(kafkaComp.getId().getValue());
        });

        //assert kafka user#1
        var kafkaUserDto = blueprintComponentDtoList.get(4);
        var kafkaUserComp = kafkaComp.getKafkaUsers().stream().filter(x -> x.getId().getValue().equals(kafkaUserDto.getId())).findFirst().get();
        assertGenericBlueprintComponent(kafkaUserDto, kafkaUserComp, CaaSKafkaUser.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(kafkaUserDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    kafkaUserComp.getNamespace(),
                    kafkaUserComp.getContainerPlatform(),
                    kafkaUserComp.getClusterName(),
                    kafkaUserComp.getAcls()
            );
            softly.assertThat(kafkaUserDto.getDependencies()).as("Component Dependencies").containsExactly(kafkaComp.getId().getValue());
        });

        //assert kafka user#2
        var kafkaUserDto2 = blueprintComponentDtoList.get(4);
        var kafkaUserComp2 = kafkaComp.getKafkaUsers().stream().filter(x -> x.getId().getValue().equals(kafkaUserDto.getId())).findFirst().get();
        assertGenericBlueprintComponent(kafkaUserDto2, kafkaUserComp2, CaaSKafkaUser.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(kafkaUserDto2.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
                    kafkaUserComp2.getNamespace(),
                    kafkaUserComp2.getContainerPlatform(),
                    kafkaUserComp2.getClusterName(),
                    kafkaUserComp2.getAcls()
            );
            softly.assertThat(kafkaUserDto2.getDependencies()).as("Component Dependencies").containsExactly(kafkaComp.getId().getValue());
        });
    }

    @Test
    public void blueprintComponentValid_when_azurePostgresLiveSystemComponentConverted() {
        var apg = TestUtils.getAzurePostgresExample();
        var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(apg));

        assertThat(blueprintComponentDtoList).hasSize(3);

        //assert postgres server
        var azurePgBlueprintCompDto = blueprintComponentDtoList.get(0);
        assertGenericBlueprintComponent(azurePgBlueprintCompDto, apg, PaaSPostgreSQL.TYPE);
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
        });

        //assert postgres db#1
        var azurePgDbBlueprintCompDto = blueprintComponentDtoList.get(1);
        var db1 = apg.getDatabases().stream().filter(x -> x.getId().getValue().equals(azurePgDbBlueprintCompDto.getId())).findFirst().get();
        assertGenericBlueprintComponent(azurePgDbBlueprintCompDto, db1, PaaSPostgreSQLDB.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(azurePgDbBlueprintCompDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(db1.getName());
            softly.assertThat(azurePgDbBlueprintCompDto.getDependencies()).as("Component Dependencies").containsExactly(apg.getId().getValue());
        });

        //assert postgres db#2
        var azurePgDbBlueprintCompDto2 = blueprintComponentDtoList.get(2);
        var db2 = apg.getDatabases().stream().filter(x -> x.getId().getValue().equals(azurePgDbBlueprintCompDto2.getId())).findFirst().get();
        assertGenericBlueprintComponent(azurePgDbBlueprintCompDto2, db2, PaaSPostgreSQLDB.TYPE);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(azurePgDbBlueprintCompDto2.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(db2.getName());
            softly.assertThat(azurePgDbBlueprintCompDto2.getDependencies()).as("Component Dependencies").containsExactly(apg.getId().getValue());
        });
    }

    private void assertGenericBlueprintComponent(BlueprintComponentDto componentDto, Component comp, String type) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(componentDto.getId()).as("Component ID").isEqualTo(comp.getId().getValue());
            softly.assertThat(componentDto.getDisplayName()).as("Component Display Name").isEqualTo(comp.getDisplayName());
            softly.assertThat(componentDto.getDescription()).as("Component Description").contains("Blueprint created via SDK by LiveSystem with ID: ");
            softly.assertThat(componentDto.getType()).as("Component Type").isEqualTo(type);
            softly.assertThat(componentDto.getVersion()).as("Component Version").isEqualTo(DEFAULT_VERSION);
            softly.assertThat(componentDto.getLinks()).as("Component Links").isEmpty();
            softly.assertThat(componentDto.getOutputFields()).as("Component Output Fields").isEmpty();
        });
    }

}