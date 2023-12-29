package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureLegacyStorageAccount extends BaseAzureFileStorage {
  @Override
  public String getKind() {
    return "Storage";
  }

  public static AzureLegacyStorageAccountBuilder builder() {
    return new AzureLegacyStorageAccountBuilder();
  }

  public static class AzureLegacyStorageAccountBuilder extends BaseAzureFileStorage.Builder<AzureLegacyStorageAccount, AzureLegacyStorageAccountBuilder> {

    @Override
    protected AzureLegacyStorageAccount createComponent() {
      return new AzureLegacyStorageAccount();
    }

    @Override
    protected AzureLegacyStorageAccountBuilder getBuilder() {
      return this;
    }
  }
}
