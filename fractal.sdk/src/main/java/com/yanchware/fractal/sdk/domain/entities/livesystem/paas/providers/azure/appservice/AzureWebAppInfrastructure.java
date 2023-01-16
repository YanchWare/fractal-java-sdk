package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureWebAppInfrastructure {
  boolean acrUseManagedIdentityCreds;
  String acrUserManagedIdentityId;
  boolean detailedErrorLoggingEnabled;
  String documentRoot; // Todo : FTPS enum
  int functionAppScaleLimit;
  boolean functionsRuntimeScaleMonitoringEnabled;
  String healthCheckPath;
  String keyVaultReferenceIdentity;
  long maxDiskSizeInMb;
  long maxMemoryInMb;
  double maxPercentageCpu;
  boolean localMySqlEnabled;
  int logsDirectorySizeLimit;
  int managedServiceIdentityId;
  int minimumElasticInstanceCount;
  int preWarmedInstanceCount;
  String publicNetworkAccess;
  String publishingUsername;
  String vnetName;
  boolean vnetRouteAllEnabled;
  int vnetPrivatePortsCount;
  int xServiceIdentityId;
  int containerSize;
  String customDomainVerificationId; 
  int dailyMemoryTimeQuota;
  boolean enabled;
  boolean hostnamesDisabled;
  boolean useHyperV;
  boolean isXenon;
  String redundancyMode; // TODO: use RedundancyMode enum
  boolean isReserved;
  String serverFarmId;
  boolean isStorageAccountRequired;
  String virtualNetworkSubnetId;
  boolean vnetContentSharedEnabled;
  boolean vnetImagePullEnabled;
}
