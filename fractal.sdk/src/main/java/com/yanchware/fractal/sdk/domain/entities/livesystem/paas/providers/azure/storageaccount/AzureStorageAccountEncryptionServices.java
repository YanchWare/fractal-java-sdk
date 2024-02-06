package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountEncryptionServices {

  private AzureStorageAccountEncryptionService blob;
  private AzureStorageAccountEncryptionService file;
  private AzureStorageAccountEncryptionService queue;
  private AzureStorageAccountEncryptionService table;

  public static AzureStorageAccountEncryptionServicesBuilder builder() {
    return new AzureStorageAccountEncryptionServicesBuilder();
  }

  public static class AzureStorageAccountEncryptionServicesBuilder {
    private final AzureStorageAccountEncryptionServices instance;
    private final AzureStorageAccountEncryptionServicesBuilder builder;

    public AzureStorageAccountEncryptionServicesBuilder() {
      this.instance = new AzureStorageAccountEncryptionServices();
      this.builder = this;
    }

    /**
     * <pre>
     * The encryption function of the blob storage service.
     * </pre>
     */
    public AzureStorageAccountEncryptionServicesBuilder withBlob(AzureStorageAccountEncryptionService blob) {
      instance.setBlob(blob);
      return builder;
    }

    /**
     * <pre>
     * The encryption function of the file storage service.
     * </pre>
     */
    public AzureStorageAccountEncryptionServicesBuilder withFile(AzureStorageAccountEncryptionService file) {
      instance.setFile(file);
      return builder;
    }

    /**
     * <pre>
     * The encryption function of the queue storage service.
     * </pre>
     */
    public AzureStorageAccountEncryptionServicesBuilder withQueue(AzureStorageAccountEncryptionService queue) {
      instance.setQueue(queue);
      return builder;
    }

    /**
     * <pre>
     * The encryption function of the queue storage service.
     * </pre>
     */
    public AzureStorageAccountEncryptionServicesBuilder withTable(AzureStorageAccountEncryptionService table) {
      instance.setTable(table);
      return builder;
    }

    public AzureStorageAccountEncryptionServices build() {
      return instance;
    }
  }
}
