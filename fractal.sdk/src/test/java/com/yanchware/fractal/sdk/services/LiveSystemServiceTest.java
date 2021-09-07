package com.yanchware.fractal.sdk.services;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.InstantiateLiveSystemCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.EnvironmentDto;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.net.http.HttpClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class LiveSystemServiceTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8090);

    private LiveSystemService liveSystemService;

    @Before
    public void setUp() {
        SdkConfiguration sdkConfiguration = new LocalSdkConfiguration();
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        liveSystemService = new LiveSystemService(httpClient, sdkConfiguration);
    }

    @Test
    public void urlPathMatching_when_postRequestToLiveSystem() throws InstantiatorException {
        stubFor(post(urlPathMatching("/livesystem/resource-group/livesystems"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));

        liveSystemService.instantiate(buildLiveSystemCommand());

        verify(postRequestedFor(urlPathEqualTo("/livesystem/resource-group/livesystems")));
    }

    private InstantiateLiveSystemCommandRequest buildLiveSystemCommand() {
        InstantiateLiveSystemCommandRequest command = InstantiateLiveSystemCommandRequest.builder()
                .liveSystemId("resourceGroupId/prod") //form of "resourceGroupId/name"
                .description("prod")
                .fractalId("resourceGroupId/fractalName:fractalVersion") //form of "resourceGroupId/fractalName:fractalVersion", needs to exist in blueprints, otherwise wont work
                .type("type")
                .provider("azure") //can be an enum, as in ls service it will be converted to enum.
                .environmentDto(getEnvironment())
                .blueprintMap(null) // needs to have at least one component, needs to have same components as blueprint in blueprint svc and all providers of component valid
                .build();
        return command;
    }


    private EnvironmentDto getEnvironment() {
        return EnvironmentDto.builder()
                .id("prod")
                .parentId("parent-id")
                .parentType("folder")
                .displayName("PROD")
                .build();
    }

}