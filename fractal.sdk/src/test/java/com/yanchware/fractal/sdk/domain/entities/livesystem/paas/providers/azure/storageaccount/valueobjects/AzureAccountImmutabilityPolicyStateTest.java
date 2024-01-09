package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureAccountImmutabilityPolicyStateTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureAccountImmutabilityPolicyState.UNLOCKED, "UNLOCKED constant should not be null");
    assertNotNull(AzureAccountImmutabilityPolicyState.LOCKED, "LOCKED constant should not be null");
    assertNotNull(AzureAccountImmutabilityPolicyState.DISABLED, "DISABLED constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureAccountImmutabilityPolicyState.UNLOCKED, 
        AzureAccountImmutabilityPolicyState.fromString("Unlocked"),
        "fromString should return UNLOCKED for 'Unlocked'");

    assertEquals(AzureAccountImmutabilityPolicyState.LOCKED,
        AzureAccountImmutabilityPolicyState.fromString("Locked"),
        "fromString should return LOCKED for 'Locked'");

    assertEquals(AzureAccountImmutabilityPolicyState.DISABLED,
        AzureAccountImmutabilityPolicyState.fromString("Disabled"),
        "fromString should return DISABLED for 'Disabled'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureAccountImmutabilityPolicyState> values = AzureAccountImmutabilityPolicyState.values();
    assertTrue(values.contains(AzureAccountImmutabilityPolicyState.UNLOCKED), "Values should contain UNLOCKED");
    assertTrue(values.contains(AzureAccountImmutabilityPolicyState.LOCKED), "Values should contain LOCKED");
    assertTrue(values.contains(AzureAccountImmutabilityPolicyState.DISABLED), "Values should contain DISABLED");
    assertEquals(3, values.size(), "There should be exactly 3 value");
  }
}