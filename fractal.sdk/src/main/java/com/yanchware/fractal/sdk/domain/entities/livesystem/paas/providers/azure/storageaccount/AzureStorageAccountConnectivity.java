package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.*;
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
  boolean publishInternetEndpoints;
  boolean publishMicrosoftEndpoints;
  AzureStoragePublicNetworkAccess publicNetworkAccess;
  AzureStorageDefaultAction networkRuleSetDefaultAction;
  AzureStorageBypass networkRuleSetBypass;
  List<AzureVirtualNetworkRule> networkRuleSetVirtualNetworkRules;
  List<AzureResourceAccessRule> networkRuleSetResourceAccessRules;
  List<AzureStorageIpRule> networkRuleSetIpRules;
  AzureTlsVersion minimumTlsVersion;
  int keyPolicyExpirationInDays;

  @JsonProperty(value = "isSftpEnabled")
  boolean isSftpEnabled;

  @JsonProperty(value = "isLocalUserEnabled")
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
