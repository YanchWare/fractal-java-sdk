package com.yanchware.fractal.sdk.domain.accounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EntityStatus {
  UNKNOWN("Unknown"),
  ACTIVE("Active"),
  DELETING("Deleting");

  private final String value;

  EntityStatus(String value) {
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

  @JsonCreator
  public static EntityStatus fromString(String s) {
    if (s == null) return null;
    for (var e : values()) {
      if (e.value.equalsIgnoreCase(s) || e.name().equalsIgnoreCase(s)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Invalid EntityStatus: " + s);
  }
}