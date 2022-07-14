package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

public enum PreemptionPolicy {
  PREEMPT_LOWER_PRIORITY("PreemptLowerPriority"),
  NEVER("Never");

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
