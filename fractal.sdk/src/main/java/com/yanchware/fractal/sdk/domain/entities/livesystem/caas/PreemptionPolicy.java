package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

public enum PreemptionPolicy {
  PreemptLowerPriority("PreemptLowerPriority"),
  Never("Never");

  private final String id;

  PreemptionPolicy(final String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return id;
  }
}
