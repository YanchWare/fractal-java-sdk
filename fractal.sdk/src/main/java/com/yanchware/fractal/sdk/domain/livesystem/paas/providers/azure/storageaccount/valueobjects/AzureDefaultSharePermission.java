package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * Default share permission for users using Kerberos authentication if RBAC role is not assigned.
 */
public final class AzureDefaultSharePermission extends ExtendableEnum<AzureDefaultSharePermission> {
  public static final AzureDefaultSharePermission NONE = fromString("None");
  public static final AzureDefaultSharePermission STORAGE_FILE_DATA_SMB_SHARE_READER =
    fromString("StorageFileDataSmbShareReader");
  public static final AzureDefaultSharePermission STORAGE_FILE_DATA_SMB_SHARE_CONTRIBUTOR =
    fromString("StorageFileDataSmbShareContributor");
  public static final AzureDefaultSharePermission STORAGE_FILE_DATA_SMB_SHARE_ELEVATED_CONTRIBUTOR =
    fromString("StorageFileDataSmbShareElevatedContributor");

  @JsonCreator
  public static AzureDefaultSharePermission fromString(String name) {
    return fromString(name, AzureDefaultSharePermission.class);
  }

  public static Collection<AzureDefaultSharePermission> values() {
    return values(AzureDefaultSharePermission.class);
  }
}
