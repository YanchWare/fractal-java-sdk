package com.yanchware.fractal.sdk.domain.entities;

import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ComponentLinkTest {

    @Test
    public void componentLinkValid_when_IdPassedFromComponent() {
        var aks = TestUtils.getAksExample();
        ComponentLink componentLink = ComponentLink.builder().withComponentId(aks).build();
        assertThat(componentLink.getComponentId()).isEqualTo(aks.getId().getValue());
    }

    @Test
    public void componentLinkValid_when_IdPassedFromComponentId() {
        var aks = TestUtils.getAksExample();
        Map<String, Object> settings = Map.of("keySetting", "valueSetting");
        ComponentLink componentLink = ComponentLink.builder().withComponentId(aks.getId()).withSettings(settings).build();
        assertThat(componentLink.getComponentId()).isEqualTo(aks.getId().getValue());
        assertThat(componentLink.getSettings()).isEqualTo(settings);
    }

    @Test
    public void componentLinkValidationErrors_when_ComponentIdNotValid() {
        assertThatThrownBy(() -> ComponentLink.builder().withComponentId("A").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("A valid component id must start with a lowercase character");
    }

}