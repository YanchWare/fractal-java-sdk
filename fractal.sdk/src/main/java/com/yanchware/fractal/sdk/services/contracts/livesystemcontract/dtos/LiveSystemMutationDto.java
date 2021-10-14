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
  private Set<String> stepsReadyIds;
  private Set<String> stepsCompletedIds;
  private Set<String> stepsFailedIds;
  private Map<String, InstantiationStepDto> stepsById;
  private Map<String, Set<String>> stepsBlockedIdsByStepBlockingId;
}
