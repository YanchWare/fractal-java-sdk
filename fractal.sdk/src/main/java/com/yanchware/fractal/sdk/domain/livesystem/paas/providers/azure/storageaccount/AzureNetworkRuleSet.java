package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureAction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureNetworkRuleSet {

  private AzureBypass bypass;
  private AzureAction defaultAction;
  private List<AzureIpRule> ipRules;
  private List<AzureResourceAccessRule> resourceAccessRules;
  private List<AzureVirtualNetworkRule> virtualNetworkRules;

  public static AzureNetworkRuleSetBuilder builder() {
    return new AzureNetworkRuleSetBuilder();
  }

  public static class AzureNetworkRuleSetBuilder {
    private final AzureNetworkRuleSet instance;
    private final AzureNetworkRuleSetBuilder builder;

    public AzureNetworkRuleSetBuilder() {
      this.instance = new AzureNetworkRuleSet();
      this.builder = this;
    }

    /**
     * <pre>
     * Specifies whether traffic is bypassed for Logging/Metrics/AzureServices. 
     * Possible values are any combination of Logging|Metrics|AzureServices (For example, "Logging, Metrics"), 
     * or None to bypass none of those traffics.
     * </pre>
     */
    public AzureNetworkRuleSetBuilder withBypass(AzureBypass bypass) {
      instance.setBypass(bypass);
      return builder;
    }
    
    /**
     * <pre>
     * Specifies the default action of allow or deny when no other rules match.
     * </pre>
     */
    public AzureNetworkRuleSetBuilder withDefaultAction(AzureAction defaultAction) {
      instance.setDefaultAction(defaultAction);
      return builder;
    }

    /**
     * <pre>
     * Sets the IP ACL rule
     * </pre>>
     */
    public AzureNetworkRuleSetBuilder withIpRule(AzureIpRule ipRule) {
      return withIpRules(List.of(ipRule));
    }

    /**
     * <pre>
     * Sets the IP ACL rules
     * </pre>
     */
    public AzureNetworkRuleSetBuilder withIpRules(List<AzureIpRule> ipRules) {
      if (isBlank(ipRules)) {
        return builder;
      }

      if (instance.getIpRules() == null) {
        instance.setIpRules(new ArrayList<>());
      }

      instance.getIpRules().addAll(ipRules);
      return builder;
    }

    /**
     * <pre>
     * Sets the resource access rule
     * </pre>
     */
    public AzureNetworkRuleSetBuilder withResourceAccessRule(AzureResourceAccessRule resourceAccessRule) {
      return withResourceAccessRules(List.of(resourceAccessRule));
    }

    /**
     * <pre>
     * Sets the resource access rules
     * </pre>
     */
    public AzureNetworkRuleSetBuilder withResourceAccessRules(List<AzureResourceAccessRule> resourceAccessRules) {
      if (isBlank(resourceAccessRules)) {
        return builder;
      }

      if (instance.getResourceAccessRules() == null) {
        instance.setResourceAccessRules(new ArrayList<>());
      }

      instance.getResourceAccessRules().addAll(resourceAccessRules);
      return builder;
    }

    /**
     * <pre>
     * Sets the virtual network rule
     * </pre>
     */
    public AzureNetworkRuleSetBuilder withVirtualNetworkRule(AzureVirtualNetworkRule virtualNetworkRule) {
      return withVirtualNetworkRules(List.of(virtualNetworkRule));
    }

    /**
     * <pre>
     * Sets the virtual network rules
     * </pre>
     */
    public AzureNetworkRuleSetBuilder withVirtualNetworkRules(List<AzureVirtualNetworkRule> virtualNetworkRules) {
      if (isBlank(virtualNetworkRules)) {
        return builder;
      }

      if (instance.getVirtualNetworkRules() == null) {
        instance.setVirtualNetworkRules(new ArrayList<>());
      }

      instance.getVirtualNetworkRules().addAll(virtualNetworkRules);
      return builder;
    }

    public AzureNetworkRuleSet build() {
      return instance;
    }
  }
}
