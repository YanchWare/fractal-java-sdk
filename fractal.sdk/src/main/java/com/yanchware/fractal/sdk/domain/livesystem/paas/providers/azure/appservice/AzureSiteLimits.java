package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice;

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
  private Integer maxDiskSizeInMb;

  /**
   * Maximum allowed memory usage in MB.
   */
  private Integer maxMemoryInMb;

  /**
   * Maximum allowed CPU usage percentage.
   */
  private Double maxPercentageCpu;
}
