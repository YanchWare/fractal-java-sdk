package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureStorageAccountExtendedLocationTypesTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureStorageAccountExtendedLocationTypes.EDGE_ZONE, "EDGE_ZONE constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureStorageAccountExtendedLocationTypes.EDGE_ZONE,
        AzureStorageAccountExtendedLocationTypes.fromString("EdgeZone"),
        "fromString should return EDGE_ZONE for 'EdgeZone'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureStorageAccountExtendedLocationTypes> values = AzureStorageAccountExtendedLocationTypes.values();
    assertTrue(values.contains(AzureStorageAccountExtendedLocationTypes.EDGE_ZONE), "Values should contain EDGE_ZONE");
    assertEquals(1, values.size(), "There should be exactly 1 value");
  }
}