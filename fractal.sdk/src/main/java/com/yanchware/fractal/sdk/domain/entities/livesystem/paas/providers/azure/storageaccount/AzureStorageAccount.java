package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSDataStorage;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.AzureWebAppConnectivity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.AzureWebAppHosting;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.AzureWebAppInfrastructure;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureStorageAccount extends PaaSDataStorage implements AzureEntity, LiveSystemComponent {

  private AzureStorageAccountConnectivity connectivityParameters;
  private AzureStorageAccountSettings settingsParameters;
  private AzureStorageAccountInfrastructure infrastructureParameters;
  private AzureStorageAccountBackup backupParameters;
  
  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  protected AzureStorageAccount() {
    // TODO: check if we need anything in the constructor
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    // TODO ...
    return errors;
  }

  @Override
  public AzureResourceGroup getAzureResourceGroup() {
    return null;
  }

  @Override
  public void setAzureResourceGroup(AzureResourceGroup azureResourceGroup) {

  }

  @Override
  public AzureRegion getAzureRegion() {
    return null;
  }

  @Override
  public void setAzureRegion(AzureRegion region) {

  }
// TODO: implement builder
//  public static class AzureStoragAccountBuilder extends PaaSDataStorage.Builder<AzureStorageAccount, AzureStoragAccountBuilder> {
//
//    @Override
//    protected AzureStorageAccount createComponent() {
//      return new AzureStorageAccount();
//    }
//
//    @Override
//    protected AzureStoragAccountBuilder getBuilder() {
//      return this;
//    }
//
////    public AzureKubernetesService.AzureKubernetesServiceBuilder withVnetAddressSpaceIpRange(String vnetAddressSpaceIpRange) {
////      component.setVnetAddressSpaceIpRange(vnetAddressSpaceIpRange);
////      return builder;
////    }
////
////    public AzureKubernetesService.AzureKubernetesServiceBuilder withVnetSubnetAddressIpRange(String vnetSubnetAddressIpRange) {
////      component.setVnetSubnetAddressIpRange(vnetSubnetAddressIpRange);
////      return builder;
////    }
////
////    public AzureKubernetesService.AzureKubernetesServiceBuilder withRegion(AzureRegion region) {
////      component.setAzureRegion(region);
////      return builder;
////    }
////
////    public AzureKubernetesService.AzureKubernetesServiceBuilder withNodePool(AzureNodePool nodePool) {
////      return withNodePools(List.of(nodePool));
////    }
////
////    public AzureKubernetesService.AzureKubernetesServiceBuilder withNodePools(Collection<? extends AzureNodePool> nodePools) {
////      if (isBlank(nodePools)) {
////        return builder;
////      }
////
////      if (component.getNodePools() == null) {
////        component.setNodePools(new ArrayList<>());
////      }
////
////      component.getNodePools().addAll(nodePools);
////      return builder;
////    }
////
////    public AzureKubernetesService.AzureKubernetesServiceBuilder withOutboundIps(List<AzureOutboundIp> outboundIps) {
////      if (isBlank(outboundIps)) {
////        return builder;
////      }
////
////      if (component.getOutboundIps() == null) {
////        component.setOutboundIps(new ArrayList<>());
////      }
////
////      component.getOutboundIps().addAll(outboundIps);
////      return builder;
////    }
////
////    public AzureKubernetesService.AzureKubernetesServiceBuilder withOutboundIp(AzureOutboundIp outboundIp) {
////      return withOutboundIps(List.of(outboundIp));
////    }
////
////    public AzureKubernetesService.AzureKubernetesServiceBuilder withExternalWorkspaceResourceId(String externalWorkspaceResourceId) {
////      component.setExternalWorkspaceResourceId(externalWorkspaceResourceId);
////      return builder;
////    }
////
////    public AzureKubernetesService.AzureKubernetesServiceBuilder withAddonProfile(AzureAddonProfile addonProfile) {
////      return withAddonProfiles(List.of(addonProfile));
////    }
////
////    public AzureKubernetesService.AzureKubernetesServiceBuilder withAddonProfiles(Collection<? extends AzureAddonProfile> addonProfiles) {
////      if (isBlank(addonProfiles)) {
////        return builder;
////      }
////
////      if (component.getAddonProfiles() == null) {
////        component.setAddonProfiles(new ArrayList<>());
////      }
////
////      component.getAddonProfiles().addAll(addonProfiles);
////      return builder;
////    }
////
////    public AzureKubernetesService.AzureKubernetesServiceBuilder withRole(RoleAssignment role) {
////      return withRoles(List.of(role));
////    }
////
////    public AzureKubernetesService.AzureKubernetesServiceBuilder withRoles(List<RoleAssignment> roles) {
////      if (isBlank(roles)) {
////        return builder;
////      }
////      if (component.getRoles() == null) {
////        component.setRoles(new ArrayList<>());
////      }
////      component.getRoles().addAll(roles);
////      return builder;
////    }
////
////    public AzureKubernetesService.AzureKubernetesServiceBuilder withActiveDirectoryProfile(AzureActiveDirectoryProfile aadProfile) {
////      component.setAzureActiveDirectoryProfile(aadProfile);
////      return builder;
////    }
////
////    public AzureKubernetesService.AzureKubernetesServiceBuilder withKubernetesVersion(String kubernetesVersion) {
////      component.setKubernetesVersion(kubernetesVersion);
////      return builder;
//    }
//  }
}
