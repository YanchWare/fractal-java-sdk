package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static org.junit.Assert.assertEquals;

public class LiveSystemComponentDtoTest {

    @Test
    public void when_ConvertFromLiveSystemComponentToBlueprintComponent_valuesAreTheSame() {
        var liveSystemComponentDto = LiveSystemComponentDto.builder()
                .id("0001")
                .displayName("display name")
                .description("basic infra")
                .type("type")
                .version("V0.1")
                .parameters(emptyMap())
                .dependencies(emptySet())
                .links(emptySet())
                .outputFields(emptyMap())
                .build();

        var blueprintComponentDto = liveSystemComponentDto.toBlueprintComponent();

        assertEquals(liveSystemComponentDto.getId(), blueprintComponentDto.getId());
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(blueprintComponentDto.getId()).as("Component ID").isEqualTo(liveSystemComponentDto.getId());
            softly.assertThat(blueprintComponentDto.getDisplayName()).as("Component Display Name").isEqualTo(liveSystemComponentDto.getDisplayName());
            softly.assertThat(blueprintComponentDto.getDescription()).as("Component Description").isEqualTo(liveSystemComponentDto.getDescription());
            softly.assertThat(blueprintComponentDto.getType()).as("Component Type").isEqualTo(liveSystemComponentDto.getType());
            softly.assertThat(blueprintComponentDto.getVersion()).as("Component Version").isEqualTo(liveSystemComponentDto.getVersion());
            softly.assertThat(blueprintComponentDto.getParameters()).as("Component Parameters").containsAllEntriesOf(liveSystemComponentDto.getParameters());
            softly.assertThat(blueprintComponentDto.getDependencies()).as("Component Dependencies").containsAll(liveSystemComponentDto.getDependencies());
            softly.assertThat(blueprintComponentDto.getLinks()).as("Component Links").containsAll(liveSystemComponentDto.getLinks());
            softly.assertThat(blueprintComponentDto.getOutputFields()).as("Component Output Fields").containsAllEntriesOf(liveSystemComponentDto.getOutputFields());
        });
    }

}