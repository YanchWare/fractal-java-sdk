package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.aks;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AzureKubernetesAddonProfileTest {
  @Test
  public void validationError_when_profileNameIsNull() {
    assertThatThrownBy(() -> AzureKubernetesAddonProfile.builder()
        .withAddonToEnable(null)
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("addonToEnable variable is not set");
  }

  @Test
  public void noValidationErrors_when_allRequiredVariablesProvided() {
    assertThat(AzureKubernetesAddonProfile.builder()
        .withAddonToEnable(AzureKubernetesAddon.AZURE_POLICY)
        .build()
        .validate()
    ).isEmpty();
  }

  @Test
  public void azureAddonProfileHasAllVariables() {
    AzureKubernetesAddonProfile addonProfile = AzureKubernetesAddonProfile
        .builder()
        .withAddonToEnable(AzureKubernetesAddon.MONITORING)
        .withConfig(Map.of("logAnalyticsWorkspaceResourceID",
                "/subscriptions/<subscription_id>/resourceGroups/<resource_group_name>",
                "useAADAuth",
                "False"))
        .build();
    assertThat(addonProfile.getAddonToEnable()).isEqualTo(AzureKubernetesAddon.MONITORING);
    assertThat(addonProfile.getConfig()).extracting("logAnalyticsWorkspaceResourceID", "useAADAuth")
        .containsExactly("/subscriptions/<subscription_id>/resourceGroups/<resource_group_name>", "False");
  }
}