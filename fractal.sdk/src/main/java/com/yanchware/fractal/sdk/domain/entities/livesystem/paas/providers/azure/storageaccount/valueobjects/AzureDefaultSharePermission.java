package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureDefaultSharePermission {
  NONE ("None"),
  STORAGE_FILE_DATA_SMB_SHARE_READER ("StorageFileDataSmbShareReader"),
  STORAGE_FILE_DATA_SMB_SHARE_CONTRIBUTOR ("StorageFileDataSmbShareContributor"),
  STORAGE_FILE_DATA_SMB_SHARE_ELEVATED_CONTRIBUTOR ("StorageFileDataSmbShareElevatedContributor");
  private final String id;

  AzureDefaultSharePermission(final String id) {
    this.id = id;
  }

  @JsonValue
  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return id;
  }
}
