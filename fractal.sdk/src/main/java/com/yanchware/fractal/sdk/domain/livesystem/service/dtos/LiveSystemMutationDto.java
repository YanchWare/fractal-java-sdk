package com.yanchware.fractal.sdk.domain.livesystem.service.dtos;

import java.util.Map;
import java.util.Set;

public record LiveSystemMutationDto (
  String liveSystemId,
  String id,
  LiveSystemMutationStatusDto status,
  Set<String> componentsReadyIds,
  Set<String> componentsCompletedIds,
  Set<String> componentsFailedIds,
  Map<String, LiveSystemComponentDto> componentsById,
  Map<String, Set<String>> componentsBlocked){}

