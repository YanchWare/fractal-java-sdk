package com.yanchware.fractal.sdk.configuration.instantiation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class InstantiationConfiguration {

  /**
   * Defines the wait configuration for the instantiation process.
   * By default the instantiation terminates without waiting.
   */
  public InstantiationWaitConfiguration waitConfiguration;
}
