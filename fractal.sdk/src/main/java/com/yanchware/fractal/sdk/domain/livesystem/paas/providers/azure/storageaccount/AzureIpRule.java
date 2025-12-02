package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * IP rule with specific IP or IP range in CIDR format.
 * </pre>
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureIpRule {

  private AzureNetworkAction action;
  private String ipAddressOrRange;

  public static AzureIpRuleBuilder builder() {
    return new AzureIpRuleBuilder();
  }

  public static class AzureIpRuleBuilder {
    private final AzureIpRule instance;
    private final AzureIpRuleBuilder builder;

    public AzureIpRuleBuilder() {
      this.instance = new AzureIpRule();
      this.builder = this;
    }

    /**
     * <pre>
     * The action of IP ACL rule.
     * </pre>
     */
    public AzureIpRuleBuilder withAction(AzureNetworkAction action) {
      instance.setAction(action);
      return builder;
    }

    /**
     * <pre>
     * Specifies the IP or IP range in CIDR format.
     * Only IPV4 address is allowed.
     * </pre>
     */
    public AzureIpRuleBuilder withIpAddressOrRange(String ipAddressOrRange) {
      instance.setIpAddressOrRange(ipAddressOrRange);
      return builder;
    }

    public AzureIpRule build() {
      return instance;
    }
  }
}
