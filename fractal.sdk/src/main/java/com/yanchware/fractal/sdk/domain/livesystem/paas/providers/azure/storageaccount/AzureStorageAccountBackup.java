package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureRecoveryServicesBackupPolicyWorkloadType;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureRecoveryServicesSkuName;
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

    /**
     * <pre>
     * The name of the Recovery Services vault used to manage backups and restorations for Azure services.
     * </pre>
     */
    public AzureStorageAccountBackupBuilder withVaultName(String vaultName) {
      backup.setVaultName(vaultName);
      return builder;
    }

    /**
     * <pre>
     * Specifies the geographic location of the Recovery Services vault, ensuring data residency and compliance
     * requirements are met.
     * </pre>
     */
    public AzureStorageAccountBackupBuilder withVaultLocation(String vaultLocation) {
      backup.setVaultLocation(vaultLocation);
      return builder;
    }

    /**
     * <pre>
     * The name of the Azure Resource Group that contains the Recovery Services vault.
     * </pre>
     */
    public AzureStorageAccountBackupBuilder withVaultResourceGroupName(String vaultResourceGroupName) {
      backup.setVaultResourceGroupName(vaultResourceGroupName);
      return builder;
    }

    /**
     * <pre>
     * Specifies the SKU level for the Recovery Services vault, which determines the pricing and features available
     * for backup and recovery services.
     * </pre>
     */
    public AzureStorageAccountBackupBuilder withSku(AzureRecoveryServicesSkuName sku) {
      backup.setSku(sku);
      return builder;
    }

    /**
     * <pre>
     * A unique identifier for a particular configuration version of the Recovery Services vault, used for optimistic
     * concurrency during updates.
     * </pre>
     */
    public AzureStorageAccountBackupBuilder withEtag(String etag) {
      backup.setEtag(etag);
      return builder;
    }

    /**
     * <pre>
     * The name of the backup policy applied to the storage account. Backup policies define the schedule and retention
     * rules for backups.
     * </pre>
     */
    public AzureStorageAccountBackupBuilder withPolicyName(String policyName) {
      backup.setPolicyName(policyName);
      return builder;
    }

    /**
     * <pre>
     * The name of the Resource Group containing the backup policy.
     * </pre>
     */
    public AzureStorageAccountBackupBuilder withPolicyResourceGroupName(String policyResourceGroupName) {
      backup.setPolicyResourceGroupName(policyResourceGroupName);
      return builder;
    }

    /**
     * <pre>
     * The geographic location where the backup policy is stored, aligning with data residency and compliance needs.
     * </pre>
     */
    public AzureStorageAccountBackupBuilder withPolicyLocation(String policyLocation) {
      backup.setPolicyLocation(policyLocation);
      return builder;
    }

    /**
     * <pre>
     * Determines the type of Azure workload the backup policy is designed for, allowing for targeted backup strategies
     * tailored to specific service types.
     * </pre>
     */
    public AzureStorageAccountBackupBuilder withPolicyType(AzureRecoveryServicesBackupPolicyWorkloadType policyType) {
      backup.setPolicyType(policyType);
      return builder;
    }

    /**
     * <pre>
     * A JSON string representing the detailed configuration of the backup policy, including backup frequency,
     * retention duration, and other parameters.
     * </pre>
     */
    public AzureStorageAccountBackupBuilder withPolicy(String policy) {
      backup.setPolicy(policy);
      return builder;
    }

    public AzureStorageAccountBackup build() {
      return backup;
    }
  }
}
