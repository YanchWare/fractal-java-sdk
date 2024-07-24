package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LiveSystemComponentMutationDto {
  private String liveSystemId;
  private String id;
  private LiveSystemMutationStatusDto status;
  private LiveSystemComponentDto component;
  private Date created;
  private Date lastUpdated;
}
