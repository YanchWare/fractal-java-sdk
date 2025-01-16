package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * The custom domain assigned to this storage account.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountCustomDomain {
  private String name;
  private Boolean useSubDomainName;

  public static AzureStorageAccountCustomDomainBuilder builder() {
    return new AzureStorageAccountCustomDomainBuilder();
  }


  public static class AzureStorageAccountCustomDomainBuilder {
    private final AzureStorageAccountCustomDomain customDomain;
    private final AzureStorageAccountCustomDomainBuilder builder;

    public AzureStorageAccountCustomDomainBuilder() {
      this.customDomain = new AzureStorageAccountCustomDomain();
      this.builder = this;
    }

    /**
     * <pre>
     * Sets the custom domain name assigned to the storage account.
     * Name is the CNAME source.
     * </pre>
     */
    public AzureStorageAccountCustomDomainBuilder withName(String name) {
      customDomain.setName(name);
      return builder;
    }

    /**
     * <pre>
     * Indicates whether indirect CName validation is enabled.
     * </pre>
     */
    public AzureStorageAccountCustomDomainBuilder withUseSubDomainName(Boolean useSubDomainName) {
      customDomain.setUseSubDomainName(useSubDomainName);
      return builder;
    }

    public AzureStorageAccountCustomDomain build() {
      return customDomain;
    }
  }
}
