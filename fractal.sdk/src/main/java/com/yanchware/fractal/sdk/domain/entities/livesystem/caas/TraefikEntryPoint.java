package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TraefikEntryPoint {
  private String name;
  private boolean isTlsEnabled;
  private int port;
  private String protocol;
  private boolean isHttpsRedirected;
}
