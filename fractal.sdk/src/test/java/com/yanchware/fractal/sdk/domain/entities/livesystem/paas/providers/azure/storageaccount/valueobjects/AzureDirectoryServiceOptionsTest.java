package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureDirectoryServiceOptionsTest {
  @Test
  public void azureDirectoryServiceOptionsConstants_shouldNotBeNull() {
    assertThat(AzureDirectoryServiceOptions.NONE)
        .as("NONE constant should not be null")
        .isNotNull();

    assertThat(AzureDirectoryServiceOptions.AADDS)
        .as("AADDS constant should not be null")
        .isNotNull();

    assertThat(AzureDirectoryServiceOptions.AD)
        .as("AD constant should not be null")
        .isNotNull();

    assertThat(AzureDirectoryServiceOptions.AADKERB)
        .as("AADKERB constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureDirectoryServiceOptions() {
    assertThat(AzureDirectoryServiceOptions.fromString("None"))
        .as("fromString should return NONE for 'None'")
        .isEqualTo(AzureDirectoryServiceOptions.NONE);

    assertThat(AzureDirectoryServiceOptions.fromString("AADDS"))
        .as("fromString should return AADDS for 'AADDS'")
        .isEqualTo(AzureDirectoryServiceOptions.AADDS);
    
    assertThat(AzureDirectoryServiceOptions.fromString("AD"))
        .as("fromString should return AD for 'AD'")
        .isEqualTo(AzureDirectoryServiceOptions.AD);

    assertThat(AzureDirectoryServiceOptions.fromString("AADKERB"))
        .as("fromString should return AADKERB for 'AADKERB'")
        .isEqualTo(AzureDirectoryServiceOptions.AADKERB);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureDirectoryServiceOptionsWithCorrectSize() {
    Collection<AzureDirectoryServiceOptions> values = AzureDirectoryServiceOptions.values();

    assertThat(values)
        .as("Values should contain NONE, AADDS, AD, and AADKERB and have exactly 4 values")
        .containsExactlyInAnyOrder(AzureDirectoryServiceOptions.NONE,
            AzureDirectoryServiceOptions.AADDS,
            AzureDirectoryServiceOptions.AD,
            AzureDirectoryServiceOptions.AADKERB);
  }
}