package com.yanchware.fractal.sdk;

import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.configuration.EnvVarServiceConfiguration;
import com.yanchware.fractal.sdk.configuration.ServiceConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.BlueprintService;
import com.yanchware.fractal.sdk.services.LiveSystemService;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.List;

@Slf4j
public class Automaton {

    private Automaton() {
    }

    public static void instantiate(List<LiveSystem> liveSystems) throws InstantiatorException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        ServiceConfiguration serviceConfiguration = new EnvVarServiceConfiguration();
        BlueprintService blueprintService = new BlueprintService(httpClient, serviceConfiguration);
        LiveSystemService liveSystemService = new LiveSystemService(httpClient, serviceConfiguration);

        blueprintService.instantiate(null, "", "");
        liveSystemService.instantiate(null);
    }

}
