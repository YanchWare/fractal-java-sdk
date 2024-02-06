package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureBypassTest {
  @Test
  public void azureBypassConstants_shouldNotBeNull() {
    assertThat(AzureBypass.NONE)
        .as("NONE constant should not be null")
        .isNotNull();

    assertThat(AzureBypass.LOGGING)
        .as("LOGGING constant should not be null")
        .isNotNull();

    assertThat(AzureBypass.METRICS)
        .as("METRICS constant should not be null")
        .isNotNull();

    assertThat(AzureBypass.AZURE_SERVICES)
        .as("AZURE_SERVICES constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureBypass() {
    assertThat(AzureBypass.fromString("None"))
        .as("fromString should return NONE for 'None'")
        .isEqualTo(AzureBypass.NONE);

    assertThat(AzureBypass.fromString("Logging"))
        .as("fromString should return LOGGING for 'Logging'")
        .isEqualTo(AzureBypass.LOGGING);

    assertThat(AzureBypass.fromString("Metrics"))
        .as("fromString should return METRICS for 'Metrics'")
        .isEqualTo(AzureBypass.METRICS);

    assertThat(AzureBypass.fromString("AzureServices"))
        .as("fromString should return AZURE_SERVICES for 'AzureServices'")
        .isEqualTo(AzureBypass.AZURE_SERVICES);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureBypassesWithCorrectSize() {
    Collection<AzureBypass> values = AzureBypass.values();

    assertThat(values)
        .as("Values should contain NONE, LOGGING, METRICS, and AZURE_SERVICES and have exactly 4 values")
        .containsExactlyInAnyOrder(AzureBypass.NONE,
            AzureBypass.LOGGING,
            AzureBypass.METRICS,
            AzureBypass.AZURE_SERVICES);
  }
}