package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class TraefikEntryPoint {
  private String name;
  private String protocol;
  private int port;
  private int exposedPort;

  @JsonProperty(value = "isTlsEnabled")
  private boolean isTlsEnabled;

  @JsonProperty(value = "isHttpsRedirected")
  private boolean isHttpsRedirected;

  public static TraefikEntryPointBuilder builder() {
    return new TraefikEntryPointBuilder();
  }

  public static class TraefikEntryPointBuilder {
    private final TraefikEntryPoint entryPoint;
    private final TraefikEntryPointBuilder builder;

    public TraefikEntryPointBuilder() {
      entryPoint = new TraefikEntryPoint();
      builder = this;
    }


    public TraefikEntryPointBuilder withName(String name) {
      entryPoint.setName(name);
      return builder;
    }

    public TraefikEntryPointBuilder withTlsEnabled(boolean isTlsEnabled) {
      entryPoint.setTlsEnabled(isTlsEnabled);
      return builder;
    }

    public TraefikEntryPointBuilder withPort(int port) {
      entryPoint.setPort(port);
      return builder;
    }

    public TraefikEntryPointBuilder withExposedPort(int exposedPort) {
      entryPoint.setExposedPort(exposedPort);
      return builder;
    }

    public TraefikEntryPointBuilder withProtocol(String protocol) {
      entryPoint.setProtocol(protocol);
      return builder;
    }

    public TraefikEntryPointBuilder withHttpsRedirected(boolean isHttpsRedirected) {
      entryPoint.setHttpsRedirected(isHttpsRedirected);
      return builder;
    }


    public TraefikEntryPoint build() {
      return entryPoint;
    }
  }
}
