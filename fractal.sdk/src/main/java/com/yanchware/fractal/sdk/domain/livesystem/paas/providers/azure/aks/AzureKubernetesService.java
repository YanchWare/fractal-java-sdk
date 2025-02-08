package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks;

import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.paas.KubernetesCluster;
import com.yanchware.fractal.sdk.domain.livesystem.paas.RoleAssignment;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidAlphanumericsUnderscoresHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isPresentAndValidIpRange;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class AzureKubernetesService extends KubernetesCluster implements AzureResourceEntity, LiveSystemComponent {
  private final static String EMPTY_NODE_POOL = "[AzureKubernetesService Validation] Node pool list is null or empty and at least one node pool is required";
  private final static String REGION_IS_NULL = "[AzureKubernetesService Validation] Region is not specified and it is required";
  private final static String VNET_ADDRESS_SPACE_RANGE_NOT_VALID = "[KubernetesCluster Validation] VNet Address Space IP Range does not contain a valid ip with mask";
  private final static String VNET_SUBNET_ADDRESS_IP_RANGE_NOT_VALID = "[KubernetesCluster Validation] VNet Subnet Address IP Range does not contain a valid ip with mask";
  private final static String NAME_NOT_VALID = "[KubernetesCluster Validation] The name can contain only letters, numbers, underscores, and hyphens. The name must start and end with a letter or number and must be between 1 and 63 characters long";
  private String vnetAddressSpaceIpRange;
  private String vnetSubnetAddressIpRange;
  @Setter
  private AzureRegion azureRegion;
  @Setter
  private AzureResourceGroup azureResourceGroup;

  private Collection<AzureNodePool> nodePools;
  private Collection<AzureOutboundIp> outboundIps;
  private String externalWorkspaceResourceId;
  private Collection<AzureKubernetesAddonProfile> addonProfiles;
  private Collection<RoleAssignment> roles;
  private AzureActiveDirectoryProfile azureActiveDirectoryProfile;
  private String kubernetesVersion;
  private ManagedClusterSkuTier managedClusterSkuTier;
  private boolean privateClusterDisabled;

  @Setter
  private Map<String, String> tags;

  @Setter
  private String name;

  protected AzureKubernetesService() {
    nodePools = new ArrayList<>();
    outboundIps = new ArrayList<>();
    managedClusterSkuTier = ManagedClusterSkuTier.FREE;
    privateClusterDisabled = false;
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
      component.setAzureRegion(region);
      return builder;
    }

    public AzureKubernetesServiceBuilder withResourceGroup(AzureResourceGroup azureResourceGroup) {
      component.setAzureResourceGroup(azureResourceGroup);
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

    public AzureKubernetesServiceBuilder withOutboundIps(List<AzureOutboundIp> outboundIps) {
      if (isBlank(outboundIps)) {
        return builder;
      }

      if (component.getOutboundIps() == null) {
        component.setOutboundIps(new ArrayList<>());
      }

      component.getOutboundIps().addAll(outboundIps);
      return builder;
    }

    public AzureKubernetesServiceBuilder withOutboundIp(AzureOutboundIp outboundIp) {
      return withOutboundIps(List.of(outboundIp));
    }
    
    public AzureKubernetesServiceBuilder withExternalWorkspaceResourceId(String externalWorkspaceResourceId) {
      component.setExternalWorkspaceResourceId(externalWorkspaceResourceId);
      return builder;
    }
    
    public AzureKubernetesServiceBuilder withAddonProfile(AzureKubernetesAddonProfile addonProfile) {
      return withAddonProfiles(List.of(addonProfile));
    }

    public AzureKubernetesServiceBuilder withAddonProfiles(Collection<? extends AzureKubernetesAddonProfile> addonProfiles) {
      if (isBlank(addonProfiles)) {
        return builder;
      }

      if (component.getAddonProfiles() == null) {
        component.setAddonProfiles(new ArrayList<>());
      }

      component.getAddonProfiles().addAll(addonProfiles);
      return builder;
    }

    public AzureKubernetesServiceBuilder withRole(RoleAssignment role) {
      return withRoles(List.of(role));
    }

    public AzureKubernetesServiceBuilder withRoles(List<RoleAssignment> roles) {
      if (isBlank(roles)) {
        return builder;
      }
      if (component.getRoles() == null) {
        component.setRoles(new ArrayList<>());
      }
      component.getRoles().addAll(roles);
      return builder;
    }

    public AzureKubernetesServiceBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    public AzureKubernetesServiceBuilder withTags(Map<String, String> tags) {
      component.setTags(tags);
      return builder;
    }

    public AzureKubernetesServiceBuilder withTag(String key, String value) {
      if (component.getTags() == null) {
        withTags(new HashMap<>());
      }
      
      component.getTags().put(key, value);
      return builder;
    }
    
    public AzureKubernetesServiceBuilder withActiveDirectoryProfile(AzureActiveDirectoryProfile aadProfile) {
      component.setAzureActiveDirectoryProfile(aadProfile);
      return builder;
    }

    public AzureKubernetesServiceBuilder withKubernetesVersion(String kubernetesVersion) {
      component.setKubernetesVersion(kubernetesVersion);
      return builder;
    }

    public AzureKubernetesServiceBuilder withManagedClusterSkuTier(ManagedClusterSkuTier managedClusterSkuTier) {
      component.setManagedClusterSkuTier(managedClusterSkuTier);
      return builder;
    }

    public AzureKubernetesServiceBuilder disablePrivateCluster() {
      component.setPrivateClusterDisabled(true);
      return builder;
    }
  }

  @Override
  public Collection<String> validate() {

    Collection<String> errors = super.validate();
    errors.addAll(AzureResourceEntity.validateAzureResourceEntity(this, "Kubernetes Service"));

    if(StringUtils.isNotBlank(name)) {
      var isAlphaNumerics = isValidAlphanumericsUnderscoresHyphens(name);
      var hasValidLengths = isValidStringLength(name, 1, 63);
      if(!isAlphaNumerics || !hasValidLengths) {
        errors.add(NAME_NOT_VALID);
      }
    }
    
    if (azureRegion == null) {
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
    
    outboundIps.stream()
        .map(AzureOutboundIp::validate)
        .forEach(errors::addAll);

    return errors;
  }

}
