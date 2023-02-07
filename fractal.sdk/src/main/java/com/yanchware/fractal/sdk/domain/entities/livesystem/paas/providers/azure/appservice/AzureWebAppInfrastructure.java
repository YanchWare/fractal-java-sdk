package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.valueobjects.AzureAppServiceRedundancyMode;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.valueobjects.AzureFtpsState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureWebAppInfrastructure {
  Boolean acrUseManagedIdentityCreds;
  String acrUserManagedIdentityId;
  Boolean detailedErrorLoggingEnabled;
  AzureFtpsState ftpsState;
  Integer functionAppScaleLimit;
  Boolean functionsRuntimeScaleMonitoringEnabled;
  String healthCheckPath;
  String keyVaultReferenceIdentity;
  Long maxDiskSizeInMb;
  Long maxMemoryInMb;
  Double maxPercentageCpu;
  Boolean localMySqlEnabled;
  Integer logsDirectorySizeLimit;
  Integer managedServiceIdentityId;
  Integer minimumElasticInstanceCount;
  Integer preWarmedInstanceCount;
  String publicNetworkAccess;
  String publishingUsername;
  String vnetName;
  Boolean vnetRouteAllEnabled;
  Integer vnetPrivatePortsCount;
  Integer xServiceIdentityId;
  Integer containerSize;
  String customDomainVerificationId;
  Integer dailyMemoryTimeQuota;
  Boolean enabled;
  Boolean hostnamesDisabled;
  Boolean useHyperV;
  Boolean isXenon;
  AzureAppServiceRedundancyMode redundancyMode;
  Boolean isReserved;
  Boolean isStorageAccountRequired;
  String virtualNetworkSubnetId;
  Boolean vnetContentSharedEnabled;
  Boolean vnetImagePullEnabled;
}
