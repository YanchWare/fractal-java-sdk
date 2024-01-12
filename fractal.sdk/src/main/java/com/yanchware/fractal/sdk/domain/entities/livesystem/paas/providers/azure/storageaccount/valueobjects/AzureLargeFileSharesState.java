package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * <pre>
 * Allow large file shares if sets to Enabled. It cannot be disabled once it is enabled.
 * </pre>
 */
public enum AzureLargeFileSharesState {
  DISABLED ("Disabled"),
  ENABLED ("Enabled");
  
  private final String id;

  AzureLargeFileSharesState(final String id) {
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
