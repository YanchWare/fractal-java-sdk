package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureStorageAccountExtendedLocationTypesTest {
  @Test
  public void azureStorageAccountExtendedLocationTypesConstants_shouldNotBeNull() {
    assertThat(AzureStorageAccountExtendedLocationTypes.EDGE_ZONE)
        .as("EDGE_ZONE constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureStorageAccountExtendedLocationTypes() {
    assertThat(AzureStorageAccountExtendedLocationTypes.fromString("EdgeZone"))
        .as("fromString should return EDGE_ZONE for 'EdgeZone'")
        .isEqualTo(AzureStorageAccountExtendedLocationTypes.EDGE_ZONE);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureStorageAccountExtendedLocationTypesWithCorrectSize() {
    Collection<AzureStorageAccountExtendedLocationTypes> values = AzureStorageAccountExtendedLocationTypes.values();

    assertThat(values)
        .as("Values should contain EDGE_ZONE and have exactly 1 value")
        .containsExactlyInAnyOrder(AzureStorageAccountExtendedLocationTypes.EDGE_ZONE);
  }
}