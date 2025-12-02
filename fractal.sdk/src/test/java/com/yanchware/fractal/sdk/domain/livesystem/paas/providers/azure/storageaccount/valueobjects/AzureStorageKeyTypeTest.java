package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureStorageKeyTypeTest {
  @Test
  public void azureStorageKeyTypeConstants_shouldNotBeNull() {
    assertThat(AzureStorageKeyType.SERVICE)
      .as("SERVICE constant should not be null")
      .isNotNull();

    assertThat(AzureStorageKeyType.ACCOUNT)
      .as("ACCOUNT constant should not be null")
      .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureStorageKeyType() {
    assertThat(AzureStorageKeyType.fromString("Service"))
      .as("fromString should return SERVICE for 'Service'")
      .isEqualTo(AzureStorageKeyType.SERVICE);

    assertThat(AzureStorageKeyType.fromString("Account"))
      .as("fromString should return ACCOUNT for 'Account'")
      .isEqualTo(AzureStorageKeyType.ACCOUNT);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureStorageKeyTypesWithCorrectSize() {
    Collection<AzureStorageKeyType> values = AzureStorageKeyType.values();

    assertThat(values)
      .as("Values should contain SERVICE, and ACCOUNT and have exactly 2 values")
      .containsExactlyInAnyOrder(AzureStorageKeyType.SERVICE, AzureStorageKeyType.ACCOUNT);
  }
}