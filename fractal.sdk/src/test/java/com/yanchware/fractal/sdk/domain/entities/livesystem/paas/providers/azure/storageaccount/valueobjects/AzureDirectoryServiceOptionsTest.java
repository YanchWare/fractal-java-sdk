package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureDirectoryServiceOptionsTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureDirectoryServiceOptions.NONE, "NONE constant should not be null");
    assertNotNull(AzureDirectoryServiceOptions.AADDS, "AADDS constant should not be null");
    assertNotNull(AzureDirectoryServiceOptions.AD, "AD constant should not be null");
    assertNotNull(AzureDirectoryServiceOptions.AADKERB, "AADKERB constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureDirectoryServiceOptions.NONE,
        AzureDirectoryServiceOptions.fromString("None"),
        "fromString should return NONE for 'None'");

    assertEquals(AzureDirectoryServiceOptions.AADDS,
        AzureDirectoryServiceOptions.fromString("AADDS"),
        "fromString should return AADDS for 'AADDS'");

    assertEquals(AzureDirectoryServiceOptions.AD,
        AzureDirectoryServiceOptions.fromString("AD"),
        "fromString should return AD for 'AD'");

    assertEquals(AzureDirectoryServiceOptions.AADKERB,
        AzureDirectoryServiceOptions.fromString("AADKERB"),
        "fromString should return AADKERB for 'AADKERB'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureDirectoryServiceOptions> values = AzureDirectoryServiceOptions.values();
    assertTrue(values.contains(AzureDirectoryServiceOptions.NONE), "Values should contain NONE");
    assertTrue(values.contains(AzureDirectoryServiceOptions.AADDS), "Values should contain AADDS");
    assertTrue(values.contains(AzureDirectoryServiceOptions.AD), "Values should contain AD");
    assertTrue(values.contains(AzureDirectoryServiceOptions.AADKERB), "Values should contain AADKERB");
    assertEquals(4, values.size(), "There should be exactly 4 values");
  }
}