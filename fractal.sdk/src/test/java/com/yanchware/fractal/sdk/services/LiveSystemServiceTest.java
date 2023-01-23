package com.yanchware.fractal.sdk.services;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosGremlinDatabase;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosGremlinDbms;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.InstantiateLiveSystemCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.EnvironmentDto;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentDto;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class LiveSystemServiceTest {
    
    private LiveSystemService liveSystemService;

    @BeforeEach
    public void setUp() {
        SdkConfiguration sdkConfiguration = new LocalSdkConfiguration();
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        liveSystemService = new LiveSystemService(httpClient, sdkConfiguration, RetryRegistry.ofDefaults());
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
        return InstantiateLiveSystemCommandRequest.builder()
                .description("prod")
                .fractalId("resourceGroupId/fractalName:fractalVersion")
                .environment(getEnvironment())
                .blueprintMap(LiveSystemComponentDto.fromLiveSystemComponents(List.of(AzureCosmosGremlinDbms.builder()
                    .withId("cosmos-graph-1")
                    .withMaxTotalThroughput(500)
                    .withAzureResourceGroup(
                        AzureResourceGroup.builder()
                            .withName("MyRg")
                            .withRegion(AzureRegion.ASIA_EAST)
                            .build())
                    .withCosmosEntity(AzureCosmosGremlinDatabase.builder()
                      .withId("graph-db-1")
                      .build())
                    .build())))
                .build();
    }


    private EnvironmentDto getEnvironment() {
        return new EnvironmentDto(
                "prod",
                "parent-id",
                "folder",
                "PROD");
    }

}