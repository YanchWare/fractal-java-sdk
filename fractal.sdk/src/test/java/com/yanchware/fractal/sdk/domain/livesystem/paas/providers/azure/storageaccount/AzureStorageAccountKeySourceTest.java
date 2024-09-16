package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureStorageAccountKeySourceTest {
  @Test
  public void azureStorageAccountKeySourceConstants_shouldNotBeNull() {
    assertThat(AzureStorageAccountKeySource.MICROSOFT_STORAGE)
        .as("MICROSOFT_STORAGE constant should not be null")
        .isNotNull();

    assertThat(AzureStorageAccountKeySource.MICROSOFT_KEYVAULT)
        .as("MICROSOFT_KEYVAULT constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureStorageAccountKeySource() {
    assertThat(AzureStorageAccountKeySource.fromString("Microsoft.Storage"))
        .as("fromString should return MICROSOFT_STORAGE for 'Microsoft.Storage'")
        .isEqualTo(AzureStorageAccountKeySource.MICROSOFT_STORAGE);

    assertThat(AzureStorageAccountKeySource.fromString("Microsoft.Keyvault"))
        .as("fromString should return MICROSOFT_KEYVAULT for 'Microsoft.Keyvault'")
        .isEqualTo(AzureStorageAccountKeySource.MICROSOFT_KEYVAULT);
  }

  @Test
  public void valuesMethod_shouldContainAlAzureStorageAccountKeySourcesWithCorrectSize() {
    Collection<AzureStorageAccountKeySource> values = AzureStorageAccountKeySource.values();

    assertThat(values)
        .as("Values should contain MICROSOFT_STORAGE, and MICROSOFT_KEYVAULT and have exactly 2 values")
        .containsExactlyInAnyOrder(AzureStorageAccountKeySource.MICROSOFT_STORAGE,
            AzureStorageAccountKeySource.MICROSOFT_KEYVAULT);
  }
}