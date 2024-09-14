package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * Restrict copy to and from Storage Accounts within an AAD tenant or with Private Links to the same VNet.
 */
public final class AzureStorageAccountAllowedCopyScope extends ExtendableEnum<AzureStorageAccountAllowedCopyScope> {
  public static final AzureStorageAccountAllowedCopyScope PRIVATE_LINK = fromString("PrivateLink");
  public static final AzureStorageAccountAllowedCopyScope AAD = fromString("AAD");

  public AzureStorageAccountAllowedCopyScope() {
  }

  @JsonCreator
  public static AzureStorageAccountAllowedCopyScope fromString(String name) {
    return fromString(name, AzureStorageAccountAllowedCopyScope.class);
  }

  public static Collection<AzureStorageAccountAllowedCopyScope> values() {
    return values(AzureStorageAccountAllowedCopyScope.class);
  }
}
