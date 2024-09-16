package com.yanchware.fractal.sdk.domain.services.contracts.providerscontract.dtos;

import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.LiveSystemComponentStatusDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.domain.services.contracts.ComponentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class ProviderLiveSystemComponentDto extends ComponentDto {
  private int lastOperationRetried;
  private String systemMutationId;
  private LiveSystemComponentStatusDto status;
  private Map<String, Object> outputFields;
  private ProviderType provider;
  private String lastOperationStatusMessage;
  private String errorCode;
}
