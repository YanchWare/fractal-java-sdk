package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureRecoveryServicesBackupPolicyWorkloadType;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureRecoveryServicesSkuName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
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

  public static AzureStorageAccountBackupBuilder builder() {
    return new AzureStorageAccountBackupBuilder();
  }

  public static class AzureStorageAccountBackupBuilder {
    private final AzureStorageAccountBackup backup;
    private final AzureStorageAccountBackupBuilder builder;

    public AzureStorageAccountBackupBuilder() {
      this.backup = new AzureStorageAccountBackup();
      this.builder = this;
    }

    public AzureStorageAccountBackupBuilder withVaultName(String vaultName) {
      backup.setVaultName(vaultName);
      return builder;
    }

    public AzureStorageAccountBackupBuilder withVaultLocation(String vaultLocation) {
      backup.setVaultLocation(vaultLocation);
      return builder;
    }

    public AzureStorageAccountBackupBuilder withVaultResourceGroupName(String vaultResourceGroupName) {
      backup.setVaultResourceGroupName(vaultResourceGroupName);
      return builder;
    }

    public AzureStorageAccountBackupBuilder withSku(AzureRecoveryServicesSkuName sku) {
      backup.setSku(sku);
      return builder;
    }

    public AzureStorageAccountBackupBuilder withEtag(String etag) {
      backup.setEtag(etag);
      return builder;
    }

    public AzureStorageAccountBackupBuilder withPolicyName(String policyName) {
      backup.setPolicyName(policyName);
      return builder;
    }

    public AzureStorageAccountBackupBuilder withPolicyResourceGroupName(String policyResourceGroupName) {
      backup.setPolicyResourceGroupName(policyResourceGroupName);
      return builder;
    }

    public AzureStorageAccountBackupBuilder withPolicyLocation(String policyLocation) {
      backup.setPolicyLocation(policyLocation);
      return builder;
    }

    public AzureStorageAccountBackupBuilder withPolicyType(AzureRecoveryServicesBackupPolicyWorkloadType policyType) {
      backup.setPolicyType(policyType);
      return builder;
    }

    public AzureStorageAccountBackupBuilder withPolicy(String policy) {
      backup.setPolicy(policy);
      return builder;
    }

    public AzureStorageAccountBackup build() {
      return backup;
    }
  }
}
