package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class InstantiationStepDto {
  private String id;
  private String liveSystemId;
  private Date lastUpdated;
  private ProviderTypeDto provider;
  private LiveSystemComponentDto component;
  private boolean isExecuting;
}
