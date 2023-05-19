package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.aggregates.Environment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentDto {
  private String id;
  private String displayName;
  private String parentId;
  private String parentType;
  private Map<String, Object> parameters;

  public static EnvironmentDto fromEnvironment(Environment env) {
    return new EnvironmentDto(
        env.getId(),
        env.getDisplayName(),
        env.getParentId(),
        env.getParentType(),
        env.getParameters());
  }
}
