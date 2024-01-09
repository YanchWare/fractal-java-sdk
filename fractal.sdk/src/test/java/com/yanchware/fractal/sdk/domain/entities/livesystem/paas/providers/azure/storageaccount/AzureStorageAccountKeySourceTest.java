package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureStorageAccountKeySourceTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureStorageAccountKeySource.MICROSOFT_STORAGE, "MICROSOFT_STORAGE constant should not be null");
    assertNotNull(AzureStorageAccountKeySource.MICROSOFT_KEYVAULT, "MICROSOFT_KEYVAULT constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureStorageAccountKeySource.MICROSOFT_STORAGE,
        AzureStorageAccountKeySource.fromString("Microsoft.Storage"),
        "fromString should return MICROSOFT_STORAGE for 'Microsoft.Storage'");

    assertEquals(AzureStorageAccountKeySource.MICROSOFT_KEYVAULT,
        AzureStorageAccountKeySource.fromString("Microsoft.Keyvault"),
        "fromString should return MICROSOFT_KEYVAULT for 'Microsoft.Keyvault'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureStorageAccountKeySource> values = AzureStorageAccountKeySource.values();
    assertTrue(values.contains(AzureStorageAccountKeySource.MICROSOFT_STORAGE), "Values should contain MICROSOFT_STORAGE");
    assertTrue(values.contains(AzureStorageAccountKeySource.MICROSOFT_KEYVAULT), "Values should contain MICROSOFT_KEYVAULT");
    assertEquals(2, values.size(), "There should be exactly 2 value");
  }
}