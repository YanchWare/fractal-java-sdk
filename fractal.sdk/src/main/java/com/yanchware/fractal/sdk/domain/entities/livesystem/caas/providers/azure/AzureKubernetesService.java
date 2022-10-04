package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.KubernetesCluster;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isPresentAndValidIpRange;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class AzureKubernetesService extends KubernetesCluster {
  private final static String EMPTY_NODE_POOL = "[AzureKubernetesService Validation] Node pool list is null or empty and at least one node pool is required";
  private final static String REGION_IS_NULL = "[AzureKubernetesService Validation] Region is not specified and it is required";
  private final static String VNET_ADDRESS_SPACE_RANGE_NOT_VALID = "[KubernetesCluster Validation] VNet Address Space IP Range does not contain a valid ip with mask";
  private final static String VNET_SUBNET_ADDRESS_IP_RANGE_NOT_VALID = "[KubernetesCluster Validation] VNet Subnet Address IP Range does not contain a valid ip with mask";
  private String vnetAddressSpaceIpRange;
  private String vnetSubnetAddressIpRange;
  private AzureRegion region;
  private Collection<AzureNodePool> nodePools;
  private Collection<String> externalLoadBalancerOutboundIps;
  private String externalWorkspaceResourceId;
  private Collection<AzureAddonProfile> addonProfiles;

  protected AzureKubernetesService() {
    nodePools = new ArrayList<>();
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static AzureKubernetesServiceBuilder builder() {
    return new AzureKubernetesServiceBuilder();
  }

  public static class AzureKubernetesServiceBuilder extends Builder<AzureKubernetesService, AzureKubernetesServiceBuilder> {

    @Override
    protected AzureKubernetesService createComponent() {
      return new AzureKubernetesService();
    }

    @Override
    protected AzureKubernetesServiceBuilder getBuilder() {
      return this;
    }

    public AzureKubernetesServiceBuilder withVnetAddressSpaceIpRange(String vnetAddressSpaceIpRange) {
      component.setVnetAddressSpaceIpRange(vnetAddressSpaceIpRange);
      return builder;
    }

    public AzureKubernetesServiceBuilder withVnetSubnetAddressIpRange(String vnetSubnetAddressIpRange) {
      component.setVnetSubnetAddressIpRange(vnetSubnetAddressIpRange);
      return builder;
    }

    public AzureKubernetesServiceBuilder withRegion(AzureRegion region) {
      component.setRegion(region);
      return builder;
    }

    public AzureKubernetesServiceBuilder withNodePool(AzureNodePool nodePool) {
      return withNodePools(List.of(nodePool));
    }

    public AzureKubernetesServiceBuilder withNodePools(Collection<? extends AzureNodePool> nodePools) {
      if (isBlank(nodePools)) {
        return builder;
      }

      if (component.getNodePools() == null) {
        component.setNodePools(new ArrayList<>());
      }

      component.getNodePools().addAll(nodePools);
      return builder;
    }

    public AzureKubernetesServiceBuilder withExternalLoadBalancerOutboundIp(String externalLoadBalancerOutboundIp) {
      return withExternalLoadBalancerOutboundIps(List.of(externalLoadBalancerOutboundIp));
    }

    public AzureKubernetesServiceBuilder withExternalLoadBalancerOutboundIps(List<String> externalLoadBalancerOutboundIps) {
      if (isBlank(externalLoadBalancerOutboundIps)) {
        return builder;
      }

      if (component.getExternalLoadBalancerOutboundIps() == null) {
        component.setExternalLoadBalancerOutboundIps(new ArrayList<>());
      }

      component.getExternalLoadBalancerOutboundIps().addAll(externalLoadBalancerOutboundIps);
      return builder;
    }

    public AzureKubernetesServiceBuilder withExternalWorkspaceResourceId(String externalWorkspaceResourceId) {
      component.setExternalWorkspaceResourceId(externalWorkspaceResourceId);
      return builder;
    }
    
    public AzureKubernetesServiceBuilder withAddonProfile(AzureAddonProfile addonProfile) {
      return withAddonProfiles(List.of(addonProfile));
    }

    public AzureKubernetesServiceBuilder withAddonProfiles(Collection<? extends AzureAddonProfile> addonProfiles) {
      if (isBlank(addonProfiles)) {
        return builder;
      }

      if (component.getAddonProfiles() == null) {
        component.setAddonProfiles(new ArrayList<>());
      }

      component.getAddonProfiles().addAll(addonProfiles);
      return builder;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (region == null) {
      errors.add(REGION_IS_NULL);
    }
    
    if (nodePools.isEmpty()) {
      errors.add(EMPTY_NODE_POOL);
    }

    isPresentAndValidIpRange(vnetAddressSpaceIpRange, errors, VNET_ADDRESS_SPACE_RANGE_NOT_VALID);
    isPresentAndValidIpRange(vnetSubnetAddressIpRange, errors, VNET_SUBNET_ADDRESS_IP_RANGE_NOT_VALID);

    nodePools.stream()
        .map(AzureNodePool::validate)
        .forEach(errors::addAll);

    return errors;
  }

}
