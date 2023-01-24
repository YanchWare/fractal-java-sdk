package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

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
  boolean isSftpEnabled;
  boolean isLocalUserEnabled;
  boolean enableNfsV3;
  boolean enableHttpsTrafficOnly;
  boolean defaultToOAuthAuthentication;
  boolean allowBlobPublicAccess;
  AzureActiveDirectoryAccountType azureIdentityBasedAuthAzureDirectoryAccountType;
}
