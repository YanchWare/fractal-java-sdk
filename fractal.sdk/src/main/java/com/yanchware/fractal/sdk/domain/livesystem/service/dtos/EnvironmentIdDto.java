package com.yanchware.fractal.sdk.domain.livesystem.service.dtos;

import com.yanchware.fractal.sdk.domain.environment.EnvironmentAggregate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentIdDto {
  private EnvironmentTypeDto type;
  private UUID ownerId;
  private String shortName;

  public static EnvironmentIdDto fromEnvironment(EnvironmentAggregate env) {
    return new EnvironmentIdDto(
        EnvironmentTypeDto.fromString(env.getEnvironmentType().toString()),
        env.getOwnerId(),
        env.getShortName());
  }

  @Override
  public String toString() {
    return type + "\\" + ownerId + "\\" + shortName;
  }
}
