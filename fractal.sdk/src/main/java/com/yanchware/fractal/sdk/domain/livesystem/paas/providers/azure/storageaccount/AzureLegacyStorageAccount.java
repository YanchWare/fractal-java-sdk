package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;


import com.yanchware.fractal.sdk.utils.CollectionUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureLegacyStorageAccount extends BaseAzureStorageAccount {
  @Override
  public String getKind() {
    return "Storage";
  }

  private AzureStorageAccountFileService fileService;

  private Collection<AzureFileShare> fileShares;

  public AzureLegacyStorageAccount() {
    this.fileShares = new ArrayList<>();
  }

  public static AzureLegacyStorageAccountBuilder builder() {
    return new AzureLegacyStorageAccountBuilder();
  }

  public static class AzureLegacyStorageAccountBuilder extends BaseAzureStorageAccount.Builder<AzureLegacyStorageAccount, AzureLegacyStorageAccountBuilder> {

    public AzureLegacyStorageAccountBuilder withFileService(AzureStorageAccountFileService fileService) {
      component.setFileService(fileService);
      return builder;
    }

    public AzureLegacyStorageAccountBuilder withFileShares(Collection<AzureFileShare> fileShares) {

      if (CollectionUtils.isBlank(fileShares)) {
        return builder;
      }

      fileShares.forEach(f -> {
        f.getDependencies().add(component.getId());
        f.setAzureRegion(component.getAzureRegion());
        f.setAzureResourceGroup(component.getAzureResourceGroup());
      });

      this.component.fileShares.addAll(fileShares);
      return builder;
    }

    public AzureLegacyStorageAccountBuilder withFileShare(AzureFileShare fileShare) {
      return withFileShares(List.of(fileShare));
    }
    
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
