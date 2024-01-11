package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzurePublicNetworkAccessTest {
  @Test
  public void azurePublicNetworkAccessConstants_shouldNotBeNull() {
    assertThat(AzurePublicNetworkAccess.ENABLED)
        .as("ENABLED constant should not be null")
        .isNotNull();

    assertThat(AzurePublicNetworkAccess.DISABLED)
        .as("DISABLED constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzurePublicNetworkAccess() {
    assertThat(AzurePublicNetworkAccess.fromString("Enabled"))
        .as("fromString should return ENABLED for 'Enabled'")
        .isEqualTo(AzurePublicNetworkAccess.ENABLED);
    
    assertThat(AzurePublicNetworkAccess.fromString("Disabled"))
        .as("fromString should return DISABLED for 'Disabled'")
        .isEqualTo(AzurePublicNetworkAccess.DISABLED);
  }

  @Test
  public void valuesMethod_shouldContainAllAzurePublicNetworkAccessesWithCorrectSize() {
    Collection<AzurePublicNetworkAccess> values = AzurePublicNetworkAccess.values();

    assertThat(values)
        .as("Values should contain ENABLED, and DISABLED and have exactly 2 values")
        .containsExactlyInAnyOrder(AzurePublicNetworkAccess.ENABLED,
            AzurePublicNetworkAccess.DISABLED);
  }
}