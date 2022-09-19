package com.yanchware.fractal.sdk.configuration.instantiation;

import lombok.Data;

@Data
public class InstantiationConfiguration {

    /**
     * Defines the wait configuration for the instantiation process.
     * By default the instantiation terminates without waiting.
     */
    public InstantiationWaitConfiguration waitConfiguration;
}
