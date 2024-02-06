package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountKeyPolicy {

  private Integer keyExpirationPeriodInDays;

  public static AzureStorageAccountKeyPolicyBuilder builder() {
    return new AzureStorageAccountKeyPolicyBuilder();
  }

  public static class AzureStorageAccountKeyPolicyBuilder {
    private final AzureStorageAccountKeyPolicy instance;
    private final AzureStorageAccountKeyPolicyBuilder builder;

    public AzureStorageAccountKeyPolicyBuilder() {
      this.instance = new AzureStorageAccountKeyPolicy();
      this.builder = this;
    }

    /**
     * <pre>
     * The key expiration period in days.
     * </pre>
     */
    public AzureStorageAccountKeyPolicyBuilder withKeyExpirationPeriodInDays(Integer keyExpirationPeriodInDays) {
      instance.setKeyExpirationPeriodInDays(keyExpirationPeriodInDays);
      return builder;
    }

    public AzureStorageAccountKeyPolicy build() {
      return instance;
    }
  }
}
