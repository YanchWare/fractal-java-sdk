package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureAccountImmutabilityPolicyStateTest {
  @Test
  public void azureAccountImmutabilityPolicyStateConstants_shouldNotBeNull() {
    assertThat(AzureAccountImmutabilityPolicyState.UNLOCKED)
        .as("UNLOCKED constant should not be null")
        .isNotNull();

    assertThat(AzureAccountImmutabilityPolicyState.LOCKED)
        .as("LOCKED constant should not be null")
        .isNotNull();

    assertThat(AzureAccountImmutabilityPolicyState.DISABLED)
        .as("DISABLED constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureAccountImmutabilityPolicyState() {
    assertThat(AzureAccountImmutabilityPolicyState.fromString("Unlocked"))
        .as("fromString should return UNLOCKED for 'Unlocked'")
        .isEqualTo(AzureAccountImmutabilityPolicyState.UNLOCKED);

    assertThat(AzureAccountImmutabilityPolicyState.fromString("Locked"))
        .as("fromString should return LOCKED for 'Locked'")
        .isEqualTo(AzureAccountImmutabilityPolicyState.LOCKED);

    assertThat(AzureAccountImmutabilityPolicyState.fromString("Disabled"))
        .as("fromString should return DISABLED for 'Disabled'")
        .isEqualTo(AzureAccountImmutabilityPolicyState.DISABLED);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureAccountImmutabilityPolicyStatesWithCorrectSize() {
    Collection<AzureAccountImmutabilityPolicyState> values = AzureAccountImmutabilityPolicyState.values();

    assertThat(values)
        .as("Values should contain UNLOCKED, LOCKED, and DISABLED and have exactly 3 values")
        .containsExactlyInAnyOrder(
            AzureAccountImmutabilityPolicyState.UNLOCKED,
            AzureAccountImmutabilityPolicyState.LOCKED,
            AzureAccountImmutabilityPolicyState.DISABLED);
  }
}