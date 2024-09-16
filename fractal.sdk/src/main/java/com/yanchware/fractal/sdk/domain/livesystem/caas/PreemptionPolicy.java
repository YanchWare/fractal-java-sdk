package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PreemptionPolicy {
  PREEMPT_LOWER_PRIORITY("PreemptLowerPriority"),
  NEVER("Never");

  private final String id;

  PreemptionPolicy(final String id) {
    this.id = id;
  }

  @JsonValue
  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return id;
  }
}
