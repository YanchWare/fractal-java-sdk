package com.yanchware.fractal.sdk.domain.entities.livesystem;

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

    public String getId() {
        return id;
    }
}
