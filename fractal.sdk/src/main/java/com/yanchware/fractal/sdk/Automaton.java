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

    private Automaton() {
    }

    public static void instantiate(List<LiveSystem> liveSystems) throws InstantiatorException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        SdkConfiguration serviceConfiguration = new EnvVarSdkConfiguration();
        BlueprintService blueprintService = new BlueprintService(httpClient, serviceConfiguration);
        LiveSystemService liveSystemService = new LiveSystemService(httpClient, serviceConfiguration);

        for(LiveSystem ls : liveSystems) {
            blueprintService.instantiate(CreateBlueprintCommandRequest.fromLiveSystem(ls.getComponents()), "", "");
            //liveSystemService.instantiate(null);
        }
    }

}
