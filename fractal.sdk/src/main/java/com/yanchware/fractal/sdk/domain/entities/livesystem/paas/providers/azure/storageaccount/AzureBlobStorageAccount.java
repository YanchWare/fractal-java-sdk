package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureBlobStorageAccount extends BaseAzureStorageAccount {
  @Override
  public String getKind() {
    return "BlobStorage";
  }

  public static AzureBlobStorageAccountBuilder builder() {
    return new AzureBlobStorageAccountBuilder();
  }

  public static class AzureBlobStorageAccountBuilder extends Builder<AzureBlobStorageAccount, AzureBlobStorageAccountBuilder> {

    @Override
    protected AzureBlobStorageAccount createComponent() {
      return new AzureBlobStorageAccount();
    }

    @Override
    protected AzureBlobStorageAccountBuilder getBuilder() {
      return this;
    }
  }
}
