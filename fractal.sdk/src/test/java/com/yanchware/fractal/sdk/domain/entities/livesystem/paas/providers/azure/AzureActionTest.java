package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureActionTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureAction.ALLOW, "ALLOW constant should not be null");
    assertNotNull(AzureAction.DENY, "DENY constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureAction.ALLOW, AzureAction.fromString("Allow"), 
        "fromString should return ALLOW for 'Allow'");
    
    assertEquals(AzureAction.DENY, AzureAction.fromString("Deny"), 
        "fromString should return DENY for 'Deny'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureAction> values = AzureAction.values();
    assertTrue(values.contains(AzureAction.ALLOW), "Values should contain ALLOW");
    assertTrue(values.contains(AzureAction.DENY), "Values should contain DENY");
    assertEquals(2, values.size(), "There should be exactly 2 value");
  }
}