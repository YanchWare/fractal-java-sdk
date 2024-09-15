package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureVirtualDirectory {
  /**
   * Physical path.
   */
  private String physicalPath;

  /**
   * Path to virtual application.
   */
  private String virtualPath;
}
