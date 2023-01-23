package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureStorageAccountSettings {
  AzureLargeFileSharesState largeFileSharesState;
  AzureStorageKind kind;
  boolean isHnsEnabled;
  boolean accountImmutabilityEnabled;
  AzureAccountImmutabilityPolicyState accountImmutabilityPolicyState;
  int accountImmutabilityPeriodSinceCreationInDays;
  boolean accountImmutabilityAllowProtectedAppendWrites;
  boolean fileEncryptionEnabled;
  AzureStorageKeySource fileEncryptionType;
  boolean blobEncryptionEnabled;
  AzureStorageKeyType blobEncryptionType;
  boolean queueEncryptionEnabled;
  AzureStorageKeyType queueEncryptionType;
  boolean tableEncryptionEnabled;
  AzureStorageKeyType tableEncryptionType;
  AzureStorageKeySource encryptionKeySource;
  String encryptionUserAssignedIdentity;
  String encryptionFederatedIdentityClientId; 
  boolean requireInfrastructureEncryption;
  String encryptionKeyVaultUri;
  String encryptionKeyName;
  String encryptionKeyVersion;
  AzureStorageAllowedCopyScope allowedCopyScope;
  boolean allowCrossTenantReplication;
  boolean allowSharedKeyAccess;
}
