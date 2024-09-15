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
public class AzureFileStorageAccount extends BaseAzureStorageAccount {
  private Collection<AzureFileShare> fileShares;
  private AzureStorageAccountFileService fileService;

  public AzureFileStorageAccount() {
    this.fileShares = new ArrayList<>();
  }
  
  @Override
  public String getKind() {
    return "FileStorage";
  }

  public static AzureFileStorageAccountBuilder builder() {
    return new AzureFileStorageAccountBuilder();
  }

  public static class AzureFileStorageAccountBuilder extends Builder<AzureFileStorageAccount, AzureFileStorageAccountBuilder> {

    @Override
    protected AzureFileStorageAccount createComponent() {
      return new AzureFileStorageAccount();
    }

    @Override
    protected AzureFileStorageAccountBuilder getBuilder() {
      return this;
    }

    public AzureFileStorageAccountBuilder withFileService(AzureStorageAccountFileService fileService) {
      component.setFileService(fileService);
      return builder;
    }

    public AzureFileStorageAccountBuilder withFileShares(Collection<AzureFileShare> fileShares) {

      if (CollectionUtils.isBlank(fileShares)) {
        return builder;
      }

      fileShares.forEach(f -> {
        f.getDependencies().add(component.getId());
        f.setAzureRegion(component.getAzureRegion());
        f.setAzureResourceGroup(component.getAzureResourceGroup());
      });
      
      this.component.fileShares.addAll(fileShares);
      return this;
    }

    public AzureFileStorageAccountBuilder withFileShare(AzureFileShare fileShare) {
      return withFileShares(List.of(fileShare));
    }
  }
}
