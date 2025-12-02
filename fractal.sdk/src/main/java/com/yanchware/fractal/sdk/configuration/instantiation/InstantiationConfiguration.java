package com.yanchware.fractal.sdk.configuration.instantiation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class InstantiationConfiguration {

  /**
   * Defines the wait configuration for the instantiation process.
   * By default, the instantiation terminates without waiting.
   */
  public InstantiationWaitConfiguration waitConfiguration;

  /**
   * Indicates whether to create or update a blueprint during the instantiation process.
   * This variable is used to control the behavior of blueprint management.
   * If set to true, an existing blueprint will be updated or a new blueprint will
   * be created as part of the Live System instantiation.
   */
  public boolean createOrUpdateBlueprint;
}
