package com.yanchware.fractal.sdk;

import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.configuration.EnvVarSdkConfiguration;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.BlueprintService;
import com.yanchware.fractal.sdk.services.LiveSystemService;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.CreateBlueprintCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.InstantiateLiveSystemCommandRequest;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.List;

@Slf4j
public class Automaton {
    private static Automaton instance;

    private static HttpClient httpClient;
    private static SdkConfiguration sdkConfiguration;

    private Automaton(HttpClient httpClient, SdkConfiguration sdkConfiguration) {
        Automaton.httpClient = httpClient;
        Automaton.sdkConfiguration = sdkConfiguration;
    }

    //used for unit testing
    protected static void initializeAutomaton(HttpClient httpClient, SdkConfiguration sdkConfiguration) {
        instance = new Automaton(httpClient, sdkConfiguration);
    }

    public static void instantiate(List<LiveSystem> liveSystems) throws InstantiatorException {
        if (instance == null) {
            instance = new Automaton(HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build(), new EnvVarSdkConfiguration());
        }

        // Improve resiliency
        RetryConfig retryConfig = RetryConfig.custom()
          .maxAttempts(20)
          .intervalFunction(IntervalFunction.ofExponentialBackoff())
          .build();

        RetryRegistry registry = RetryRegistry.of(retryConfig);

        BlueprintService blueprintService = new BlueprintService(httpClient, sdkConfiguration, registry);
        LiveSystemService liveSystemService = new LiveSystemService(httpClient, sdkConfiguration, registry);

        for (LiveSystem ls : liveSystems) {
            String fractalId = ls.getResourceGroupId() + "/" + ls.getName() + ":" + "1.0";
            var blueprintCommand = CreateBlueprintCommandRequest.fromLiveSystem(ls.getComponents(), "some blueprint description");
            var liveSystemCommand = InstantiateLiveSystemCommandRequest.fromLiveSystem(ls);

            log.info("BlueprintCmd : {}", blueprintCommand);
            log.info("LiveSystemCmd: {}", liveSystemCommand);

            blueprintService.createOrUpdateBlueprint(blueprintCommand, fractalId);
            liveSystemService.instantiate(liveSystemCommand);
        }
    }

}
