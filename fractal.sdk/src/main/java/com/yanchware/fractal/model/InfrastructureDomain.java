package com.yanchware.fractal.model;

public enum InfrastructureDomain {
    API_MANAGEMENT("ApiManagement"),
    CUSTOM_WORKLOAD("CustomWorkload"),
    MESSAGING("Messaging"),
    NETWORK("Network"),
    COMPUTE("Compute"),
    OBSERVABILITY("Observability"),
    SECURITY("Security"),
    STORAGE("Storage");

    private final String value;

    InfrastructureDomain(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
