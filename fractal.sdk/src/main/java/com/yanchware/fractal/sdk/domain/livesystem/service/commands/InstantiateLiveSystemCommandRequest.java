package com.yanchware.fractal.sdk.domain.livesystem.service.commands;

import com.yanchware.fractal.sdk.domain.livesystem.EnvironmentReference;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.LiveSystemComponentDto;

import java.util.Map;

public record InstantiateLiveSystemCommandRequest(
    String liveSystemId,
    String fractalId,
    String description,
    String provider,
    Map<String, LiveSystemComponentDto> blueprintMap,
    EnvironmentReference environment){
}
