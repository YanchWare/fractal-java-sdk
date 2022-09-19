package com.yanchware.fractal.sdk.services.contracts.providers.responses;

import com.yanchware.fractal.sdk.services.contracts.providers.dtos.ProviderLiveSystemDto;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class CurrentLiveSystemsResponse {
  private List<ProviderLiveSystemDto> liveSystems;
}
