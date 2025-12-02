package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class ManagedClusterSkuTierTest {
  @Test
  public void managedClusterSkuTierConstants_shouldNotBeNull() {
    assertThat(ManagedClusterSkuTier.PREMIUM).as("PREMIUM constant should not be null").isNotNull();
    assertThat(ManagedClusterSkuTier.STANDARD).as("STANDARD constant should not be null").isNotNull();
    assertThat(ManagedClusterSkuTier.FREE).as("FREE constant should not be null").isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingManagedClusterSkuTier() {
    assertThat(ManagedClusterSkuTier.fromString("Premium"))
      .as("fromString should return PREMIUM for 'Premium'")
      .isEqualTo(ManagedClusterSkuTier.PREMIUM);

    assertThat(ManagedClusterSkuTier.fromString("Standard"))
      .as("fromString should return STANDARD for 'Standard'")
      .isEqualTo(ManagedClusterSkuTier.STANDARD);

    assertThat(ManagedClusterSkuTier.fromString("Free"))
      .as("fromString should return FREE for 'Free'")
      .isEqualTo(ManagedClusterSkuTier.FREE);
  }

  @Test
  public void valuesMethod_shouldContainAllManagedClusterSkuTiersWithCorrectSize() {
    Collection<ManagedClusterSkuTier> values = ManagedClusterSkuTier.values();

    assertThat(values)
      .as("Values should contain PREMIUM, STANDARD, FREE and have exactly 3 values")
      .containsExactlyInAnyOrder(ManagedClusterSkuTier.PREMIUM,
        ManagedClusterSkuTier.STANDARD,
        ManagedClusterSkuTier.FREE);
  }
}