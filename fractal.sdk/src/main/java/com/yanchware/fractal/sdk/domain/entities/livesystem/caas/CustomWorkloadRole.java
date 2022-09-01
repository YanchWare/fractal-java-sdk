package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class CustomWorkloadRole {
  private String name;
  private String scope;
  private boolean isOcelotRole;
}
