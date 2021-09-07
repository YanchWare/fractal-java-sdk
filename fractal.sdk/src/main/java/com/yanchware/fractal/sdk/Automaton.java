package com.yanchware.fractal.sdk;

import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.configuration.EnvVarSdkConfiguration;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.BlueprintService;
import com.yanchware.fractal.sdk.services.LiveSystemService;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.CreateBlueprintCommandRequest;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.List;

@Slf4j
public class Automaton {

    static HttpClient httpClient;

    static SdkConfiguration sdkConfiguration;

    static {
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        sdkConfiguration = new EnvVarSdkConfiguration();
    }

    public Automaton(HttpClient httpClient, SdkConfiguration sdkConfiguration) {
        Automaton.httpClient = httpClient;
        Automaton.sdkConfiguration = sdkConfiguration;
    }

    public static void instantiate(List<LiveSystem> liveSystems) throws InstantiatorException {
        BlueprintService blueprintService = new BlueprintService(httpClient, sdkConfiguration);
        LiveSystemService liveSystemService = new LiveSystemService(httpClient, sdkConfiguration);

        for (LiveSystem ls : liveSystems) {
            blueprintService.instantiate(CreateBlueprintCommandRequest.fromLiveSystem(ls.getComponents()), "", "");
            //liveSystemService.instantiate(null);
        }
    }

}
