package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class CustomWorkloadRole {
  private String name;
  private String scope;
  private RoleType roleType;

  public static CustomWorkloadRoleBuilder builder() {
    return new CustomWorkloadRoleBuilder();
  }

  public static class CustomWorkloadRoleBuilder {
    private final CustomWorkloadRole role;
    private final CustomWorkloadRoleBuilder builder;

    public CustomWorkloadRoleBuilder() {
      this.role = new CustomWorkloadRole();
      this.builder = this;
    }

    public CustomWorkloadRoleBuilder withName(String name) {
      role.setName(name);
      return builder;
    }

    public CustomWorkloadRoleBuilder withScope(String scope) {
      role.setScope(scope);
      return builder;
    }

    public CustomWorkloadRoleBuilder withRoleType(RoleType roleType) {
      role.setRoleType(roleType);
      return builder;
    }

    public CustomWorkloadRole build() {
      return role;
    }
  }
}
