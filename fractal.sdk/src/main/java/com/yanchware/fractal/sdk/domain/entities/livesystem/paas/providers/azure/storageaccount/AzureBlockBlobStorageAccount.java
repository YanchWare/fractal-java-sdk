package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

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
public class AzureBlockBlobStorageAccount extends BaseAzureStorageAccount {
  private Boolean isHnsEnabled;
  private Collection<AzureBlobContainer> containers;

  public AzureBlockBlobStorageAccount() {
    this.containers = new ArrayList<>();
  }
  
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

    /**
     * <pre>
     * Enables Account HierarchicalNamespace, if sets to true.
     * </pre>
     */
    public AzureBlockBlobStorageAccountBuilder withIsHnsEnabled(Boolean isHnsEnabled) {
      component.setIsHnsEnabled(isHnsEnabled);

      return builder;
    }

    public AzureBlockBlobStorageAccountBuilder withContainers(Collection<AzureBlobContainer> containers) {

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

    public AzureBlockBlobStorageAccountBuilder withContainer(AzureBlobContainer container) {
      return withContainers(List.of(container));
    }
  }
}
