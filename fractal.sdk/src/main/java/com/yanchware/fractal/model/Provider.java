package com.yanchware.fractal.model;

public enum Provider {
    CAAS("CaaS"),
    AZURE("Azure"),
    AWS("AWS"),
    GCP("GCP");

    private final String value;

    Provider(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
