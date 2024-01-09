package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureStorageSkuNameTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureStorageAccountSkuName.STANDARD_LRS, "STANDARD_LRS constant should not be null");
    assertNotNull(AzureStorageAccountSkuName.STANDARD_GRS, "STANDARD_GRS constant should not be null");
    assertNotNull(AzureStorageAccountSkuName.STANDARD_RAGRS, "STANDARD_RAGRS constant should not be null");
    assertNotNull(AzureStorageAccountSkuName.STANDARD_ZRS, "STANDARD_ZRS constant should not be null");
    assertNotNull(AzureStorageAccountSkuName.PREMIUM_LRS, "PREMIUM_LRS constant should not be null");
    assertNotNull(AzureStorageAccountSkuName.PREMIUM_ZRS, "PREMIUM_ZRS constant should not be null");
    assertNotNull(AzureStorageAccountSkuName.STANDARD_GZRS, "STANDARD_GZRS constant should not be null");
    assertNotNull(AzureStorageAccountSkuName.STANDARD_RAGZRS, "STANDARD_RAGZRS constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureStorageAccountSkuName.STANDARD_LRS,
        AzureStorageAccountSkuName.fromString("Standard_LRS"), 
        "fromString should return STANDARD_LRS for 'Standard_LRS'");

    assertEquals(AzureStorageAccountSkuName.STANDARD_GRS,
        AzureStorageAccountSkuName.fromString("Standard_GRS"),
        "fromString should return STANDARD_GRS for 'Standard_GRS'");

    assertEquals(AzureStorageAccountSkuName.STANDARD_RAGRS,
        AzureStorageAccountSkuName.fromString("Standard_RAGRS"),
        "fromString should return STANDARD_RAGRS for 'Standard_RAGRS'");

    assertEquals(AzureStorageAccountSkuName.STANDARD_ZRS,
        AzureStorageAccountSkuName.fromString("Standard_ZRS"),
        "fromString should return STANDARD_ZRS for 'Standard_ZRS'");

    assertEquals(AzureStorageAccountSkuName.PREMIUM_LRS,
        AzureStorageAccountSkuName.fromString("Premium_LRS"),
        "fromString should return PREMIUM_LRS for 'Premium_LRS'");

    assertEquals(AzureStorageAccountSkuName.PREMIUM_ZRS,
        AzureStorageAccountSkuName.fromString("Premium_ZRS"),
        "fromString should return PREMIUM_ZRS for 'Premium_ZRS'");

    assertEquals(AzureStorageAccountSkuName.STANDARD_GZRS,
        AzureStorageAccountSkuName.fromString("Standard_GZRS"),
        "fromString should return STANDARD_GZRS for 'Standard_GZRS'");

    assertEquals(AzureStorageAccountSkuName.STANDARD_RAGZRS,
        AzureStorageAccountSkuName.fromString("Standard_RAGZRS"),
        "fromString should return STANDARD_RAGZRS for 'Standard_RAGZRS'");
   }

  @Test
  public void testValuesMethod() {
    Collection<AzureStorageAccountSkuName> values = AzureStorageAccountSkuName.values();
    assertTrue(values.contains(AzureStorageAccountSkuName.STANDARD_LRS), "Values should contain STANDARD_LRS");
    assertTrue(values.contains(AzureStorageAccountSkuName.STANDARD_GRS), "Values should contain STANDARD_GRS");
    assertTrue(values.contains(AzureStorageAccountSkuName.STANDARD_RAGRS), "Values should contain STANDARD_RAGRS");
    assertTrue(values.contains(AzureStorageAccountSkuName.STANDARD_ZRS), "Values should contain STANDARD_ZRS");
    assertTrue(values.contains(AzureStorageAccountSkuName.PREMIUM_LRS), "Values should contain PREMIUM_LRS");
    assertTrue(values.contains(AzureStorageAccountSkuName.PREMIUM_ZRS), "Values should contain PREMIUM_ZRS");
    assertTrue(values.contains(AzureStorageAccountSkuName.STANDARD_GZRS), "Values should contain STANDARD_GZRS");
    assertTrue(values.contains(AzureStorageAccountSkuName.STANDARD_RAGZRS), "Values should contain STANDARD_RAGZRS");
    assertEquals(8, values.size(), "There should be exactly 8 values");
  }
}