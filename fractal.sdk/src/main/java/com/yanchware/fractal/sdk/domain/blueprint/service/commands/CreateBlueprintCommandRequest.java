package com.yanchware.fractal.sdk.domain.blueprint.service.commands;

import com.yanchware.fractal.sdk.domain.blueprint.service.dtos.BlueprintComponentDto;

import java.util.Collection;

public record CreateBlueprintCommandRequest (
    String description,
    boolean isPrivate,
    Collection<BlueprintComponentDto> components) { }
