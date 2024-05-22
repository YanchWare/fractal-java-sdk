package com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands.dtos;

import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.EnvironmentIdDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
public class EnvironmentResponse {
  private EnvironmentIdDto id;
  private String name;
  private Collection<UUID> resourceGroups;
  private Map<String, String> parameters;
  private String status;
  private Date createdAt;
  private String createdBy;
  private Date updatedAt;
  private String updatedBy;
}
