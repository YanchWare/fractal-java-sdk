package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureResourceAccessRule {

  private String resourceId;
  private String tenantId;

  public static AzureResourceAccessRuleBuilder builder() {
    return new AzureResourceAccessRuleBuilder();
  }

  public static class AzureResourceAccessRuleBuilder {
    private final AzureResourceAccessRule instance;
    private final AzureResourceAccessRuleBuilder builder;

    public AzureResourceAccessRuleBuilder() {
      this.instance = new AzureResourceAccessRule();
      this.builder = this;
    }

    /**
     * Resource Id
     */
    public AzureResourceAccessRuleBuilder withResourceId(String resourceId) {
      instance.setResourceId(resourceId);
      return builder;
    }
    
    /**
     * Tenant Id
     */
    public AzureResourceAccessRuleBuilder withTenantId(String tenantId) {
      instance.setTenantId(tenantId);
      return builder;
    }

    public AzureResourceAccessRule build() {
      return instance;
    }
  }
}
