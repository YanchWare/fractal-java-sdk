package com.yanchware.fractal.sdk.domain.entities.livesystem.paas;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.RoleType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class RoleAssignment {
  private String roleName;
  private String scope;
  private RoleType roleType;
  private IdentityType identityType;
}
