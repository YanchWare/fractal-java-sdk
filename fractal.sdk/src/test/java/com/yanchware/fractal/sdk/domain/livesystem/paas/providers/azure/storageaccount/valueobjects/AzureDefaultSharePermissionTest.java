package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureDefaultSharePermissionTest {
  @Test
  public void azureDefaultSharePermissionConstants_shouldNotBeNull() {
    assertThat(AzureDefaultSharePermission.NONE)
      .as("NONE constant should not be null")
      .isNotNull();

    assertThat(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_READER)
      .as("STORAGE_FILE_DATA_SMB_SHARE_READER constant should not be null")
      .isNotNull();

    assertThat(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_CONTRIBUTOR)
      .as("STORAGE_FILE_DATA_SMB_SHARE_CONTRIBUTOR constant should not be null")
      .isNotNull();

    assertThat(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_ELEVATED_CONTRIBUTOR)
      .as("STORAGE_FILE_DATA_SMB_SHARE_ELEVATED_CONTRIBUTOR constant should not be null")
      .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureDefaultSharePermission() {
    assertThat(AzureDefaultSharePermission.fromString("None"))
      .as("fromString should return NONE for 'None'")
      .isEqualTo(AzureDefaultSharePermission.NONE);

    assertThat(AzureDefaultSharePermission.fromString("StorageFileDataSmbShareReader"))
      .as("fromString should return STORAGE_FILE_DATA_SMB_SHARE_READER for 'StorageFileDataSmbShareReader'")
      .isEqualTo(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_READER);

    assertThat(AzureDefaultSharePermission.fromString("StorageFileDataSmbShareContributor"))
      .as("fromString should return STORAGE_FILE_DATA_SMB_SHARE_CONTRIBUTOR for 'StorageFileDataSmbShareContributor'")
      .isEqualTo(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_CONTRIBUTOR);

    assertThat(AzureDefaultSharePermission.fromString("StorageFileDataSmbShareElevatedContributor"))
      .as("fromString should return STORAGE_FILE_DATA_SMB_SHARE_ELEVATED_CONTRIBUTOR for " +
        "'StorageFileDataSmbShareElevatedContributor'")
      .isEqualTo(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_ELEVATED_CONTRIBUTOR);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureDefaultSharePermissionsWithCorrectSize() {
    Collection<AzureDefaultSharePermission> values = AzureDefaultSharePermission.values();

    assertThat(values)
      .as("Values should contain NONE, STORAGE_FILE_DATA_SMB_SHARE_READER, " +
        "STORAGE_FILE_DATA_SMB_SHARE_CONTRIBUTOR, and STORAGE_FILE_DATA_SMB_SHARE_ELEVATED_CONTRIBUTOR " +
        "and have exactly 4 values")
      .containsExactlyInAnyOrder(
        AzureDefaultSharePermission.NONE,
        AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_READER,
        AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_CONTRIBUTOR,
        AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_ELEVATED_CONTRIBUTOR);
  }
}