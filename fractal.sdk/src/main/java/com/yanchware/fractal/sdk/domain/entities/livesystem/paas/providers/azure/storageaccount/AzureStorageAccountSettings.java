package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.annotation.JsonProperty;
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
  @JsonProperty(value = "isHnsEnabled")
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
}
