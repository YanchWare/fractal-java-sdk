package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureBypassTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureBypass.NONE, "NONE constant should not be null");
    assertNotNull(AzureBypass.LOGGING, "LOGGING constant should not be null");
    assertNotNull(AzureBypass.METRICS, "METRICS constant should not be null");
    assertNotNull(AzureBypass.AZURE_SERVICES, "AZURE_SERVICES constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureBypass.NONE, AzureBypass.fromString("None"), 
        "fromString should return NONE for 'None'");
    
    assertEquals(AzureBypass.LOGGING, AzureBypass.fromString("Logging"), 
        "fromString should return LOGGING for 'Logging'");
    
    assertEquals(AzureBypass.METRICS, AzureBypass.fromString("Metrics"), 
        "fromString should return METRICS for 'Metrics'");
    
    assertEquals(AzureBypass.AZURE_SERVICES, AzureBypass.fromString("AzureServices"), 
        "fromString should return AZURE_SERVICES for 'AzureServices'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureBypass> values = AzureBypass.values();
    assertTrue(values.contains(AzureBypass.NONE), "Values should contain NONE");
    assertTrue(values.contains(AzureBypass.LOGGING), "Values should contain LOGGING");
    assertTrue(values.contains(AzureBypass.METRICS), "Values should contain METRICS");
    assertTrue(values.contains(AzureBypass.AZURE_SERVICES), "Values should contain AZURE_SERVICES");
    assertEquals(4, values.size(), "There should be exactly 4 value");
  }
}