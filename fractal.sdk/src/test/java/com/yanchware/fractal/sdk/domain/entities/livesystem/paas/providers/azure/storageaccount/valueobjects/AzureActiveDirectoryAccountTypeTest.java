package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureActiveDirectoryAccountTypeTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureActiveDirectoryAccountType.USER, "USER constant should not be null");
    assertNotNull(AzureActiveDirectoryAccountType.COMPUTER, "COMPUTER constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureActiveDirectoryAccountType.USER,
        AzureActiveDirectoryAccountType.fromString("User"),
        "fromString should return USER for 'User'");

    assertEquals(AzureActiveDirectoryAccountType.COMPUTER,
        AzureActiveDirectoryAccountType.fromString("Computer"),
        "fromString should return COMPUTER for 'Computer'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureActiveDirectoryAccountType> values = AzureActiveDirectoryAccountType.values();
    assertTrue(values.contains(AzureActiveDirectoryAccountType.USER), "Values should contain USER");
    assertTrue(values.contains(AzureActiveDirectoryAccountType.COMPUTER), "Values should contain COMPUTER");
    assertEquals(2, values.size(), "There should be exactly 2 values");
  }
}