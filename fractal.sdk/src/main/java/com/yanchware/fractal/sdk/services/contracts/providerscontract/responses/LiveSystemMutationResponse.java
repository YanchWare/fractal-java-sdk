package com.yanchware.fractal.sdk.services.contracts.providerscontract.responses;

import com.yanchware.fractal.sdk.services.contracts.providerscontract.dtos.ProviderLiveSystemComponentDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class LiveSystemMutationResponse {
  private List<ProviderLiveSystemComponentDto> components;
  private String mutationId;
}
