package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TraefikTlsCertificate {
  private String certSecretId;
  private String keySecretId;
  private boolean isDefault;
}
