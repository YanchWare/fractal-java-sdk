package com.yanchware.fractal.model;

public enum InfrastructureTier {
    IAAS("IaaS"),
    CAAS("CaaS"),
    PAAS("PaaS"),
    FAAS("FaaS"),
    SAAS("SaaS");

    private final String value;

    InfrastructureTier(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
