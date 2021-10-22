package com.yanchware.fractal.sdk.domain.entities.livesystem;

public enum KafkaACLPatternType {
    LITERAL("literal"),
    PREFIXED("prefixed"),
    WILDCARD("*");

    private final String id;

    KafkaACLPatternType(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
