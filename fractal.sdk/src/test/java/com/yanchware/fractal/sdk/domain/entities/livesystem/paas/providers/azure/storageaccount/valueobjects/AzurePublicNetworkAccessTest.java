package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzurePublicNetworkAccessTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzurePublicNetworkAccess.ENABLED, "ENABLED constant should not be null");
    assertNotNull(AzurePublicNetworkAccess.DISABLED, "DISABLED constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzurePublicNetworkAccess.ENABLED, AzurePublicNetworkAccess.fromString("Enabled"),
        "fromString should return ENABLED for 'Enabled'");

    assertEquals(AzurePublicNetworkAccess.DISABLED, AzurePublicNetworkAccess.fromString("Disabled"),
        "fromString should return DISABLED for 'Disabled'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzurePublicNetworkAccess> values = AzurePublicNetworkAccess.values();
    assertTrue(values.contains(AzurePublicNetworkAccess.ENABLED), "Values should contain ENABLED");
    assertTrue(values.contains(AzurePublicNetworkAccess.DISABLED), "Values should contain DISABLED");
    assertEquals(2, values.size(), "There should be exactly 2 value");
  }
}