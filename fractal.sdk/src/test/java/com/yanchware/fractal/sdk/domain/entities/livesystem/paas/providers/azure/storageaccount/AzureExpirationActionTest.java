package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureExpirationActionTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureExpirationAction.LOG, "LOG constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureExpirationAction.LOG, AzureExpirationAction.fromString("Log"),
        "fromString should return LOG for 'Log'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureExpirationAction> values = AzureExpirationAction.values();
    assertTrue(values.contains(AzureExpirationAction.LOG), "Values should contain LOG");
    assertEquals(1, values.size(), "There should be exactly 1 value");
  }
}