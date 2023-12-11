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
  private EnvironmentIdDto id;
  private Map<String, Object> parameters;

  public static EnvironmentDto fromEnvironment(Environment env) {
    return new EnvironmentDto(EnvironmentIdDto.fromEnvironment(env), env.getParameters());
  }
}

