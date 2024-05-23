package com.yanchware.fractal.sdk.services.contracts.environmentscontract.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
public class InitializationStepResponse {
  private UUID id;
  private String name;
  private String description;
  private Integer order;
  private String resourceName;
  private String resourceType;
  private Map<String, Object> outputFields;
  private String lastOperationStatusMessage;
  private String status;
  private Integer retryCount;
  private Date createdAt;
  private Date updatedAt;
}
