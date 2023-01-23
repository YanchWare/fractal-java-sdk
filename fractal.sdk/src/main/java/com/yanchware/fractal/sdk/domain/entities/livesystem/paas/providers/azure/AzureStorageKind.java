package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureStorageKind {
  STORAGE ("Storage"),
  STORAGE_V2 ("StorageV2"),
  BLOB_STORAGE ("BlobStorage"),
  FILE_STORAGE ("FileStorage"),
  BLOCK_BLOB_STORAGE ("BlockBlobStorage");
  
  private final String id;

  AzureStorageKind(final String id) {
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
