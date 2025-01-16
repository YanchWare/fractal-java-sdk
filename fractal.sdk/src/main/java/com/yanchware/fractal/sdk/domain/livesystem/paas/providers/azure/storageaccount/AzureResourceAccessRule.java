package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Resource Access Rule.
 * </pre>
 */
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
     * <pre>
     * Resource Id
     * </pre>
     */
    public AzureResourceAccessRuleBuilder withResourceId(String resourceId) {
      instance.setResourceId(resourceId);
      return builder;
    }

    /**
     * <pre>
     * Tenant Id
     * </pre>
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
