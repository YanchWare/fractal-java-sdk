package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public final class AzureStorageAccountKeySource extends ExtendableEnum<AzureStorageAccountKeySource> {

  public static final AzureStorageAccountKeySource MICROSOFT_STORAGE = fromString("Microsoft.Storage");
  public static final AzureStorageAccountKeySource MICROSOFT_KEYVAULT = fromString("Microsoft.Keyvault");

  @JsonCreator
  public static AzureStorageAccountKeySource fromString(String name) {
    return fromString(name, AzureStorageAccountKeySource.class);
  }

  public static Collection<AzureStorageAccountKeySource> values() {
    return values(AzureStorageAccountKeySource.class);
  }
}
