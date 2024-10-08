package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountKeyVaultProperties {

  private String keyName;
  private String keyVaultUri;
  private String keyVersion;

  public static AzureStorageAccountKeyVaultPropertiesBuilder builder() {
    return new AzureStorageAccountKeyVaultPropertiesBuilder();
  }

  public static class AzureStorageAccountKeyVaultPropertiesBuilder {
    private final AzureStorageAccountKeyVaultProperties instance;
    private final AzureStorageAccountKeyVaultPropertiesBuilder builder;

    public AzureStorageAccountKeyVaultPropertiesBuilder() {
      this.instance = new AzureStorageAccountKeyVaultProperties();
      this.builder = this;
    }
    
    /**
     * <pre>
     * The name of KeyVault key.
     * </pre>
     */
    public AzureStorageAccountKeyVaultPropertiesBuilder withKeyName(String keyName) {
      instance.setKeyName(keyName);
      return builder;
    }
    
    /**
     * <pre>
     * The Uri of KeyVault.
     * </pre>
     */
    public AzureStorageAccountKeyVaultPropertiesBuilder withKeyVaultUri(String keyVaultUri) {
      instance.setKeyVaultUri(keyVaultUri);
      return builder;
    }
    
    /**
     * <pre>
     * The version of KeyVault key.
     * </pre>
     */
    public AzureStorageAccountKeyVaultPropertiesBuilder withKeyVersion(String keyVersion) {
      instance.setKeyVersion(keyVersion);
      return builder;
    }

    public AzureStorageAccountKeyVaultProperties build() {
      return instance;
    }
  }
}
