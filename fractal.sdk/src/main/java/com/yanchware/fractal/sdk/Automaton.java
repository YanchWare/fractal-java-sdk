package com.yanchware.fractal.sdk;

import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.configuration.EnvVarSdkConfiguration;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.configuration.instantiation.InstantiationConfiguration;
import com.yanchware.fractal.sdk.configuration.instantiation.InstantiationWaitConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.BlueprintService;
import com.yanchware.fractal.sdk.services.LiveSystemService;
import com.yanchware.fractal.sdk.services.ProviderService;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.CreateBlueprintCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.InstantiateLiveSystemCommandRequest;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

import static com.yanchware.fractal.sdk.configuration.Constants.TEST_ENVIRONMENT;

@Slf4j
public class Automaton {
    private static Automaton instance;
    private static BlueprintService blueprintService;
    private static LiveSystemService liveSystemService;
    private static ProviderService providerService;
    private static RetryRegistry serviceRetryRegistry;

    private Automaton(HttpClient httpClient, SdkConfiguration sdkConfiguration) {
        Automaton.serviceRetryRegistry = getDefaultRetryRegistry();
        Automaton.blueprintService = new BlueprintService(httpClient, sdkConfiguration, serviceRetryRegistry);
        Automaton.liveSystemService = new LiveSystemService(httpClient, sdkConfiguration, serviceRetryRegistry);
        Automaton.providerService = new ProviderService(httpClient, sdkConfiguration);
    }

    // Used for unit testing:
    protected static void initializeAutomaton(HttpClient httpClient, SdkConfiguration sdkConfiguration) {
        instance = new Automaton(httpClient, sdkConfiguration);
    }

    protected static void initializeAutomaton(SdkConfiguration sdkConfiguration) {
        var builder = HttpClient
            .newBuilder()
            .version(HttpClient.Version.HTTP_2);

        if (isRunningAgainstTestEnvironment(sdkConfiguration)) {
            builder
                .sslContext(getTestSSLContext());
        }
        instance = new Automaton(builder.build(), sdkConfiguration);
    }

    private static RetryRegistry getDefaultRetryRegistry() {
        return RetryRegistry.of(RetryConfig.custom()
            .maxAttempts(5)
            .intervalFunction(IntervalFunction.ofExponentialBackoff())
            .build());
    }

    private static void waitForInstantiation(List<LiveSystem> liveSystems, InstantiationWaitConfiguration config)
        throws InstantiatorException {

        for (LiveSystem liveSystem : liveSystems) {
            providerService.checkLiveSystemStatus(liveSystem, liveSystemService, config);
        }
    }

    public static void instantiate(List<LiveSystem> liveSystems) throws InstantiatorException {
        if (instance == null) {
            EnvVarSdkConfiguration configuration;

            try {
                configuration = new EnvVarSdkConfiguration();
            } catch (URISyntaxException e) {
                throw new InstantiatorException("Error with Sdk configuration", e);
            }

            var builder = HttpClient
                .newBuilder()
                .version(HttpClient.Version.HTTP_2);

            if (isRunningAgainstTestEnvironment(configuration)) {
                builder
                    .sslContext(getTestSSLContext());
            }

            instance = new Automaton(builder.build(), configuration);
        }

        for (LiveSystem ls : liveSystems) {
            log.info("Starting to instantiate live system with id: {}", ls.getLiveSystemId());
            var blueprintCommand = CreateBlueprintCommandRequest.fromLiveSystem(ls.getComponents(), ls.getFractalId());
            var liveSystemCommand = InstantiateLiveSystemCommandRequest.fromLiveSystem(ls);

            blueprintService.createOrUpdateBlueprint(blueprintCommand, ls.getFractalId());
            liveSystemService.instantiate(liveSystemCommand);
        }
    }

    public static void instantiate(List<LiveSystem> liveSystems, InstantiationConfiguration config)
        throws InstantiatorException {

        instantiate(liveSystems);

        if(config != null && config.waitConfiguration != null && config.getWaitConfiguration().waitForInstantiation)
        {
            waitForInstantiation(liveSystems, config.getWaitConfiguration());
        }
    }

    private static boolean isRunningAgainstTestEnvironment(SdkConfiguration configuration) {
        return TEST_ENVIRONMENT.equalsIgnoreCase(configuration.getBlueprintEndpoint().getHost())
          && TEST_ENVIRONMENT.equalsIgnoreCase(configuration.getLiveSystemEndpoint().getHost());
    }

    private static SSLContext getTestSSLContext() {
        var trustAllCerts = new TrustManager[] {
          new X509TrustManager() {
              public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                  return new X509Certificate[0];
              }
              public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
              public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
          }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            return sc;
        } catch (GeneralSecurityException e) {
            log.warn("Error installing test trust manager", e);
        }

        return null;
    }

}
