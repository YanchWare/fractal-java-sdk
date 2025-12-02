package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureStorageSkuNameTest {
  @Test
  public void azureStorageAccountSkuNameConstants_shouldNotBeNull() {
    assertThat(AzureStorageAccountSkuName.STANDARD_LRS)
      .as("STANDARD_LRS constant should not be null")
      .isNotNull();

    assertThat(AzureStorageAccountSkuName.STANDARD_GRS)
      .as("STANDARD_GRS constant should not be null")
      .isNotNull();

    assertThat(AzureStorageAccountSkuName.STANDARD_RAGRS)
      .as("STANDARD_RAGRS constant should not be null")
      .isNotNull();

    assertThat(AzureStorageAccountSkuName.STANDARD_ZRS)
      .as("STANDARD_ZRS constant should not be null")
      .isNotNull();

    assertThat(AzureStorageAccountSkuName.PREMIUM_LRS)
      .as("PREMIUM_LRS constant should not be null")
      .isNotNull();

    assertThat(AzureStorageAccountSkuName.PREMIUM_ZRS)
      .as("PREMIUM_ZRS constant should not be null")
      .isNotNull();

    assertThat(AzureStorageAccountSkuName.STANDARD_GZRS)
      .as("STANDARD_GZRS constant should not be null")
      .isNotNull();

    assertThat(AzureStorageAccountSkuName.STANDARD_RAGZRS)
      .as("STANDARD_RAGZRS constant should not be null")
      .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureStorageAccountSkuName() {
    assertThat(AzureStorageAccountSkuName.fromString("Standard_LRS"))
      .as("fromString should return STANDARD_LRS for 'Standard_LRS'")
      .isEqualTo(AzureStorageAccountSkuName.STANDARD_LRS);

    assertThat(AzureStorageAccountSkuName.fromString("Standard_GRS"))
      .as("fromString should return STANDARD_GRS for 'Standard_GRS'")
      .isEqualTo(AzureStorageAccountSkuName.STANDARD_GRS);

    assertThat(AzureStorageAccountSkuName.fromString("Standard_RAGRS"))
      .as("fromString should return STANDARD_RAGRS for 'Standard_RAGRS'")
      .isEqualTo(AzureStorageAccountSkuName.STANDARD_RAGRS);

    assertThat(AzureStorageAccountSkuName.fromString("Standard_ZRS"))
      .as("fromString should return STANDARD_ZRS for 'Standard_ZRS'")
      .isEqualTo(AzureStorageAccountSkuName.STANDARD_ZRS);

    assertThat(AzureStorageAccountSkuName.fromString("Premium_LRS"))
      .as("fromString should return PREMIUM_LRS for 'Premium_LRS'")
      .isEqualTo(AzureStorageAccountSkuName.PREMIUM_LRS);

    assertThat(AzureStorageAccountSkuName.fromString("Premium_ZRS"))
      .as("fromString should return PREMIUM_ZRS for 'Premium_ZRS'")
      .isEqualTo(AzureStorageAccountSkuName.PREMIUM_ZRS);

    assertThat(AzureStorageAccountSkuName.fromString("Standard_GZRS"))
      .as("fromString should return STANDARD_GZRS for 'Standard_GZRS'")
      .isEqualTo(AzureStorageAccountSkuName.STANDARD_GZRS);

    assertThat(AzureStorageAccountSkuName.fromString("Standard_RAGZRS"))
      .as("fromString should return STANDARD_RAGZRS for 'Standard_RAGZRS'")
      .isEqualTo(AzureStorageAccountSkuName.STANDARD_RAGZRS);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureStorageAccountSkuNamesWithCorrectSize() {
    Collection<AzureStorageAccountSkuName> values = AzureStorageAccountSkuName.values();

    assertThat(values)
      .as("Values should contain STANDARD_LRS, STANDARD_GRS, STANDARD_RAGRS, STANDARD_ZRS, PREMIUM_LRS, " +
        "PREMIUM_ZRS, STANDARD_GZRS and STANDARD_RAGZRS and have exactly 8 values")
      .containsExactlyInAnyOrder(AzureStorageAccountSkuName.STANDARD_LRS,
        AzureStorageAccountSkuName.STANDARD_GRS,
        AzureStorageAccountSkuName.STANDARD_RAGRS,
        AzureStorageAccountSkuName.STANDARD_ZRS,
        AzureStorageAccountSkuName.PREMIUM_LRS,
        AzureStorageAccountSkuName.PREMIUM_ZRS,
        AzureStorageAccountSkuName.STANDARD_GZRS,
        AzureStorageAccountSkuName.STANDARD_RAGZRS);
  }
}