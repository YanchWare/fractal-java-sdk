package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureStorageAccountFileService {
  Boolean deleteRetentionEnabled;
  Integer deleteRetentionDays;
}
