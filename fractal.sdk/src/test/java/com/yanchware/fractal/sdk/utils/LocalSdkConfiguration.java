package com.yanchware.fractal.sdk.utils;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import lombok.SneakyThrows;

import java.net.URI;

public class LocalSdkConfiguration implements SdkConfiguration {
    @Override
    public String getClientId() {
        return "aa574c7e-8adb-4ab8-a783-b0b0db62acf2";
    }

    @Override
    public String getClientSecret() {
        return "MnBBt&ySyZjpa%88KfC_S";
    }

    @SneakyThrows
    @Override
    public URI getBlueprintEndpoint() {
        return new URI("https://api.fractal.cloud/blueprints");
    }

    @SneakyThrows
    @Override
    public URI getLiveSystemEndpoint() {
        return new URI("https://api.fractal.cloud/livesystems");
    }
}
