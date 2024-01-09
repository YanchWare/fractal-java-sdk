package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureDnsEndpointTypeTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureDnsEndpointType.STANDARD, "STANDARD constant should not be null");
    assertNotNull(AzureDnsEndpointType.AZURE_DNS_ZONE, "AZURE_DNS_ZONE constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureDnsEndpointType.STANDARD, AzureDnsEndpointType.fromString("Standard"),
        "fromString should return STANDARD for 'Standard'");

    assertEquals(AzureDnsEndpointType.AZURE_DNS_ZONE, AzureDnsEndpointType.fromString("AzureDnsZone"),
        "fromString should return STANDARD for 'Standard'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureDnsEndpointType> values = AzureDnsEndpointType.values();
    assertTrue(values.contains(AzureDnsEndpointType.STANDARD), "Values should contain STANDARD");
    assertTrue(values.contains(AzureDnsEndpointType.AZURE_DNS_ZONE), "Values should contain AZURE_DNS_ZONE");
    assertEquals(2, values.size(), "There should be exactly 2 values");
  }
}