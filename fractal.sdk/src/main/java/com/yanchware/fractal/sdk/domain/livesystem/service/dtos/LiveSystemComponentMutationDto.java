package com.yanchware.fractal.sdk.domain.livesystem.service.dtos;

import java.util.Date;

public record LiveSystemComponentMutationDto (
  String liveSystemId,
  String id,
  LiveSystemMutationStatusDto status,
  LiveSystemComponentDto component,
  Date created,
  Date lastUpdated){ }
