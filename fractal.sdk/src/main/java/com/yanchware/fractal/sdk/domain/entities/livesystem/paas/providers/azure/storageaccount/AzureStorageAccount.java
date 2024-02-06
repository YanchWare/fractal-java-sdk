package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureStorageAccount extends BaseAzureStorageAccount {
  @Override
  public String getKind() {
    return "StorageV2";
  }

  public static AzureStorageAccountBuilder builder() {
    return new AzureStorageAccountBuilder();
  }

  public static class AzureStorageAccountBuilder extends Builder<AzureStorageAccount, AzureStorageAccountBuilder> {

    @Override
    protected AzureStorageAccount createComponent() {
      return new AzureStorageAccount();
    }

    @Override
    protected AzureStorageAccountBuilder getBuilder() {
      return this;
    }
  }
}
