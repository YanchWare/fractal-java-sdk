package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureFileShareRootSquashTypeTest {

  @Test
  public void azureFileShareRootSquashTypeConstants_shouldNotBeNull() {
    assertThat(AzureFileShareRootSquashType.ALL_SQUASH)
      .as("ALL_SQUASH constant should not be null")
      .isNotNull();

    assertThat(AzureFileShareRootSquashType.NO_ROOT_SQUASH)
      .as("NO_ROOT_SQUASH constant should not be null")
      .isNotNull();

    assertThat(AzureFileShareRootSquashType.ROOT_SQUASH)
      .as("ROOT_SQUASH constant should not be null")
      .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureFileShareRootSquashType() {
    assertThat(AzureFileShareRootSquashType.fromString("AllSquash"))
      .as("fromString should return ALL_SQUASH for 'AllSquash'")
      .isEqualTo(AzureFileShareRootSquashType.ALL_SQUASH);

    assertThat(AzureFileShareRootSquashType.fromString("NoRootSquash"))
      .as("fromString should return NO_ROOT_SQUASH for 'NoRootSquash'")
      .isEqualTo(AzureFileShareRootSquashType.NO_ROOT_SQUASH);

    assertThat(AzureFileShareRootSquashType.fromString("RootSquash"))
      .as("fromString should return ROOT_SQUASH for 'RootSquash'")
      .isEqualTo(AzureFileShareRootSquashType.ROOT_SQUASH);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureFileShareRootSquashTypesWithCorrectSize() {
    Collection<AzureFileShareRootSquashType> values = AzureFileShareRootSquashType.values();

    assertThat(values)
      .as("Values should contain ALL_SQUASH, NO_ROOT_SQUASH, and ROOT_SQUASH and have exactly 3 values")
      .containsExactlyInAnyOrder(AzureFileShareRootSquashType.ALL_SQUASH,
        AzureFileShareRootSquashType.NO_ROOT_SQUASH,
        AzureFileShareRootSquashType.ROOT_SQUASH);
  }
}