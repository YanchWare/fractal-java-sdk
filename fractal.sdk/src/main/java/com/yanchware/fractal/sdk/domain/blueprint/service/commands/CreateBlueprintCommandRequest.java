package com.yanchware.fractal.sdk.domain.blueprint.service.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yanchware.fractal.sdk.domain.blueprint.service.dtos.BlueprintComponentDto;

import java.util.Collection;

public record CreateBlueprintCommandRequest(
  String description,
  @JsonProperty("private")
  boolean isPrivate,
  Collection<BlueprintComponentDto> components)
{
}
