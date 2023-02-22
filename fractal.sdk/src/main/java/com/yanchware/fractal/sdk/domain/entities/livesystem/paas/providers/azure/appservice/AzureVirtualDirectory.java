package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

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
  String physicalPath;

  /**
   * Path to virtual application.
   */
  String virtualPath;
}
