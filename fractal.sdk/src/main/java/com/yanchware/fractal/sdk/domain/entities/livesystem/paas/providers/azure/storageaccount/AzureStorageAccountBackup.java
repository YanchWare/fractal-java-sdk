package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRecoveryServicesBackupPolicyWorkloadType;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRecoveryServicesSkuName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureStorageAccountBackup {
  String vaultName;
  String vaultLocation;
  String vaultResourceGroupName;
  AzureRecoveryServicesSkuName sku;
  String etag;
  String policyName;
  String policyResourceGroupName;
  String policyLocation;
  AzureRecoveryServicesBackupPolicyWorkloadType policyType;
  String policy;
}
