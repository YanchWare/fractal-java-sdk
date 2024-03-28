package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureBlobContainerImmutableStorageWithVersioning {
  private Boolean enabled;

  public static AzureBlobContainerImmutableStorageWithVersioningBuilder builder() {
    return new AzureBlobContainerImmutableStorageWithVersioningBuilder();
  }

  public static class AzureBlobContainerImmutableStorageWithVersioningBuilder {
    private final AzureBlobContainerImmutableStorageWithVersioning instance;
    private final AzureBlobContainerImmutableStorageWithVersioningBuilder builder;

    public AzureBlobContainerImmutableStorageWithVersioningBuilder() {
      this.instance = new AzureBlobContainerImmutableStorageWithVersioning();
      this.builder = this;
    }
    
    public AzureBlobContainerImmutableStorageWithVersioningBuilder withEnabled(Boolean enabled) {
      instance.setEnabled(enabled);
      return builder;
    }
    

    public AzureBlobContainerImmutableStorageWithVersioning build() {
      return instance;
    }
  }
}
