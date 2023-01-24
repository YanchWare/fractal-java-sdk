package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureStorageKind;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureStorageAccountBackup {
  String vaultName;
  String vaultLocation;
  String vaultResourceGroupName;
  AzureStorageKind.AzureRecoveryServicesSkuName sku;
  String etag;
  String policyName;
  String policyResourceGroupName;
  String policyLocation;
  AzureStorageKind.AzureRecoveryServicesBackupPolicyWorkloadType policyType;
  String policy;
}
