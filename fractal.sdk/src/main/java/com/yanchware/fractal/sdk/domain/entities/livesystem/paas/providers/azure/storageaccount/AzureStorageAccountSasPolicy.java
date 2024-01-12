package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * SasPolicy assigned to the storage account.
 * </pre>
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountSasPolicy {

  private AzureExpirationAction expirationAction;
  private String sasExpirationPeriod;

  public static AzureStorageAccountSasPolicyBuilder builder() {
    return new AzureStorageAccountSasPolicyBuilder();
  }

  public static class AzureStorageAccountSasPolicyBuilder {
    private final AzureStorageAccountSasPolicy instance;
    private final AzureStorageAccountSasPolicyBuilder builder;

    public AzureStorageAccountSasPolicyBuilder() {
      this.instance = new AzureStorageAccountSasPolicy();
      this.builder = this;
    }

    /**
     * <pre>
     * The SAS expiration action. 
     * Can only be Log.
     * </pre>
     */
    public AzureStorageAccountSasPolicyBuilder withExpirationAction(AzureExpirationAction expirationAction) {
      instance.setExpirationAction(expirationAction);
      return builder;
    }
    
    /**
     * <pre>
     * The SAS expiration period, DD.HH:MM:SS
     * </pre>
     */
    public AzureStorageAccountSasPolicyBuilder withSasExpirationPeriod(String sasExpirationPeriod) {
      instance.setSasExpirationPeriod(sasExpirationPeriod);
      return builder;
    }

    public AzureStorageAccountSasPolicy build() {
      return instance;
    }
  }
}
