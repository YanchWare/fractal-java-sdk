package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureStorageAccountAllowedCopyScopeTest {
  @Test
  public void azureStorageAccountAllowedCopyScopeConstants_shouldNotBeNull() {
    assertThat(AzureStorageAccountAllowedCopyScope.PRIVATE_LINK)
      .as("PRIVATE_LINK constant should not be null")
      .isNotNull();

    assertThat(AzureStorageAccountAllowedCopyScope.AAD)
      .as("AAD constant should not be null")
      .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureStorageAccountAllowedCopyScope() {
    assertThat(AzureStorageAccountAllowedCopyScope.fromString("PrivateLink"))
      .as("fromString should return PRIVATE_LINK for 'PrivateLink'")
      .isEqualTo(AzureStorageAccountAllowedCopyScope.PRIVATE_LINK);

    assertThat(AzureStorageAccountAllowedCopyScope.fromString("AAD"))
      .as("fromString should return AAD for 'AAD'")
      .isEqualTo(AzureStorageAccountAllowedCopyScope.AAD);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureStorageAccountAllowedCopyScopesWithCorrectSize() {
    Collection<AzureStorageAccountAllowedCopyScope> values = AzureStorageAccountAllowedCopyScope.values();

    assertThat(values)
      .as("Values should contain PRIVATE_LINK, and AAD and have exactly 2 values")
      .containsExactlyInAnyOrder(AzureStorageAccountAllowedCopyScope.PRIVATE_LINK,
        AzureStorageAccountAllowedCopyScope.AAD);
  }
}