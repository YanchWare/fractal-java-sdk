package com.yanchware.fractal.sdk.domain.livesystem.paas;

import com.yanchware.fractal.sdk.domain.livesystem.caas.RoleType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class RoleAssignment {
  private String roleName;
  private String scope;
  private RoleType roleType;
  private IdentityType identityType;

  public static RoleAssignmentBuilder builder() {
    return new RoleAssignmentBuilder();
  }

  public static class RoleAssignmentBuilder {
    private final RoleAssignment roleAssignment;
    private final RoleAssignmentBuilder builder;

    public RoleAssignmentBuilder() {
      this.roleAssignment = new RoleAssignment();
      this.builder = this;
    }

    public RoleAssignmentBuilder withRoleName(String roleName) {
      roleAssignment.setRoleName(roleName);
      return builder;
    }

    public RoleAssignmentBuilder withScope(String scope) {
      roleAssignment.setScope(scope);
      return builder;
    }

    public RoleAssignmentBuilder withRoleType(RoleType roleType) {
      roleAssignment.setRoleType(roleType);
      return builder;
    }

    public RoleAssignmentBuilder withIdentityType(IdentityType identityType) {
      roleAssignment.setIdentityType(identityType);
      return builder;
    }

    public RoleAssignment build() {
      return roleAssignment;
    }
  }
}
