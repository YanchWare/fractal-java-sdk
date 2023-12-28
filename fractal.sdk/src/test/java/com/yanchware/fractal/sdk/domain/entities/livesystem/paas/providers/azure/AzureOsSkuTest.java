package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AzureOsSkuTest {
  
  @Test
  public void testAzureLinuxInstance() {
    assertEquals("AzureLinux", AzureOsSku.AZURE_LINUX.toString());
  }

  @Test
  public void testCBLMarinerInstance() throws NoSuchFieldException {
    // Use reflection to check if CBLMARINER is deprecated
    assertTrue(AzureOsSku.class.getField("CBLMARINER").isAnnotationPresent(Deprecated.class));
    assertEquals("CBLMariner", AzureOsSku.CBLMARINER.toString());
  }

  @Test
  public void testUbuntuInstance() {
    assertEquals("Ubuntu", AzureOsSku.UBUNTU.toString());
  }

  @Test
  public void testWindows2019Instance() {
    assertEquals("Windows2019", AzureOsSku.WINDOWS2019.toString());
  }

  @Test
  public void testWindows2022Instance() {
    assertEquals("Windows2022", AzureOsSku.WINDOWS2022.toString());
  }

  @Test
  public void testFromString() {
    assertEquals(AzureOsSku.AZURE_LINUX, AzureOsSku.fromString("AzureLinux"));
    assertEquals(AzureOsSku.UBUNTU, AzureOsSku.fromString("Ubuntu"));
    assertEquals(AzureOsSku.WINDOWS2019, AzureOsSku.fromString("Windows2019"));
    assertEquals(AzureOsSku.WINDOWS2022, AzureOsSku.fromString("Windows2022"));
  }

  @Test
  public void testValuesMethod() {
    assertTrue(AzureOsSku.values().contains(AzureOsSku.AZURE_LINUX));
    assertTrue(AzureOsSku.values().contains(AzureOsSku.UBUNTU));
    assertTrue(AzureOsSku.values().contains(AzureOsSku.WINDOWS2019));
    assertTrue(AzureOsSku.values().contains(AzureOsSku.WINDOWS2022));
    assertTrue(AzureOsSku.values().contains(AzureOsSku.CBLMARINER));
  }
}