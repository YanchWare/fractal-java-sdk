package com.yanchware.fractal.sdk.domain.entities;

import com.yanchware.fractal.sdk.domain.ComponentLink;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
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
    public void componentLinkValid_when_SettingsAndRoleNameProvided() {
        var aks = TestUtils.getAksExample();
        Map<String, Object> settings = Map.of("keySetting", "valueSetting");
        String roleName = "Owner";
        
        ComponentLink componentLink = ComponentLink.builder()
            .withComponentId(aks.getId())
            .withSettings(settings)
            .withRoleName(roleName)
            .build();

        Map<String, Object> expectedSettings = new HashMap<>(settings);
        expectedSettings.put("roleName", roleName);
        
        assertThat(componentLink.getComponentId()).isEqualTo(aks.getId().getValue());
        assertThat(componentLink.getSettings()).isEqualTo(expectedSettings);
    }

    @Test
    public void componentLinkValid_when_RoleNameProvided() {
        var aks = TestUtils.getAksExample();
        String roleName = "Owner";

        ComponentLink componentLink = ComponentLink.builder()
            .withComponentId(aks.getId())
            .withRoleName(roleName)
            .build();

        Map<String, Object> expectedSettings = new HashMap<>();
        expectedSettings.put("roleName", roleName);

        assertThat(componentLink.getComponentId()).isEqualTo(aks.getId().getValue());
        assertThat(componentLink.getSettings()).isEqualTo(expectedSettings);
    }

    @Test
    public void lastRoleNameUsed_when_MultipleRoleNameProvided() {
        var aks = TestUtils.getAksExample();
        String roleName1 = "Owner";
        String roleName2 = "Contributor";

        ComponentLink componentLink = ComponentLink.builder()
            .withComponentId(aks.getId())
            .withRoleName(roleName1)
            .withRoleName(roleName2)
            .build();

        Map<String, Object> expectedSettings = new HashMap<>();
        expectedSettings.put("roleName", roleName2);

        assertThat(componentLink.getComponentId()).isEqualTo(aks.getId().getValue());
        assertThat(componentLink.getSettings()).isEqualTo(expectedSettings);
    }

    @Test
    public void componentLinkValidationErrors_when_ComponentIdNotValid() {
        assertThatThrownBy(() -> ComponentLink.builder().withComponentId("A").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("A valid component id must start with a lowercase character");
    }

}