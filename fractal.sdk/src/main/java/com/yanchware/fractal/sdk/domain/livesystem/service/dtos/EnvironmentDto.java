package com.yanchware.fractal.sdk.domain.livesystem.service.dtos;

import com.yanchware.fractal.sdk.domain.environment.EnvironmentAggregate;
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

  public static EnvironmentDto fromEnvironment(EnvironmentAggregate env) {
    return new EnvironmentDto(EnvironmentIdDto.fromEnvironment(env), env.getParameters());
  }
}

