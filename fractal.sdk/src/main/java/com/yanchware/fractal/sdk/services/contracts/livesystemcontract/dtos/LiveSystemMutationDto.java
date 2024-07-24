package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
public class LiveSystemMutationDto {
  private String liveSystemId;
  private String id;
  private LiveSystemMutationStatusDto status;
  private Set<String> componentsReadyIds;
  private Set<String> componentsCompletedIds;
  private Set<String> componentsFailedIds;
  private Map<String, LiveSystemComponentDto> componentsById;
  private Map<String, Set<String>> componentsBlocked;
}

