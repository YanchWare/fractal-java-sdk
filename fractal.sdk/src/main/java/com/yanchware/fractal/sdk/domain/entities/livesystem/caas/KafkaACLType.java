package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.fasterxml.jackson.annotation.JsonValue;

public enum KafkaACLType {
  CLUSTER("cluster"),
  DELEGATION_TOKEN("delegationToken"),
  GROUP("group"),
  TOPIC("topic"),
  TRANSACTIONAL_ID("transactionalId");

  private final String id;

  KafkaACLType(final String id) {
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
