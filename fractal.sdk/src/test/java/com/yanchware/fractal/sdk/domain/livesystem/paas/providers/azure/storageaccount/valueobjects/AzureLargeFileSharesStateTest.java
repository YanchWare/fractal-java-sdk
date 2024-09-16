package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureLargeFileSharesStateTest {
  @Test
  public void azureLargeFileSharesStateConstants_shouldNotBeNull() {
    assertThat(AzureLargeFileSharesState.DISABLED)
        .as("DISABLED constant should not be null")
        .isNotNull();
    
    assertThat(AzureLargeFileSharesState.ENABLED)
        .as("ENABLED constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureAction() {
    assertThat(AzureLargeFileSharesState.fromString("Disabled"))
        .as("fromString should return DISABLED for 'Disabled'")
        .isEqualTo(AzureLargeFileSharesState.DISABLED);

    assertThat(AzureLargeFileSharesState.fromString("Enabled"))
        .as("fromString should return ENABLED for 'Enabled'")
        .isEqualTo(AzureLargeFileSharesState.ENABLED);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureActionsWithCorrectSize() {
    Collection<AzureLargeFileSharesState> values = AzureLargeFileSharesState.values();

    assertThat(values)
        .as("Values should contain DISABLED, ENABLED and have exactly 2 values")
        .containsExactlyInAnyOrder(AzureLargeFileSharesState.DISABLED, 
            AzureLargeFileSharesState.ENABLED);
  }
}