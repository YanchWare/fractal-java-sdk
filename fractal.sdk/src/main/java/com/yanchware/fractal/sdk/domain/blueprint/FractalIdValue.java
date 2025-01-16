package com.yanchware.fractal.sdk.domain.blueprint;

public record FractalIdValue(String resourceGroupId, String name, String version) {
  @Override
  public String toString() {
    return String.format("%s/%s:%s", resourceGroupId, name, version);
  }
}
