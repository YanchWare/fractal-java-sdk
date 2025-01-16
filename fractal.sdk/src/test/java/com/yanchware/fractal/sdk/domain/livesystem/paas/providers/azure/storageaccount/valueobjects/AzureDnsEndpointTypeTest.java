package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureDnsEndpointTypeTest {
  @Test
  public void azureDnsEndpointTypeConstants_shouldNotBeNull() {
    assertThat(AzureDnsEndpointType.STANDARD)
      .as("STANDARD constant should not be null")
      .isNotNull();

    assertThat(AzureDnsEndpointType.AZURE_DNS_ZONE)
      .as("AZURE_DNS_ZONE constant should not be null")
      .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureDnsEndpointType() {
    assertThat(AzureDnsEndpointType.fromString("Standard"))
      .as("fromString should return STANDARD for 'Standard'")
      .isEqualTo(AzureDnsEndpointType.STANDARD);

    assertThat(AzureDnsEndpointType.fromString("AzureDnsZone"))
      .as("fromString should return STANDARD for 'Standard'")
      .isEqualTo(AzureDnsEndpointType.AZURE_DNS_ZONE);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureDnsEndpointTypesWithCorrectSize() {
    Collection<AzureDnsEndpointType> values = AzureDnsEndpointType.values();

    assertThat(values)
      .as("Values should contain STANDARD, and AZURE_DNS_ZONE and have exactly 2 values")
      .containsExactlyInAnyOrder(AzureDnsEndpointType.STANDARD, AzureDnsEndpointType.AZURE_DNS_ZONE);
  }
}