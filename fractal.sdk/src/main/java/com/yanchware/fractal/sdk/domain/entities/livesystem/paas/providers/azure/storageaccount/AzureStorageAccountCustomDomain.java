package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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

    public AzureStorageAccountCustomDomainBuilder withName(String name) {
      customDomain.setName(name);
      return builder;
    }

    public AzureStorageAccountCustomDomainBuilder withUseSubDomainName(Boolean useSubDomainName) {
      customDomain.setUseSubDomainName(useSubDomainName);
      return builder;
    }

    public AzureStorageAccountCustomDomain build() {
      return customDomain;
    }
  }
}
