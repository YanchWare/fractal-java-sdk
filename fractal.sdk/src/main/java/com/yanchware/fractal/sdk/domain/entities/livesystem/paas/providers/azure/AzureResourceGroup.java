package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
public class AzureResourceGroup {
  @NonNull
  private AzureRegion region;
  @NonNull
  private String name;
}
