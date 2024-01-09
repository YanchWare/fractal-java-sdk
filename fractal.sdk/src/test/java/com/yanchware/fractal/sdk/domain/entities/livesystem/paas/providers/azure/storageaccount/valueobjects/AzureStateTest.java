package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureStateTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureState.PROVISIONING, "PROVISIONING constant should not be null");
    assertNotNull(AzureState.DEPROVISIONING, "DEPROVISIONING constant should not be null");
    assertNotNull(AzureState.SUCCEEDED, "SUCCEEDED constant should not be null");
    assertNotNull(AzureState.FAILED, "FAILED constant should not be null");
    assertNotNull(AzureState.NETWORK_SOURCE_DELETED, "NETWORK_SOURCE_DELETED constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureState.PROVISIONING, AzureState.fromString("Provisioning"),
        "fromString should return PROVISIONING for 'Provisioning'");

    assertEquals(AzureState.DEPROVISIONING, AzureState.fromString("Deprovisioning"),
        "fromString should return DEPROVISIONING for 'Deprovisioning'");

    assertEquals(AzureState.SUCCEEDED, AzureState.fromString("Succeeded"),
        "fromString should return SUCCEEDED for 'Succeeded'");

    assertEquals(AzureState.FAILED, AzureState.fromString("Failed"),
        "fromString should return FAILED for 'Failed'");

    assertEquals(AzureState.NETWORK_SOURCE_DELETED, AzureState.fromString("NetworkSourceDeleted"),
        "fromString should return NETWORK_SOURCE_DELETED for 'NetworkSourceDeleted'");
    
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureState> values = AzureState.values();
    assertTrue(values.contains(AzureState.PROVISIONING), "Values should contain PROVISIONING");
    assertTrue(values.contains(AzureState.DEPROVISIONING), "Values should contain DEPROVISIONING");
    assertTrue(values.contains(AzureState.SUCCEEDED), "Values should contain SUCCEEDED");
    assertTrue(values.contains(AzureState.FAILED), "Values should contain FAILED");
    assertTrue(values.contains(AzureState.NETWORK_SOURCE_DELETED), "Values should contain NETWORK_SOURCE_DELETED");
    assertEquals(5, values.size(), "There should be exactly 5 value");
  }
}