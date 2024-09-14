package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public final class AzureStorageKeyType extends ExtendableEnum<AzureStorageKeyType> {
  public static final AzureStorageKeyType SERVICE = fromString("Service");
  public static final AzureStorageKeyType ACCOUNT = fromString("Account");

  @JsonCreator
  public static AzureStorageKeyType fromString(String name) {
    return fromString(name, AzureStorageKeyType.class);
  }

  public static Collection<AzureStorageKeyType> values() {
    return values(AzureStorageKeyType.class);
  }
}
