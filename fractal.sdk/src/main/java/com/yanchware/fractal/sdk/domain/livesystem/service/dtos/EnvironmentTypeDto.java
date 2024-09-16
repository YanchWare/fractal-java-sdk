package com.yanchware.fractal.sdk.domain.livesystem.service.dtos;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnvironmentTypeDto {
  UNKNOWN("Unknown"),
  PERSONAL("Personal"),
  ORGANIZATIONAL("Organizational");

  private final String environmentType;

  EnvironmentTypeDto(final String environmentType) {
    this.environmentType = environmentType;
  }

  @JsonValue
  public String getEnvironmentType() {
    return environmentType;
  }

  public static EnvironmentTypeDto fromString(String text) {
    for (var item : values()) {
      if (item.environmentType.equalsIgnoreCase(text)) {
        return item;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return environmentType;
  }
}
