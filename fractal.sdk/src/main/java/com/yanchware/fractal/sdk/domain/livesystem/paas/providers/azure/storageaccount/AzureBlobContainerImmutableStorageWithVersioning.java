package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

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

    /**
     * <pre>
     * Sets whether immutable storage with versioning is enabled for the Azure Blob Container.
     *
     * Enabling immutable storage with versioning ensures that blobs cannot be modified
     * or deleted once they are created, providing an additional layer of data protection.
     * This setting is particularly useful for compliance and data retention scenarios.</pre>
     *
     * @param enabled A <code>Boolean</code> indicating whether immutable storage with versioning is enabled.
     * @return The builder instance for chaining.
     */
    public AzureBlobContainerImmutableStorageWithVersioningBuilder withEnabled(Boolean enabled) {
      instance.setEnabled(enabled);
      return builder;
    }

    /**
     * <pre>
     * Builds the configured {@link AzureBlobContainerImmutableStorageWithVersioning} instance.
     *
     * This method finalizes the configuration of the
     * {@link AzureBlobContainerImmutableStorageWithVersioning} instance and returns it,
     * ready for use in configuring an Azure Blob Container.</pre>
     *
     * @return The configured {@link AzureBlobContainerImmutableStorageWithVersioning} instance.
     */
    public AzureBlobContainerImmutableStorageWithVersioning build() {
      return instance;
    }
  }
}
