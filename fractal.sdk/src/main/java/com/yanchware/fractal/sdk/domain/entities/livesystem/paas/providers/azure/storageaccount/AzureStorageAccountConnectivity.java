package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureAction;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureTlsVersion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureStorageAccountConnectivity {
  String sasPolicyExpirationPeriod;
  String sasPolicyExpirationAction;
  String routingChoise;
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
}
