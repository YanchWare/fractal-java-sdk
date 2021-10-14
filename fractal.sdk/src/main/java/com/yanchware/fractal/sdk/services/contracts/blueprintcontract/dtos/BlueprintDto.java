package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
public class BlueprintDto {
  private String fractalId;
  private boolean isPrivate;
  private BlueprintStatusDto status;
  private String reasonCode;
  private String description;
  private Collection<BlueprintComponentDto> components;
  private Date created;
}
