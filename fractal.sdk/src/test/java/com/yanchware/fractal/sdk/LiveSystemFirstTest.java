package com.yanchware.fractal.sdk;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

import java.net.http.HttpClient;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.TestUtils.getLiveSystemExample;

public class LiveSystemFirstTest {

    //@Rule public WireMockRule wireMockRule = new WireMockRule(8090);

    @BeforeEach
    public void setUp() {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        SdkConfiguration sdkConfiguration = new LocalSdkConfiguration();
        Automaton.initializeAutomaton(sdkConfiguration);
    }

    @Test
    @Disabled
    @SetEnvironmentVariable(key = "CLIENT_ID", value = "xxx")
    @SetEnvironmentVariable(key = "CLIENT_SECRET", value = "xxx")
    public void liveSystemInstantiated_when_AutomatonCalledWithValidLiveSystemInformation() throws InstantiatorException {
        Automaton.instantiate(List.of(getLiveSystemExample()));
    }

}