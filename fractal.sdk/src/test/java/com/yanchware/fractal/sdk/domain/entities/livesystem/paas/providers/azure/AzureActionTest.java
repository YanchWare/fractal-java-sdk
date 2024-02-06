package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureActionTest {
  @Test
  public void azureActionConstants_shouldNotBeNull() {
    assertThat(AzureAction.ALLOW).as("ALLOW constant should not be null").isNotNull();
    assertThat(AzureAction.DENY).as("DENY constant should not be null").isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureAction() {
    assertThat(AzureAction.fromString("Allow"))
        .as("fromString should return ALLOW for 'Allow'")
        .isEqualTo(AzureAction.ALLOW);

    assertThat(AzureAction.fromString("Deny"))
        .as("fromString should return DENY for 'Deny'")
        .isEqualTo(AzureAction.DENY);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureActionsWithCorrectSize() {
    Collection<AzureAction> values = AzureAction.values();

    assertThat(values)
        .as("Values should contain ALLOW, DENY and have exactly 2 values")
        .containsExactlyInAnyOrder(AzureAction.ALLOW, AzureAction.DENY);
  }
}