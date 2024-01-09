package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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
     * The action of virtual network rule.
     */
    public AzureVirtualNetworkRuleBuilder withAction(AzureNetworkAction action) {
      instance.setAction(action);
      return builder;
    }
    
    /**
     * Resource ID of a subnet, 
     * for example: /subscriptions/{subscriptionId}/resourceGroups/{groupName}/providers/Microsoft.Network/virtualNetworks/{vnetName}/subnets/{subnetName}.
     */
    public AzureVirtualNetworkRuleBuilder withVirtualNetworkResourceId(String virtualNetworkResourceId) {
      instance.setVirtualNetworkResourceId(virtualNetworkResourceId);
      return builder;
    }
    
    /**
     * Gets the state of virtual network rule.
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
