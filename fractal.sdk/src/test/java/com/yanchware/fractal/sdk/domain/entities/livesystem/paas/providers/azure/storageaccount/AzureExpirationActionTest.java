package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureExpirationActionTest {
  @Test
  public void azureExpirationActionConstants_shouldNotBeNull() {
    assertThat(AzureExpirationAction.LOG)
        .as("LOG constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureExpirationAction() {
    assertThat(AzureExpirationAction.fromString("Log"))
        .as("fromString should return LOG for 'Log'")
        .isEqualTo(AzureExpirationAction.LOG);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureExpirationActionsWithCorrectSize() {
    Collection<AzureExpirationAction> values = AzureExpirationAction.values();

    assertThat(values)
        .as("Values should contain LOG and have exactly 1 value")
        .containsExactlyInAnyOrder(AzureExpirationAction.LOG);
  }
}