package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

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
     * <pre>
     * The identity to be used with service-side encryption at rest.
     * </pre>
     */
    public AzureStorageAccountEncryptionBuilder withIdentity(AzureStorageAccountEncryptionIdentity identity) {
      encryption.setIdentity(identity);
      return builder;
    }

    /**
     * <pre>
     * The encryption keySource (provider). 
     * Possible values (case-insensitive): Microsoft.Storage, Microsoft.Keyvault
     * </pre>
     */
    public AzureStorageAccountEncryptionBuilder withKeySource(AzureStorageAccountKeySource keySource) {
      encryption.setKeySource(keySource);
      return this;
    }

    /**
     * <pre>
     * Properties provided by key vault.
     * </pre>
     */
    public AzureStorageAccountEncryptionBuilder withKeyVaultProperties(AzureStorageAccountKeyVaultProperties keyVaultProperties) {
      encryption.setKeyVaultProperties(keyVaultProperties);
      return builder;
    }
    
    /**
     * <pre>
     * A boolean indicating whether the service applies a secondary layer of encryption 
     * with platform managed keys for data at rest.
     * </pre>
     */
    public AzureStorageAccountEncryptionBuilder withRequireInfrastructureEncryption(Boolean requireInfrastructureEncryption) {
      encryption.setRequireInfrastructureEncryption(requireInfrastructureEncryption);
      return builder;
    }
    
    /**
     * <pre>
     * List of services which support encryption.
     * </pre>
     */
    public AzureStorageAccountEncryptionBuilder withServices(AzureStorageAccountEncryptionServices services) {
      encryption.setServices(services);
      return builder;
    }


    public AzureStorageAccountEncryption build() {
      return encryption;
    }
  }
}
