package com.yanchware.fractal.sdk.aggregates;

import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;

import java.util.Map;

public abstract class CloudAgent {
    public abstract ProviderType getProvider();
    public abstract void injectIntoEnvironmentParameters(Map<String, Object> environmentParameters);
}
