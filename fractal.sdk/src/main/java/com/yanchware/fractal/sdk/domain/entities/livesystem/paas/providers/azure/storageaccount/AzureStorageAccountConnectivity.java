package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureAction;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureTlsVersion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountConnectivity {
  String sasPolicyExpirationPeriod;
  String sasPolicyExpirationAction;
  String routingChoice;
  Boolean publishInternetEndpoints;
  Boolean publishMicrosoftEndpoints;
  AzureStoragePublicNetworkAccess publicNetworkAccess;
  AzureAction networkRuleSetDefaultAction;
  AzureStorageBypass networkRuleSetBypass;
  List<AzureVirtualNetworkRule> networkRuleSetVirtualNetworkRules;
  List<AzureResourceAccessRule> networkRuleSetResourceAccessRules;
  List<AzureStorageIpRule> networkRuleSetIpRules;
  AzureTlsVersion minimumTlsVersion;
  Integer keyPolicyExpirationInDays;
  Boolean isSftpEnabled;
  Boolean isLocalUserEnabled;
  Boolean enableNfsV3;
  Boolean enableHttpsTrafficOnly;
  Boolean defaultToOAuthAuthentication;
  Boolean allowBlobPublicAccess;
  AzureActiveDirectoryAccountType azureIdentityBasedAuthAzureDirectoryAccountType;
  String azureIdentityBasedAuthAzureDirectoryStorageSid;
  String azureIdentityBasedAuthAzureDirectoryDomainGuid;
  String azureIdentityBasedAuthAzureDirectoryDomainName;
  String azureIdentityBasedAuthAzureDirectoryForestName;
  String azureIdentityBasedAuthAzureDirectoryDomainSid;
  String azureIdentityBasedAuthAzureDirectoryNetBiosDomainName;
  String azureIdentityBasedAuthAzureDirectorySamAccount;
  AzureDefaultSharePermission azureIdentityBasedDefaultSharePermission;
  AzureDirectoryServiceOptions azureIdentityBasedDirectoryServiceOptions;

  public static AzureStorageAccountConnectivityBuilder builder() {
    return new AzureStorageAccountConnectivityBuilder();
  }

  public static class AzureStorageAccountConnectivityBuilder {
    private final AzureStorageAccountConnectivity connectivity;
    private final AzureStorageAccountConnectivityBuilder builder;

    public AzureStorageAccountConnectivityBuilder() {
      this.connectivity = new AzureStorageAccountConnectivity();
      this.builder = this;
    }

    public AzureStorageAccountConnectivityBuilder withSasPolicyExpirationPeriod(String sasPolicyExpirationPeriod) {
      connectivity.setSasPolicyExpirationPeriod(sasPolicyExpirationPeriod);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withSasPolicyExpirationAction(String sasPolicyExpirationAction) {
      connectivity.setSasPolicyExpirationAction(sasPolicyExpirationAction);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withRoutingChoice(String routingChoice) {
      connectivity.setRoutingChoice(routingChoice);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withPublishInternetEndpoints(Boolean publishInternetEndpoints) {
      connectivity.setPublishInternetEndpoints(publishInternetEndpoints);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withPublishMicrosoftEndpoints(Boolean publishMicrosoftEndpoints) {
      connectivity.setPublishMicrosoftEndpoints(publishMicrosoftEndpoints);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withPublicNetworkAccess(AzureStoragePublicNetworkAccess publicNetworkAccess) {
      connectivity.setPublicNetworkAccess(publicNetworkAccess);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withNetworkRuleSetDefaultAction(AzureAction networkRuleSetDefaultAction) {
      connectivity.setNetworkRuleSetDefaultAction(networkRuleSetDefaultAction);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withNetworkRuleSetBypass(AzureStorageBypass networkRuleSetBypass) {
      connectivity.setNetworkRuleSetBypass(networkRuleSetBypass);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withNetworkRuleSetVirtualNetworkRule(AzureVirtualNetworkRule networkRuleSetVirtualNetworkRule) {
      return withNetworkRuleSetVirtualNetworkRules(List.of(networkRuleSetVirtualNetworkRule));
    }

    public AzureStorageAccountConnectivityBuilder withNetworkRuleSetVirtualNetworkRules(List<AzureVirtualNetworkRule> networkRuleSetVirtualNetworkRules) {
      if (isBlank(networkRuleSetVirtualNetworkRules)) {
        return builder;
      }

      if (connectivity.getNetworkRuleSetVirtualNetworkRules() == null) {
        connectivity.setNetworkRuleSetVirtualNetworkRules(new ArrayList<>());
      }

      connectivity.getNetworkRuleSetVirtualNetworkRules().addAll(networkRuleSetVirtualNetworkRules);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withNetworkRuleSetResourceAccessRule(AzureResourceAccessRule networkRuleSetResourceAccessRule) {
      return withNetworkRuleSetResourceAccessRules(List.of(networkRuleSetResourceAccessRule));
    }

    public AzureStorageAccountConnectivityBuilder withNetworkRuleSetResourceAccessRules(List<AzureResourceAccessRule> networkRuleSetResourceAccessRules) {
      if (isBlank(networkRuleSetResourceAccessRules)) {
        return builder;
      }

      if (connectivity.getNetworkRuleSetResourceAccessRules() == null) {
        connectivity.setNetworkRuleSetResourceAccessRules(new ArrayList<>());
      }

      connectivity.getNetworkRuleSetResourceAccessRules().addAll(networkRuleSetResourceAccessRules);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withNetworkRuleSetIpRule(AzureStorageIpRule networkRuleSetIpRule) {
      return withNetworkRuleSetIpRules(List.of(networkRuleSetIpRule));
    }

    public AzureStorageAccountConnectivityBuilder withNetworkRuleSetIpRules(List<AzureStorageIpRule> networkRuleSetIpRules) {
      if (isBlank(networkRuleSetIpRules)) {
        return builder;
      }

      if (connectivity.getNetworkRuleSetIpRules() == null) {
        connectivity.setNetworkRuleSetIpRules(new ArrayList<>());
      }

      connectivity.getNetworkRuleSetIpRules().addAll(networkRuleSetIpRules);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withMinimumTlsVersion(AzureTlsVersion minimumTlsVersion) {
      connectivity.setMinimumTlsVersion(minimumTlsVersion);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withKeyPolicyExpirationInDays(Integer keyPolicyExpirationInDays) {
      connectivity.setKeyPolicyExpirationInDays(keyPolicyExpirationInDays);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withIsSftpEnabled(Boolean isSftpEnabled) {
      connectivity.setIsSftpEnabled(isSftpEnabled);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withIsLocalUserEnabled(Boolean isLocalUserEnabled) {
      connectivity.setIsLocalUserEnabled(isLocalUserEnabled);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withEnableNfsV3(Boolean enableNfsV3) {
      connectivity.setEnableNfsV3(enableNfsV3);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withEnableHttpsTrafficOnly(Boolean enableHttpsTrafficOnly) {
      connectivity.setEnableHttpsTrafficOnly(enableHttpsTrafficOnly);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withDefaultToOAuthAuthentication(Boolean defaultToOAuthAuthentication) {
      connectivity.setDefaultToOAuthAuthentication(defaultToOAuthAuthentication);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withAllowBlobPublicAccess(Boolean allowBlobPublicAccess) {
      connectivity.setAllowBlobPublicAccess(allowBlobPublicAccess);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withAzureIdentityBasedAuthAzureDirectoryAccountType(AzureActiveDirectoryAccountType azureIdentityBasedAuthAzureDirectoryAccountType) {
      connectivity.setAzureIdentityBasedAuthAzureDirectoryAccountType(azureIdentityBasedAuthAzureDirectoryAccountType);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withAzureIdentityBasedAuthAzureDirectoryStorageSid(String azureIdentityBasedAuthAzureDirectoryStorageSid) {
      connectivity.setAzureIdentityBasedAuthAzureDirectoryStorageSid(azureIdentityBasedAuthAzureDirectoryStorageSid);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withAzureIdentityBasedAuthAzureDirectoryDomainGuid(String azureIdentityBasedAuthAzureDirectoryDomainGuid) {
      connectivity.setAzureIdentityBasedAuthAzureDirectoryDomainGuid(azureIdentityBasedAuthAzureDirectoryDomainGuid);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withAzureIdentityBasedAuthAzureDirectoryDomainName(String azureIdentityBasedAuthAzureDirectoryDomainName) {
      connectivity.setAzureIdentityBasedAuthAzureDirectoryDomainName(azureIdentityBasedAuthAzureDirectoryDomainName);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withAzureIdentityBasedAuthAzureDirectoryForestName(String azureIdentityBasedAuthAzureDirectoryForestName) {
      connectivity.setAzureIdentityBasedAuthAzureDirectoryForestName(azureIdentityBasedAuthAzureDirectoryForestName);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withAzureIdentityBasedAuthAzureDirectoryDomainSid(String azureIdentityBasedAuthAzureDirectoryDomainSid) {
      connectivity.setAzureIdentityBasedAuthAzureDirectoryDomainSid(azureIdentityBasedAuthAzureDirectoryDomainSid);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withAzureIdentityBasedAuthAzureDirectoryNetBiosDomainName(String azureIdentityBasedAuthAzureDirectoryNetBiosDomainName) {
      connectivity.setAzureIdentityBasedAuthAzureDirectoryNetBiosDomainName(azureIdentityBasedAuthAzureDirectoryNetBiosDomainName);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withAzureIdentityBasedAuthAzureDirectorySamAccount(String azureIdentityBasedAuthAzureDirectorySamAccount) {
      connectivity.setAzureIdentityBasedAuthAzureDirectorySamAccount(azureIdentityBasedAuthAzureDirectorySamAccount);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withAzureIdentityBasedDefaultSharePermission(AzureDefaultSharePermission azureIdentityBasedDefaultSharePermission) {
      connectivity.setAzureIdentityBasedDefaultSharePermission(azureIdentityBasedDefaultSharePermission);
      return builder;
    }

    public AzureStorageAccountConnectivityBuilder withAzureIdentityBasedDirectoryServiceOptions(AzureDirectoryServiceOptions azureIdentityBasedDirectoryServiceOptions) {
      connectivity.setAzureIdentityBasedDirectoryServiceOptions(azureIdentityBasedDirectoryServiceOptions);
      return builder;
    }

    public AzureStorageAccountConnectivity build() {
      return connectivity;
    }
  }
}
