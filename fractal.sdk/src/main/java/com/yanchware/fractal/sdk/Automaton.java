package com.yanchware.fractal.sdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.configuration.EnvVarSdkConfiguration;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.configuration.instantiation.InstantiationConfiguration;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemId;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.BlueprintService;
import com.yanchware.fractal.sdk.services.LiveSystemService;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.CreateBlueprintCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.InstantiateLiveSystemCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemMutationDto;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;

@Slf4j
public class Automaton {
    private static Automaton instance;
    private static BlueprintService blueprintService;
    private static LiveSystemService liveSystemService;
    private static RetryRegistry serviceRetryRegistry;

    private Automaton(HttpClient httpClient, SdkConfiguration sdkConfiguration) {
        Automaton.serviceRetryRegistry = getDefaultRetryRegistry();
        Automaton.blueprintService = new BlueprintService(httpClient, sdkConfiguration, Automaton.serviceRetryRegistry);
        Automaton.liveSystemService = new LiveSystemService(httpClient, sdkConfiguration, Automaton.serviceRetryRegistry);
    }

    // Used for unit testing:
    protected static void initializeAutomaton(HttpClient httpClient, SdkConfiguration sdkConfiguration) {
        instance = new Automaton(httpClient, sdkConfiguration);
    }

    protected static void initializeAutomaton(SdkConfiguration sdkConfiguration) {
        var builder = HttpClient
            .newBuilder()
            .version(HttpClient.Version.HTTP_2);

        initializeAutomaton(builder.build(), sdkConfiguration);
    }

    private static RetryRegistry getDefaultRetryRegistry() {
        return RetryRegistry.of(RetryConfig.custom()
            .maxAttempts(5)
            .intervalFunction(IntervalFunction.ofExponentialBackoff(5000L))
            .build());
    }

    private static void waitForMutationInstantiation(
        LiveSystem liveSystem,
        LiveSystemMutationDto mutation)
        throws InstantiatorException {
            liveSystemService.checkLiveSystemMutationStatus(
              new LiveSystemId(liveSystem.getLiveSystemId()),
              mutation.getId());
        }

    private static String envrionmentToJsonString(Environment environment) throws InstantiatorException {
        try {
            return serialize(environment);
        } catch (JsonProcessingException e) {
            var errorMessage = String.format("Unable to serialize Instantiate LiveSystem environment. %s",
                e.getLocalizedMessage());

            log.error(errorMessage, e);
            throw new InstantiatorException(errorMessage, e);
        }
    }
    
    private static LiveSystemMutationDto instantiateLiveSystem(LiveSystem liveSystem)
        throws InstantiatorException {
        log.info("Starting to instantiate live system [id: '{}']", liveSystem.getLiveSystemId());
        log.info("Environment -> {}", envrionmentToJsonString(liveSystem.getEnvironment()));

        createOrUpdateBlueprint(liveSystem);
        
        return liveSystemService.instantiate(InstantiateLiveSystemCommandRequest.fromLiveSystem(liveSystem));
    }
    
    private static void createOrUpdateBlueprint(LiveSystem liveSystem) throws InstantiatorException {
        var blueprintCommand = CreateBlueprintCommandRequest.fromLiveSystem(
            liveSystem.getComponents(), liveSystem.getFractalId());

        blueprintService.createOrUpdateBlueprint(blueprintCommand, liveSystem.getFractalId());
    }

    public static void instantiate(List<LiveSystem> liveSystems) throws InstantiatorException {
        if (instance == null) {
            initializeAutomaton(getSdkConfiguration());
        }

        for (LiveSystem liveSystem : liveSystems) {
            instantiateLiveSystem(liveSystem);
        }
    }

    public static void instantiate(List<LiveSystem> liveSystems, InstantiationConfiguration config)
        throws InstantiatorException {

        if (instance == null) {
            initializeAutomaton(getSdkConfiguration());
        }
        
        var liveSystemsMutations = new ArrayList<ImmutablePair<LiveSystem, LiveSystemMutationDto>>();
        
        for (LiveSystem liveSystem : liveSystems) {
            liveSystemsMutations.add(new ImmutablePair<>(liveSystem, instantiateLiveSystem(liveSystem)));
        }

        if(config != null && config.waitConfiguration != null && config.getWaitConfiguration().waitForInstantiation)
        {
            for (var liveSystemMutation : liveSystemsMutations) {
                waitForMutationInstantiation(
                    liveSystemMutation.getKey(),
                    liveSystemMutation.getValue());
            }
        }
    }

    private static EnvVarSdkConfiguration getSdkConfiguration() throws InstantiatorException {
        EnvVarSdkConfiguration configuration;
        try {
            configuration = new EnvVarSdkConfiguration();
        } catch (URISyntaxException e) {
            throw new InstantiatorException("Error with Sdk configuration", e);
        }
        return configuration;
    }
}
