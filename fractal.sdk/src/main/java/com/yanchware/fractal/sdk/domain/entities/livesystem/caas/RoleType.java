package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleType {
  APP_ROLE_ASSIGNMENT("AppRoleAssignment"),
  BUILT_IN_ROLE("BuiltInRole"),
  CUSTOM_ROLE("CustomRole"),
  OCELOT_ROLE("OcelotRole");

  private final String roleType;

  RoleType(final String roleType) {
    this.roleType = roleType;
  }

  @JsonValue
  public String getId() {
    return roleType;
  }

  @Override
  public String toString() {
    return roleType;
  }
}
