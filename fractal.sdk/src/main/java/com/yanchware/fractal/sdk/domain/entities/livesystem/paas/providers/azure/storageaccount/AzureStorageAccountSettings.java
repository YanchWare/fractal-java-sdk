package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountSettings {
  AzureLargeFileSharesState largeFileSharesState;
  Boolean isHnsEnabled;
  Boolean accountImmutabilityEnabled;
  AzureAccountImmutabilityPolicyState accountImmutabilityPolicyState;
  Integer accountImmutabilityPeriodSinceCreationInDays;
  Boolean accountImmutabilityAllowProtectedAppendWrites;
  Boolean fileEncryptionEnabled;
  AzureStorageKeySource fileEncryptionType;
  Boolean blobEncryptionEnabled;
  AzureStorageKeyType blobEncryptionType;
  Boolean queueEncryptionEnabled;
  AzureStorageKeyType queueEncryptionType;
  Boolean tableEncryptionEnabled;
  AzureStorageKeyType tableEncryptionType;
  AzureStorageKeySource encryptionKeySource;
  String encryptionUserAssignedIdentity;
  String encryptionFederatedIdentityClientId;
  Boolean requireInfrastructureEncryption;
  String encryptionKeyVaultUri;
  String encryptionKeyName;
  String encryptionKeyVersion;
  AzureStorageAllowedCopyScope allowedCopyScope;
  Boolean allowCrossTenantReplication;
  Boolean allowSharedKeyAccess;

  public static AzureStorageAccountSettingsBuilder builder() {
    return new AzureStorageAccountSettingsBuilder();
  }

  public static class AzureStorageAccountSettingsBuilder {
    private final AzureStorageAccountSettings settings;
    private final AzureStorageAccountSettingsBuilder builder;

    public AzureStorageAccountSettingsBuilder() {
      this.settings = new AzureStorageAccountSettings();
      this.builder = this;
    }

    public AzureStorageAccountSettingsBuilder withLargeFileSharesState(AzureLargeFileSharesState largeFileSharesState) {
      settings.setLargeFileSharesState(largeFileSharesState);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withIsHnsEnabled(Boolean isHnsEnabled) {
      settings.setIsHnsEnabled(isHnsEnabled);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withAccountImmutabilityEnabled(Boolean accountImmutabilityEnabled) {
      settings.setAccountImmutabilityEnabled(accountImmutabilityEnabled);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withAccountImmutabilityPolicyState(AzureAccountImmutabilityPolicyState accountImmutabilityPolicyState) {
      settings.setAccountImmutabilityPolicyState(accountImmutabilityPolicyState);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withAccountImmutabilityPeriodSinceCreationInDays(Integer accountImmutabilityPeriodSinceCreationInDays) {
      settings.setAccountImmutabilityPeriodSinceCreationInDays(accountImmutabilityPeriodSinceCreationInDays);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withAccountImmutabilityAllowProtectedAppendWrites(Boolean accountImmutabilityAllowProtectedAppendWrites) {
      settings.setAccountImmutabilityAllowProtectedAppendWrites(accountImmutabilityAllowProtectedAppendWrites);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withFileEncryptionEnabled(Boolean fileEncryptionEnabled) {
      settings.setFileEncryptionEnabled(fileEncryptionEnabled);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withFileEncryptionType(AzureStorageKeySource fileEncryptionType) {
      settings.setFileEncryptionType(fileEncryptionType);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withBlobEncryptionEnabled(Boolean blobEncryptionEnabled) {
      settings.setBlobEncryptionEnabled(blobEncryptionEnabled);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withBlobEncryptionType(AzureStorageKeyType blobEncryptionType) {
      settings.setBlobEncryptionType(blobEncryptionType);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withQueueEncryptionEnabled(Boolean queueEncryptionEnabled) {
      settings.setQueueEncryptionEnabled(queueEncryptionEnabled);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withQueueEncryptionType(AzureStorageKeyType queueEncryptionType) {
      settings.setQueueEncryptionType(queueEncryptionType);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withTableEncryptionEnabled(Boolean tableEncryptionEnabled) {
      settings.setTableEncryptionEnabled(tableEncryptionEnabled);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withTableEncryptionType(AzureStorageKeyType tableEncryptionType) {
      settings.setTableEncryptionType(tableEncryptionType);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withEncryptionKeySource(AzureStorageKeySource encryptionKeySource) {
      settings.setEncryptionKeySource(encryptionKeySource);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withEncryptionUserAssignedIdentity(String encryptionUserAssignedIdentity) {
      settings.setEncryptionUserAssignedIdentity(encryptionUserAssignedIdentity);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withEncryptionFederatedIdentityClientId(String encryptionFederatedIdentityClientId) {
      settings.setEncryptionFederatedIdentityClientId(encryptionFederatedIdentityClientId);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withRequireInfrastructureEncryption(Boolean requireInfrastructureEncryption) {
      settings.setRequireInfrastructureEncryption(requireInfrastructureEncryption);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withEncryptionKeyVaultUri(String encryptionKeyVaultUri) {
      settings.setEncryptionKeyVaultUri(encryptionKeyVaultUri);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withEncryptionKeyName(String encryptionKeyName) {
      settings.setEncryptionKeyName(encryptionKeyName);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withEncryptionKeyVersion(String encryptionKeyVersion) {
      settings.setEncryptionKeyVersion(encryptionKeyVersion);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withAllowedCopyScope(AzureStorageAllowedCopyScope allowedCopyScope) {
      settings.setAllowedCopyScope(allowedCopyScope);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withAllowCrossTenantReplication(Boolean allowCrossTenantReplication) {
      settings.setAllowCrossTenantReplication(allowCrossTenantReplication);
      return builder;
    }

    public AzureStorageAccountSettingsBuilder withAllowSharedKeyAccess(Boolean allowSharedKeyAccess) {
      settings.setAllowSharedKeyAccess(allowSharedKeyAccess);
      return builder;
    }

    public AzureStorageAccountSettings build() {
      return settings;
    }
  }
}
