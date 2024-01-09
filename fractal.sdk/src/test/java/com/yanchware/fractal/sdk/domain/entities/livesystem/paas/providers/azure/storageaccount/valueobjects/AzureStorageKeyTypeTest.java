package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureStorageKeyTypeTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureStorageKeyType.SERVICE, "SERVICE constant should not be null");
    assertNotNull(AzureStorageKeyType.ACCOUNT, "ACCOUNT constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureStorageKeyType.SERVICE, AzureStorageKeyType.fromString("Service"),
        "fromString should return SERVICE for 'Service'");

    assertEquals(AzureStorageKeyType.ACCOUNT, AzureStorageKeyType.fromString("Account"),
        "fromString should return ACCOUNT for 'Account'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureStorageKeyType> values = AzureStorageKeyType.values();
    assertTrue(values.contains(AzureStorageKeyType.SERVICE), "Values should contain SERVICE");
    assertTrue(values.contains(AzureStorageKeyType.ACCOUNT), "Values should contain ACCOUNT");
    assertEquals(2, values.size(), "There should be exactly 2 value");
  }
}