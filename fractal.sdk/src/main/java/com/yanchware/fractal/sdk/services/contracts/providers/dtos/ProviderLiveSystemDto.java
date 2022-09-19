package com.yanchware.fractal.sdk.services.contracts.providers.dtos;

import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.EnvironmentDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ProviderLiveSystemDto {
  private String id;
  private String etag;
  private EnvironmentDto environment;
  private Date created;
  private Date lastUpdated;
  private List<ProviderLiveSystemComponentDto> components;
  private String errorCode;
}
