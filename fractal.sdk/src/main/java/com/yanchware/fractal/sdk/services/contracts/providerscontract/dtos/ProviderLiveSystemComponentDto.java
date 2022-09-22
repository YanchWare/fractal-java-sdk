package com.yanchware.fractal.sdk.services.contracts.providerscontract.dtos;

import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentStatusDto;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
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
