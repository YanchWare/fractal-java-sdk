package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureTlsVersionTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureTlsVersion.TLS1_0, "TLS1_0 constant should not be null");
    assertNotNull(AzureTlsVersion.TLS1_1, "TLS1_1 constant should not be null");
    assertNotNull(AzureTlsVersion.TLS1_2, "TLS1_2 constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureTlsVersion.TLS1_0, AzureTlsVersion.fromString("TLS1_0"),
        "fromString should return TLS1_0 for 'TLS1_0'");

    assertEquals(AzureTlsVersion.TLS1_1, AzureTlsVersion.fromString("TLS1_1"),
        "fromString should return TLS1_1 for 'TLS1_1'");

    assertEquals(AzureTlsVersion.TLS1_2, AzureTlsVersion.fromString("TLS1_2"),
        "fromString should return TLS1_2 for 'TLS1_2'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureTlsVersion> values = AzureTlsVersion.values();
    assertTrue(values.contains(AzureTlsVersion.TLS1_0), "Values should contain TLS1_0");
    assertTrue(values.contains(AzureTlsVersion.TLS1_1), "Values should contain TLS1_1");
    assertTrue(values.contains(AzureTlsVersion.TLS1_2), "Values should contain TLS1_2");
    assertEquals(3, values.size(), "There should be exactly 3 value");
  }
}