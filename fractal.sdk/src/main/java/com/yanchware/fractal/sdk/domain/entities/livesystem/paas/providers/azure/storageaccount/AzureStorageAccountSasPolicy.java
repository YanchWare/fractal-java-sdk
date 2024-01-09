package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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
     * The SAS expiration action. 
     * Can only be Log.
     */
    public AzureStorageAccountSasPolicyBuilder withExpirationAction(AzureExpirationAction expirationAction) {
      instance.setExpirationAction(expirationAction);
      return builder;
    }
    
    /**
     * The SAS expiration period, DD.HH:MM:SS
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
