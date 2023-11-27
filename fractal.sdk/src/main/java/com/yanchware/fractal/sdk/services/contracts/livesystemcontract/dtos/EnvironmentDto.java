package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.EnvironmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentDto {
  private EnvironmentType environmentType;
  private String ownerId;
  private String shortName;
  private Map<String, Object> parameters;

  public static EnvironmentDto fromEnvironment(Environment env) {
    return new EnvironmentDto(
        env.getEnvironmentType(),
        env.getOwnerId(),
        env.getShortName(),
        env.getParameters());
  }
}
