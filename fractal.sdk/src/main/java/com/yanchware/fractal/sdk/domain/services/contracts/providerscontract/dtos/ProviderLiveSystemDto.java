package com.yanchware.fractal.sdk.domain.services.contracts.providerscontract.dtos;

import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentDto;
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
