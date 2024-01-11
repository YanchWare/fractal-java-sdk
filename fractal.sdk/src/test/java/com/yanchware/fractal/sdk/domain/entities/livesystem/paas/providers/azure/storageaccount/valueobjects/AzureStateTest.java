package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureStateTest {
  @Test
  public void azureStateConstants_shouldNotBeNull() {
    assertThat(AzureState.PROVISIONING)
        .as("PROVISIONING constant should not be null")
        .isNotNull();

    assertThat(AzureState.DEPROVISIONING)
        .as("DEPROVISIONING constant should not be null")
        .isNotNull();

    assertThat(AzureState.SUCCEEDED)
        .as("SUCCEEDED constant should not be null")
        .isNotNull();

    assertThat(AzureState.FAILED)
        .as("FAILED constant should not be null")
        .isNotNull();

    assertThat(AzureState.NETWORK_SOURCE_DELETED)
        .as("NETWORK_SOURCE_DELETED constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureState() {
    assertThat(AzureState.fromString("Provisioning"))
        .as("fromString should return PROVISIONING for 'Provisioning'")
        .isEqualTo(AzureState.PROVISIONING);
    
    assertThat(AzureState.fromString("Deprovisioning"))
        .as("fromString should return DEPROVISIONING for 'Deprovisioning'")
        .isEqualTo(AzureState.DEPROVISIONING);

    assertThat(AzureState.fromString("Succeeded"))
        .as("fromString should return SUCCEEDED for 'Succeeded'")
        .isEqualTo(AzureState.SUCCEEDED);
    
    assertThat(AzureState.fromString("Failed"))
        .as("fromString should return FAILED for 'Failed'")
        .isEqualTo(AzureState.FAILED);

    assertThat(AzureState.fromString("NetworkSourceDeleted"))
        .as("fromString should return NETWORK_SOURCE_DELETED for 'NetworkSourceDeleted'")
        .isEqualTo(AzureState.NETWORK_SOURCE_DELETED);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureStatesWithCorrectSize() {
    Collection<AzureState> values = AzureState.values();

    assertThat(values)
        .as("Values should contain PROVISIONING, DEPROVISIONING, SUCCEEDED, FAILED, and NETWORK_SOURCE_DELETED and have exactly 5 values")
        .containsExactlyInAnyOrder(AzureState.PROVISIONING,
            AzureState.DEPROVISIONING,
            AzureState.SUCCEEDED,
            AzureState.FAILED,
            AzureState.NETWORK_SOURCE_DELETED);
  }
}