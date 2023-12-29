package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureFileShareEnabledProtocolsTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureFileShareEnabledProtocols.NFS, "NFS constant should not be null");
    assertNotNull(AzureFileShareEnabledProtocols.SMB, "SMB constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureFileShareEnabledProtocols.NFS,
        AzureFileShareEnabledProtocols.fromString("NFS"), 
        "fromString should return NFS for 'NFS'");
    
    assertEquals(AzureFileShareEnabledProtocols.SMB,
        AzureFileShareEnabledProtocols.fromString("SMB"), 
        "fromString should return SMB for 'SMB'");

  }

  @Test
  public void testValuesMethod() {
    Collection<AzureFileShareEnabledProtocols> values = AzureFileShareEnabledProtocols.values();
    assertTrue(values.contains(AzureFileShareEnabledProtocols.NFS), "Values should contain NFS");
    assertTrue(values.contains(AzureFileShareEnabledProtocols.SMB), "Values should contain SMB");
    assertEquals(2, values.size(), "There should be exactly 2 values");
  }
}