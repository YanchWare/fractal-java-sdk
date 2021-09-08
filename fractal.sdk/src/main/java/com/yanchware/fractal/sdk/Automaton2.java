package com.yanchware.fractal.sdk;

import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.BlueprintService;
import com.yanchware.fractal.sdk.services.LiveSystemService;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.CreateBlueprintCommandRequest;

import java.net.http.HttpClient;
import java.util.List;

public final class Automaton2 {
    private static Automaton2 instance;

    private static HttpClient httpClient;
    private static SdkConfiguration sdkConfiguration;

    private Automaton2() {
        this.httpClient = null;
        this.sdkConfiguration = null;
    }

    private Automaton2(HttpClient httpClient, SdkConfiguration sdkConfiguration) {
        this.httpClient = httpClient;
        this.sdkConfiguration = sdkConfiguration;
    }

    public static Automaton2 getInstance(HttpClient httpClient, SdkConfiguration sdkConfiguration) {
        if (instance == null) {
            instance = new Automaton2(httpClient, sdkConfiguration);
        }
        return instance;
    }

    public static void instantiate(List<LiveSystem> liveSystems) throws InstantiatorException {
        BlueprintService blueprintService = new BlueprintService(httpClient, sdkConfiguration);
        LiveSystemService liveSystemService = new LiveSystemService(httpClient, sdkConfiguration);

        for (LiveSystem ls : liveSystems) {
            blueprintService.instantiate(CreateBlueprintCommandRequest.fromLiveSystem(ls.getComponents(), ""), "", "");
            //liveSystemService.instantiate(null);
        }
    }
}
