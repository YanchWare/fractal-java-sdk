package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureStorageAccountAllowedCopyScopeTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureStorageAccountAllowedCopyScope.PRIVATE_LINK, "PRIVATE_LINK constant should not be null");
    assertNotNull(AzureStorageAccountAllowedCopyScope.AAD, "AAD constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureStorageAccountAllowedCopyScope.PRIVATE_LINK,
        AzureStorageAccountAllowedCopyScope.fromString("PrivateLink"),
        "fromString should return PRIVATE_LINK for 'PrivateLink'");

    assertEquals(AzureStorageAccountAllowedCopyScope.AAD,
        AzureStorageAccountAllowedCopyScope.fromString("AAD"),
        "fromString should return AAD for 'AAD'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureStorageAccountAllowedCopyScope> values = AzureStorageAccountAllowedCopyScope.values();
    assertTrue(values.contains(AzureStorageAccountAllowedCopyScope.PRIVATE_LINK), "Values should contain PRIVATE_LINK");
    assertTrue(values.contains(AzureStorageAccountAllowedCopyScope.AAD), "Values should contain AAD");
    assertEquals(2, values.size(), "There should be exactly 2 values");
  }
}