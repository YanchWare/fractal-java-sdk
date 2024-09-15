package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ACLOperation {
  ALL("all"),
  ALTER("alter"),
  ALTER_CONFIGS("alter_configs"),
  ANY("any"),
  CLUSTER_ACTION("cluster_action"),
  CREATE("create"),
  DELETE("delete"),
  DESCRIBE("describe"),
  DESCRIBE_CONFIGS("describe_configs"),
  IDEMPOTENT_WRITE("idempotent_write"),
  READ("read"),
  UNKNOWN("unknown"),
  WRITE("write");

  private final String id;

  ACLOperation(final String id) {
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
