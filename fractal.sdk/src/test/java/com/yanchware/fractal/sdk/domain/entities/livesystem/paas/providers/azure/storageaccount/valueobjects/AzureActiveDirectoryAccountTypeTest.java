package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureActiveDirectoryAccountTypeTest {
  @Test
  public void azureActiveDirectoryAccountTypeConstants_shouldNotBeNull() {
    assertThat(AzureActiveDirectoryAccountType.USER)
        .as("USER constant should not be null")
        .isNotNull();

    assertThat(AzureActiveDirectoryAccountType.COMPUTER)
        .as("COMPUTER constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureActiveDirectoryAccountType() {
    assertThat(AzureActiveDirectoryAccountType.fromString("User"))
        .as("fromString should return USER for 'User'")
        .isEqualTo(AzureActiveDirectoryAccountType.USER);

    assertThat(AzureActiveDirectoryAccountType.fromString("Computer"))
        .as("fromString should return COMPUTER for 'Computer'")
        .isEqualTo(AzureActiveDirectoryAccountType.COMPUTER);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureActiveDirectoryAccountTypesWithCorrectSize() {
    Collection<AzureActiveDirectoryAccountType> values = AzureActiveDirectoryAccountType.values();

    assertThat(values)
        .as("Values should contain USER, and COMPUTER and have exactly 2 values")
        .containsExactlyInAnyOrder(AzureActiveDirectoryAccountType.USER,
            AzureActiveDirectoryAccountType.COMPUTER);
  }
}