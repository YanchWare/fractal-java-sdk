package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ForwardAuthSettings {
  private String oidcClientId;
  private String oidcClientSecretId;
  private String forwardAuthSecretId;
  private String oidcIssuer;
}
