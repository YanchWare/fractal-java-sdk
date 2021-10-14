package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSContainerPlatform;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSPostgreSQL;
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

        assertThat(blueprintComponentDtoList).hasSize(1);
        var blueprintComponentDto = blueprintComponentDtoList.get(0);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(blueprintComponentDto.getId()).as("Component ID").isEqualTo(aks.getId().getValue());
            softly.assertThat(blueprintComponentDto.getDisplayName()).as("Component Display Name").isEqualTo(aks.getDisplayName());
            softly.assertThat(blueprintComponentDto.getDescription()).as("Component Description").contains("Blueprint created via SDK by LiveSystem with ID: ");
            softly.assertThat(blueprintComponentDto.getType()).as("Component Type").isEqualTo(CaaSContainerPlatform.TYPE);
            softly.assertThat(blueprintComponentDto.getVersion()).as("Component Version").isEqualTo(DEFAULT_VERSION);
            softly.assertThat(blueprintComponentDto.getParameters().values()).as("Component Parameters").contains(aks.getNetwork());
            softly.assertThat(blueprintComponentDto.getDependencies()).as("Component Dependencies").isEmpty();
            softly.assertThat(blueprintComponentDto.getLinks()).as("Component Links").isEmpty();
            softly.assertThat(blueprintComponentDto.getOutputFields()).as("Component Output Fields").isEmpty();
        });
    }

    @Test
    public void blueprintComponentValid_when_azurePostgresLiveSystemComponentConverted() {
        var apg = TestUtils.getAzurePostgresExample();

        var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(apg));

        assertThat(blueprintComponentDtoList).hasSize(3);
        var blueprintComponentDto = blueprintComponentDtoList.get(0);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(blueprintComponentDto.getId()).as("Component ID").isEqualTo(apg.getId().getValue());
            softly.assertThat(blueprintComponentDto.getDisplayName()).as("Component Display Name").isEqualTo(apg.getDisplayName());
            softly.assertThat(blueprintComponentDto.getDescription()).as("Component Description").contains("Blueprint created via SDK by LiveSystem with ID: ");
            softly.assertThat(blueprintComponentDto.getType()).as("Component Type").isEqualTo(PaaSPostgreSQL.TYPE);
            softly.assertThat(blueprintComponentDto.getVersion()).as("Component Version").isEqualTo(DEFAULT_VERSION);
            softly.assertThat(blueprintComponentDto.getParameters().values()).as("Component Parameters")
                    .containsExactlyInAnyOrder(
                            apg.getRegion().getId(),
                            apg.getRootUser(),
                            apg.getSkuName().getId(),
                            apg.getStorageAutoGrow().getId(),
                            apg.getStorageMB(),
                            apg.getBackupRetentionDays()
                    );
            softly.assertThat(blueprintComponentDto.getDependencies()).as("Component Dependencies").isEmpty();
            softly.assertThat(blueprintComponentDto.getLinks()).as("Component Links").isEmpty();
            softly.assertThat(blueprintComponentDto.getOutputFields()).as("Component Output Fields").isEmpty();
        });
    }

}