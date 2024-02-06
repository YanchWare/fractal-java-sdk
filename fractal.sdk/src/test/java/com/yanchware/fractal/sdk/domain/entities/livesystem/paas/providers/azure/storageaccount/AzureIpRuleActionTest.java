package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureIpRuleActionTest {
  @Test
  public void azureNetworkActionConstants_shouldNotBeNull() {
    assertThat(AzureNetworkAction.ALLOW)
        .as("ALLOW constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureNetworkAction() {
    assertThat(AzureNetworkAction.fromString("Allow"))
        .as("fromString should return ALLOW for 'Allow'")
        .isEqualTo(AzureNetworkAction.ALLOW);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureNetworkActionsWithCorrectSize() {
    Collection<AzureNetworkAction> values = AzureNetworkAction.values();

    assertThat(values)
        .as("Values should contain ALLOW and have exactly 1 value")
        .containsExactlyInAnyOrder(AzureNetworkAction.ALLOW);
  }
}