package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureSiteLimits {
  /**
   * Maximum allowed disk size usage in MB.
   */
  Integer maxDiskSizeInMb;

  /**
   * Maximum allowed memory usage in MB.
   */
  Integer maxMemoryInMb;

  /**
   * Maximum allowed CPU usage percentage.
   */
  Double maxPercentageCpu;
}
