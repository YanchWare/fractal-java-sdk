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
public class AzureStorageAccount extends BaseAzureStorageAccount {
  private Collection<AzureBlobContainer> containers;

  public AzureStorageAccount() {
    this.containers = new ArrayList<>();
  }

  public static AzureStorageAccountBuilder builder() {
    return new AzureStorageAccountBuilder();
  }

  @Override
  public String getKind() {
    return "StorageV2";
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

    public AzureStorageAccountBuilder withContainers(Collection<AzureBlobContainer> containers) {

      if (CollectionUtils.isBlank(containers)) {
        return builder;
      }

      containers.forEach(f -> {
        f.getDependencies().add(component.getId());
        f.setAzureRegion(component.getAzureRegion());
        f.setAzureResourceGroup(component.getAzureResourceGroup());
      });

      this.component.containers.addAll(containers);
      return this;
    }

    public AzureStorageAccountBuilder withContainer(AzureBlobContainer container) {
      return withContainers(List.of(container));
    }
  }
}
