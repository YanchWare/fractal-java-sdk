package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureBlockBlobStorageAccount extends BaseAzureStorageAccount {
  @Override
  public String getKind() {
    return "BlockBlobStorage";
  }

  public static AzureBlockBlobStorageAccountBuilder builder() {
    return new AzureBlockBlobStorageAccountBuilder();
  }

  public static class AzureBlockBlobStorageAccountBuilder extends Builder<AzureBlockBlobStorageAccount, AzureBlockBlobStorageAccountBuilder> {

    @Override
    protected AzureBlockBlobStorageAccount createComponent() {
      return new AzureBlockBlobStorageAccount();
    }

    @Override
    protected AzureBlockBlobStorageAccountBuilder getBuilder() {
      return this;
    }
  }
}
