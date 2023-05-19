package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TraefikEntryPoint {
  private String name;

  @JsonProperty(value = "isTlsEnabled")
  private boolean isTlsEnabled;

  private int port;

  private int exposedPort;

  private String protocol;

  @JsonProperty(value = "isHttpsRedirected")
  private boolean isHttpsRedirected;
}
