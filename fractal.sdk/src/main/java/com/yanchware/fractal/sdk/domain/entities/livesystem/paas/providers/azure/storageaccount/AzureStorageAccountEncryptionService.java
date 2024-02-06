package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureStorageKeyType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountEncryptionService {

  private Boolean enabled;
  private AzureStorageKeyType keyType;

  public static AzureStorageAccountEncryptionServiceBuilder builder() {
    return new AzureStorageAccountEncryptionServiceBuilder();
  }

  public static class AzureStorageAccountEncryptionServiceBuilder {
    private final AzureStorageAccountEncryptionService instance;
    private final AzureStorageAccountEncryptionServiceBuilder builder;

    public AzureStorageAccountEncryptionServiceBuilder() {
      this.instance = new AzureStorageAccountEncryptionService();
      this.builder = this;
    }

    /**
     * <pre>
     * A boolean indicating whether the service encrypts the data as it is stored. 
     * Encryption at rest is enabled by default today and cannot be disabled.
     * </pre>
     */
    public AzureStorageAccountEncryptionServiceBuilder withEnabled(Boolean enabled) {
      instance.setEnabled(enabled);
      return builder;
    }
    
    /**
     * <pre>
     * Encryption key type to be used for the encryption service. 
     * 'Account' key type implies that an account-scoped encryption key will be used. 
     * 'Service' key type implies that a default service key is used.
     * </pre>
     */
    public AzureStorageAccountEncryptionServiceBuilder withKeyType(AzureStorageKeyType keyType) {
      instance.setKeyType(keyType);
      return builder;
    }

    public AzureStorageAccountEncryptionService build() {
      return instance;
    }
  }
}
