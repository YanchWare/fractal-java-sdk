package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos.BlueprintDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LiveSystemDto {
  private String liveSystemId;
  private String fractalId;
  private String requesterId;
  private BlueprintDto blueprint;
  private Date created;
  private Date lastUpdated;
  private LiveSystemStatusDto status;
  private String statusMessage;
  private EnvironmentDto environment;
}
