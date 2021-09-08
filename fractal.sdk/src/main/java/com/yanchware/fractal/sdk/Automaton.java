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
    private static Automaton instance;

    private static HttpClient httpClient;
    private static SdkConfiguration sdkConfiguration;

    protected Automaton(HttpClient httpClient, SdkConfiguration sdkConfiguration) {
        Automaton.httpClient = httpClient;
        Automaton.sdkConfiguration = sdkConfiguration;
    }

    public static Automaton getInstance(HttpClient httpClient, SdkConfiguration sdkConfiguration) {
        if (instance == null) {
            instance = new Automaton(httpClient, sdkConfiguration);
        }
        return instance;
    }

    public static void instantiate(List<LiveSystem> liveSystems) throws InstantiatorException {
        if (instance == null) {
            instance = new Automaton(HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build(), new EnvVarSdkConfiguration());
        }

        BlueprintService blueprintService = new BlueprintService(httpClient, sdkConfiguration);
        LiveSystemService liveSystemService = new LiveSystemService(httpClient, sdkConfiguration);

        for (LiveSystem ls : liveSystems) {
            String fractalName = ls.getResourceGroupId() + "/" + ls.getId();
            blueprintService.instantiate(CreateBlueprintCommandRequest.fromLiveSystem(ls.getComponents(), "some blueprint description"), fractalName, "0.0.1");
            //liveSystemService.instantiate(null);
        }
    }

}
