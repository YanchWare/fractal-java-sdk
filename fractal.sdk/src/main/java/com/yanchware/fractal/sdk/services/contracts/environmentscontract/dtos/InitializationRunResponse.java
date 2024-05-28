package com.yanchware.fractal.sdk.services.contracts.environmentscontract.dtos;

import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.EnvironmentIdDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class InitializationRunResponse {
  private UUID id;
  private EnvironmentIdDto environmentId;
  private String cloudProvider;
  private List<InitializationStepResponse> steps;
  private String status;
  private String requester;
  private Date createdAt;
}

