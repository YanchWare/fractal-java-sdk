package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountFileService {
  Boolean deleteRetentionEnabled;
  Integer deleteRetentionDays;

  public static AzureStorageAccountFileServiceBuilder builder() {
    return new AzureStorageAccountFileServiceBuilder();
  }

  public static class AzureStorageAccountFileServiceBuilder {
    private final AzureStorageAccountFileService fileService;
    private final AzureStorageAccountFileServiceBuilder builder;

    public AzureStorageAccountFileServiceBuilder() {
      this.fileService = new AzureStorageAccountFileService();
      this.builder = this;
    }

    public AzureStorageAccountFileServiceBuilder withDeleteRetentionEnabled(Boolean deleteRetentionEnabled) {
      fileService.setDeleteRetentionEnabled(deleteRetentionEnabled);
      return builder;
    }

    public AzureStorageAccountFileServiceBuilder withDeleteRetentionDays(Integer deleteRetentionDays) {
      fileService.setDeleteRetentionDays(deleteRetentionDays);
      return builder;
    }

    public AzureStorageAccountFileService build() {
      return fileService;
    }
  }
}
