package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.EnvironmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentIdDto {
  private EnvironmentType type;
  private UUID ownerId;
  private String shortName;

  public static EnvironmentIdDto fromEnvironment(Environment env) {
    return new EnvironmentIdDto(
        env.getEnvironmentType(),
        env.getOwnerId(),
        env.getShortName());
  }
}
