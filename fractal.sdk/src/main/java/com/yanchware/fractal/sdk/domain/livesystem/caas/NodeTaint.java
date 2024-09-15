package com.yanchware.fractal.sdk.domain.livesystem.caas;

import lombok.Builder;

@Builder(setterPrefix = "with")
public class NodeTaint {
  private String key;
  private String value;
  private TaintEffect effect;

  @Override
  public String toString() {
    return String.format("%s=%s:%s", key, value, effect.toString());
  }
}
