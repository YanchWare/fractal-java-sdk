package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureIpRuleActionTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureNetworkAction.ALLOW, "ALLOW constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureNetworkAction.ALLOW, AzureNetworkAction.fromString("Allow"),
        "fromString should return ALLOW for 'Allow'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureNetworkAction> values = AzureNetworkAction.values();
    assertTrue(values.contains(AzureNetworkAction.ALLOW), "Values should contain ALLOW");
    assertEquals(1, values.size(), "There should be exactly 1 value");
  }
}