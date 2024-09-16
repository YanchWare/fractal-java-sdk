package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureFileShareEnabledProtocolsTest {
  @Test
  public void azureFileShareEnabledProtocolsConstants_shouldNotBeNull() {
    assertThat(AzureFileShareEnabledProtocols.NFS)
        .as("NFS constant should not be null")
        .isNotNull();

    assertThat(AzureFileShareEnabledProtocols.SMB)
        .as("SMB constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureFileShareEnabledProtocols() {
    assertThat(AzureFileShareEnabledProtocols.fromString("NFS"))
        .as("fromString should return NFS for 'NFS'")
        .isEqualTo(AzureFileShareEnabledProtocols.NFS);
    
    assertThat(AzureFileShareEnabledProtocols.fromString("SMB"))
        .as("fromString should return SMB for 'SMB'")
        .isEqualTo(AzureFileShareEnabledProtocols.SMB);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureFileShareEnabledProtocolsWithCorrectSize() {
    Collection<AzureFileShareEnabledProtocols> values = AzureFileShareEnabledProtocols.values();

    assertThat(values)
        .as("Values should contain NFS, and SMB and have exactly 2 values")
        .containsExactlyInAnyOrder(AzureFileShareEnabledProtocols.NFS, 
            AzureFileShareEnabledProtocols.SMB);
  }
}