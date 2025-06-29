package com.yanchware.fractal.sdk.utils;

import com.flextrade.jfixture.NoSpecimen;
import com.flextrade.jfixture.SpecimenBuilder;
import com.flextrade.jfixture.SpecimenContext;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentIdDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentTypeDto;

import java.util.UUID;

public class EnvironmentIdDtoBuilder implements SpecimenBuilder {
  @Override
  public Object create(Object request, SpecimenContext context) {
    return !request.equals(EnvironmentIdDto.class)
      ? new NoSpecimen()
      : new EnvironmentIdDto(EnvironmentTypeDto.PERSONAL, UUID.randomUUID(), UUID.randomUUID().toString());
  }
}
