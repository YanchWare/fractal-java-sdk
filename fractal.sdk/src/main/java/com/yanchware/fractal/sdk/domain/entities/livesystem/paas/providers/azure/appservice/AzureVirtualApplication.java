package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureVirtualApplication {
  /**
   * Physical path.
   */
  private String physicalPath;
  private Boolean preloadEnabled;

  /**
   * Virtual directories for virtual application.
   */
  private Collection<AzureVirtualDirectory> virtualDirectories;

  /**
   * Virtual path.
   */
  private String virtualPath;
}
