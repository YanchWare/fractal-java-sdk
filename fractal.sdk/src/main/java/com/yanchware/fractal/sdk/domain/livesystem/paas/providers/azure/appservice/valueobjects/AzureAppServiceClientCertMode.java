package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureAppServiceClientCertMode {
  REQUIRED("Required"),
  OPTIONAL("Optional"),
  OPTIONAL_INTERACTIVE_USER("OptionalInteractiveUser");

  private final String id;

  AzureAppServiceClientCertMode(final String id) {
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
