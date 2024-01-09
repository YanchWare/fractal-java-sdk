package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureDefaultSharePermissionTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureDefaultSharePermission.NONE, "NONE constant should not be null");
    
    assertNotNull(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_READER, 
        "STORAGE_FILE_DATA_SMB_SHARE_READER constant should not be null");
    
    assertNotNull(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_CONTRIBUTOR, 
        "STORAGE_FILE_DATA_SMB_SHARE_CONTRIBUTOR constant should not be null");

    assertNotNull(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_ELEVATED_CONTRIBUTOR,
        "STORAGE_FILE_DATA_SMB_SHARE_ELEVATED_CONTRIBUTOR constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureDefaultSharePermission.NONE,
        AzureDefaultSharePermission.fromString("None"),
        "fromString should return NONE for 'None'");

    assertEquals(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_READER,
        AzureDefaultSharePermission.fromString("StorageFileDataSmbShareReader"),
        "fromString should return STORAGE_FILE_DATA_SMB_SHARE_READER for 'StorageFileDataSmbShareReader'");

    assertEquals(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_CONTRIBUTOR,
        AzureDefaultSharePermission.fromString("StorageFileDataSmbShareContributor"),
        "fromString should return STORAGE_FILE_DATA_SMB_SHARE_CONTRIBUTOR for 'StorageFileDataSmbShareContributor'");

    assertEquals(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_ELEVATED_CONTRIBUTOR,
        AzureDefaultSharePermission.fromString("StorageFileDataSmbShareElevatedContributor"),
        "fromString should return STORAGE_FILE_DATA_SMB_SHARE_ELEVATED_CONTRIBUTOR for 'StorageFileDataSmbShareElevatedContributor'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureDefaultSharePermission> values = AzureDefaultSharePermission.values();
    assertTrue(values.contains(AzureDefaultSharePermission.NONE), "Values should contain NONE");
    
    assertTrue(values.contains(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_READER), 
        "Values should contain STORAGE_FILE_DATA_SMB_SHARE_READER");
    
    assertTrue(values.contains(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_CONTRIBUTOR), 
        "Values should contain STORAGE_FILE_DATA_SMB_SHARE_CONTRIBUTOR");
    
    assertTrue(values.contains(AzureDefaultSharePermission.STORAGE_FILE_DATA_SMB_SHARE_ELEVATED_CONTRIBUTOR), 
        "Values should contain STORAGE_FILE_DATA_SMB_SHARE_ELEVATED_CONTRIBUTOR");
    
    assertEquals(4, values.size(), "There should be exactly 4 values");
  }
}