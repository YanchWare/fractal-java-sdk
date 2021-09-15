package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos.BlueprintDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class LiveSystemDto {
  private String liveSystemId;
  private String fractalId;
  private String name;
  private String requesterId;
  private BlueprintDto blueprint;
  private Date orderTimestamp;
  private LiveSystemStatusDto status;
  private String statusMessage;
  private EnvironmentDto environment;
  private Map<String, LiveSystemMutationDto> systemMutationsById;
  private List<LiveSystemComponentDto> components;
}
