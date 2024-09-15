package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Virtual Network rule.
 * </pre>
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureVirtualNetworkRule {

  private AzureNetworkAction action;
  private String virtualNetworkResourceId;
  private AzureState state;

  public static AzureVirtualNetworkRuleBuilder builder() {
    return new AzureVirtualNetworkRuleBuilder();
  }

  public static class AzureVirtualNetworkRuleBuilder {
    private final AzureVirtualNetworkRule instance;
    private final AzureVirtualNetworkRuleBuilder builder;

    public AzureVirtualNetworkRuleBuilder() {
      this.instance = new AzureVirtualNetworkRule();
      this.builder = this;
    }

    /**
     * <pre>
     * The action of virtual network rule.
     * </pre>
     */
    public AzureVirtualNetworkRuleBuilder withAction(AzureNetworkAction action) {
      instance.setAction(action);
      return builder;
    }
    
    /**
     * <pre>
     * Resource ID of a subnet.
     * 
     * For example:
     *  /subscriptions/{subscriptionId}/resourceGroups/{groupName}/providers/Microsoft.Network/virtualNetworks/{vnetName}/subnets/{subnetName}.
     * </pre>
     */
    public AzureVirtualNetworkRuleBuilder withVirtualNetworkResourceId(String virtualNetworkResourceId) {
      instance.setVirtualNetworkResourceId(virtualNetworkResourceId);
      return builder;
    }
    
    /**
     * <pre>
     * Gets the state of virtual network rule.
     * </pre>
     */
    public AzureVirtualNetworkRuleBuilder withState(AzureState state) {
      instance.setState(state);
      return builder;
    }

    public AzureVirtualNetworkRule build() {
      return instance;
    }
  }
}
