package com.yanchware.fractal.sdk.domain.blueprint;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemAggregate;
import com.yanchware.fractal.sdk.domain.blueprint.service.dtos.BlueprintComponentDto;
import io.github.resilience4j.retry.RetryRegistry;

import java.net.http.HttpClient;

public class BlueprintFactory {
    private final HttpClient client;
    private final SdkConfiguration sdkConfiguration;
    private final RetryRegistry retryRegistry;

    public BlueprintFactory(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {

        this.client = client;
        this.sdkConfiguration = sdkConfiguration;
        this.retryRegistry = retryRegistry;
    }

    public BlueprintAggregate getBlueprintAggregate(LiveSystemAggregate liveSystem) {
        var fractalId = liveSystem.getFractalId();
        return new BlueprintAggregate(
                client,
                sdkConfiguration,
                retryRegistry,
                fractalId,
                String.format("Blueprint created via SDK from LiveSystem with Fractal ID: %s", fractalId),
                true,
                BlueprintComponentDto.fromLiveSystemComponents(liveSystem.getComponents()));
    }
}
