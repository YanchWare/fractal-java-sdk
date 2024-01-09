package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureIdentityTypeTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureIdentityType.NONE, "NONE constant should not be null");
    assertNotNull(AzureIdentityType.SYSTEM_ASSIGNED, "SYSTEM_ASSIGNED constant should not be null");
    assertNotNull(AzureIdentityType.USER_ASSIGNED, "USER_ASSIGNED constant should not be null");
    assertNotNull(AzureIdentityType.SYSTEM_ASSIGNED_USER_ASSIGNED, "SYSTEM_ASSIGNED_USER_ASSIGNED constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureIdentityType.NONE, AzureIdentityType.fromString("None"),
        "fromString should return NONE for 'None'");

    assertEquals(AzureIdentityType.SYSTEM_ASSIGNED, AzureIdentityType.fromString("SystemAssigned"),
        "fromString should return SYSTEM_ASSIGNED for 'SystemAssigned'");

    assertEquals(AzureIdentityType.USER_ASSIGNED, AzureIdentityType.fromString("UserAssigned"),
        "fromString should return USER_ASSIGNED for 'UserAssigned'");

    assertEquals(AzureIdentityType.SYSTEM_ASSIGNED_USER_ASSIGNED, AzureIdentityType.fromString("SystemAssigned,UserAssigned"),
        "fromString should return SYSTEM_ASSIGNED_USER_ASSIGNED for 'SystemAssigned,UserAssigned'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureIdentityType> values = AzureIdentityType.values();
    assertTrue(values.contains(AzureIdentityType.NONE), "Values should contain NONE");
    assertTrue(values.contains(AzureIdentityType.SYSTEM_ASSIGNED), "Values should contain SYSTEM_ASSIGNED");
    assertTrue(values.contains(AzureIdentityType.USER_ASSIGNED), "Values should contain USER_ASSIGNED");
    assertTrue(values.contains(AzureIdentityType.SYSTEM_ASSIGNED_USER_ASSIGNED), "Values should contain SYSTEM_ASSIGNED_USER_ASSIGNED");
    assertEquals(4, values.size(), "There should be exactly 4 value");
  }
}