package com.yanchware.fractal.sdk.domain.livesystem.caas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TraefikTlsCertificate {
  private String certSecretId;
  private String keySecretId;
  private boolean isDefault;
}
