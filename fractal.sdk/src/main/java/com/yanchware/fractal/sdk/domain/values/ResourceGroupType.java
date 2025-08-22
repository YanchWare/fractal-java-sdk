package com.yanchware.fractal.sdk.domain.values;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResourceGroupType {
  PERSONAL("Personal"),
  ORGANIZATIONAL("Organizational");

  private final String value;

  ResourceGroupType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
