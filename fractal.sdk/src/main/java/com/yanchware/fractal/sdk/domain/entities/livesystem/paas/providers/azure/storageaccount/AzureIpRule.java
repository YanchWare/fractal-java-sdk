package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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
     * The action of IP ACL rule.
     */
    public AzureIpRuleBuilder withAction(AzureNetworkAction action) {
      instance.setAction(action);
      return builder;
    }
    
    /**
     * Specifies the IP or IP range in CIDR format. 
     * Only IPV4 address is allowed.
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
