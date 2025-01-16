package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * The SKU name.
 * Required for account creation.
 */
public final class AzureStorageAccountSkuName extends ExtendableEnum<AzureStorageAccountSkuName> {
  public static final AzureStorageAccountSkuName STANDARD_LRS = fromString("Standard_LRS");
  public static final AzureStorageAccountSkuName STANDARD_GRS = fromString("Standard_GRS");
  public static final AzureStorageAccountSkuName STANDARD_RAGRS = fromString("Standard_RAGRS");
  public static final AzureStorageAccountSkuName STANDARD_ZRS = fromString("Standard_ZRS");
  public static final AzureStorageAccountSkuName PREMIUM_LRS = fromString("Premium_LRS");
  public static final AzureStorageAccountSkuName PREMIUM_ZRS = fromString("Premium_ZRS");
  public static final AzureStorageAccountSkuName STANDARD_GZRS = fromString("Standard_GZRS");
  public static final AzureStorageAccountSkuName STANDARD_RAGZRS = fromString("Standard_RAGZRS");

  public AzureStorageAccountSkuName() {
  }

  @JsonCreator
  public static AzureStorageAccountSkuName fromString(String name) {
    return fromString(name, AzureStorageAccountSkuName.class);
  }

  public static Collection<AzureStorageAccountSkuName> values() {
    return values(AzureStorageAccountSkuName.class);
  }
}
