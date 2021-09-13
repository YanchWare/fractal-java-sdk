package com.yanchware.fractal.sdk.services;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.InstantiateLiveSystemCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.EnvironmentDto;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class LiveSystemServiceTest {

//    @Rule
//    public WireMockRule wireMockRule = new WireMockRule(8090);

    private LiveSystemService liveSystemService;

    @BeforeEach
    public void setUp() {
        SdkConfiguration sdkConfiguration = new LocalSdkConfiguration();
        HttpClient httpClient = HttpClient.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .build();
        liveSystemService = new LiveSystemService(httpClient, sdkConfiguration);
    }

    @Test
    @Disabled
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
          .resourceGroupId("resourceGroupId")
          .liveSystemName("prod")
          .description("prod")
          .fractalId("resourceGroupId/fractalName:fractalVersion")
//               .environmentDto(getEnvironment())
          .blueprintMap(null)
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