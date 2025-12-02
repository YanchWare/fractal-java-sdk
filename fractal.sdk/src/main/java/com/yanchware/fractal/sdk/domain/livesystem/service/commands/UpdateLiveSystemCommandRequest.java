package com.yanchware.fractal.sdk.domain.livesystem.service.commands;

import com.yanchware.fractal.sdk.domain.livesystem.EnvironmentReference;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.LiveSystemComponentDto;

import java.util.Map;

public record UpdateLiveSystemCommandRequest(
  String liveSystemId,
  String fractalId,
  String description,
  String provider,
  //not set, here just to match the request in liveSystem. We will have provider at LiveSystemComponent level
  Map<String, LiveSystemComponentDto> blueprintMap,
  EnvironmentReference environment)
{
}
