package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureRoutingChoiceTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureRoutingChoice.MICROSOFT_ROUTING, "MICROSOFT_ROUTING constant should not be null");
    assertNotNull(AzureRoutingChoice.INTERNET_ROUTING, "INTERNET_ROUTING constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureRoutingChoice.MICROSOFT_ROUTING, AzureRoutingChoice.fromString("MicrosoftRouting"),
        "fromString should return MICROSOFT_ROUTING for 'MicrosoftRouting'");

    assertEquals(AzureRoutingChoice.INTERNET_ROUTING, AzureRoutingChoice.fromString("InternetRouting"),
        "fromString should return INTERNET_ROUTING for 'InternetRouting'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureRoutingChoice> values = AzureRoutingChoice.values();
    assertTrue(values.contains(AzureRoutingChoice.MICROSOFT_ROUTING), "Values should contain MICROSOFT_ROUTING");
    assertTrue(values.contains(AzureRoutingChoice.INTERNET_ROUTING), "Values should contain INTERNET_ROUTING");
    assertEquals(2, values.size(), "There should be exactly 2 value");
  }

}