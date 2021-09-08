package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.utils.TestUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSContainerPlatform.TYPE;

public class BlueprintComponentDtoTest {

    @Test
    public void blueprintComponentValid_when_liveSystemComponentConverted() {
        var aks = TestUtils.getAksExample();

        BlueprintComponentDto blueprintComponentDto = BlueprintComponentDto.fromLiveSystemComponent(aks);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(blueprintComponentDto.getId()).as("Component ID").isEqualTo(aks.getId().getValue());
            softly.assertThat(blueprintComponentDto.getDisplayName()).as("Component Display Name").isEqualTo(aks.getDisplayName());
            softly.assertThat(blueprintComponentDto.getDescription()).as("Component Description").isEqualTo(aks.getDescription());
            softly.assertThat(blueprintComponentDto.getType()).as("Component Type").isEqualTo(TYPE);
            softly.assertThat(blueprintComponentDto.getVersion()).as("Component Version").isEqualTo("0.0.1");
            softly.assertThat(blueprintComponentDto.getParameters().values()).as("Component Parameters").contains(aks.getNetwork());
            softly.assertThat(blueprintComponentDto.getDependencies()).as("Component Dependencies").isEmpty();
            softly.assertThat(blueprintComponentDto.getLinks()).as("Component Links").isEmpty();
            softly.assertThat(blueprintComponentDto.getOutputFields()).as("Component Output Fields").isEmpty();
        });
    }

}