package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountEncryption {
  private AzureStorageAccountEncryptionIdentity identity;
  private AzureStorageAccountKeySource keySource;
  private AzureStorageAccountKeyVaultProperties keyVaultProperties;
  private Boolean requireInfrastructureEncryption;
  private AzureStorageAccountEncryptionServices services;

  public static AzureStorageAccountEncryptionBuilder builder() {

    return new AzureStorageAccountEncryptionBuilder();
  }


  public static class AzureStorageAccountEncryptionBuilder {
    private final AzureStorageAccountEncryption encryption;
    private final AzureStorageAccountEncryptionBuilder builder;

    public AzureStorageAccountEncryptionBuilder() {
      this.encryption = new AzureStorageAccountEncryption();
      this.builder = this;
    }

    /**
     * The identity to be used with service-side encryption at rest.
     */
    public AzureStorageAccountEncryptionBuilder withIdentity(AzureStorageAccountEncryptionIdentity identity) {
      encryption.setIdentity(identity);
      return builder;
    }

    /**
     * The encryption keySource (provider). 
     * Possible values (case-insensitive): Microsoft.Storage, Microsoft.Keyvault
     */
    public AzureStorageAccountEncryptionBuilder withKeySource(AzureStorageAccountKeySource keySource) {
      encryption.setKeySource(keySource);
      return this;
    }

    /**
     * Properties provided by key vault.
     */
    public AzureStorageAccountEncryptionBuilder withKeyVaultProperties(AzureStorageAccountKeyVaultProperties keyVaultProperties) {
      encryption.setKeyVaultProperties(keyVaultProperties);
      return builder;
    }
    
    /**
     * A boolean indicating whether the service applies a secondary layer of encryption with platform managed keys for data at rest.
     */
    public AzureStorageAccountEncryptionBuilder withRequireInfrastructureEncryption(Boolean requireInfrastructureEncryption) {
      encryption.setRequireInfrastructureEncryption(requireInfrastructureEncryption);
      return builder;
    }
    
    /**
     * List of services which support encryption.
     */
    public AzureStorageAccountEncryptionBuilder withServices(AzureStorageAccountEncryptionServices  services) {
      encryption.setServices(services);
      return builder;
    }


    public AzureStorageAccountEncryption build() {
      return encryption;
    }
  }
}
