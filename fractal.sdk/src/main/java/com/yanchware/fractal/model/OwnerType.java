package com.yanchware.fractal.model;

public enum OwnerType {
    PERSON("person"),
    SQUAD("squad"),
    TRIBE("tribe"),
    ORGANIZATION("organization");

    private final String value;

    OwnerType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
