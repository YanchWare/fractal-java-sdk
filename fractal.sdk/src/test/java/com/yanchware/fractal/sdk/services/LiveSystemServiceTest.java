package com.yanchware.fractal.sdk.services;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
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
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest
public class LiveSystemServiceTest {

    @Test
    public void urlPathMatching_when_postRequestToLiveSystem(WireMockRuntimeInfo wmRuntimeInfo) throws InstantiatorException {
        HttpClient httpClient = HttpClient.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .build();
        SdkConfiguration sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
        var liveSystemService = new LiveSystemService(httpClient, sdkConfiguration, RetryRegistry.ofDefaults());

        stubFor(post(urlPathMatching("/livesystems/"))
          .withRequestBody(equalToJson("{" +
            "\"fractalId\" :\"resourceGroupId/fractalName:fractalVersion\"," +
            "\"description\" : \"prod\"," +
            "\"blueprintMap\" : {" +
              "\"graph-db-1\" : {" +
                "\"id\" : \"graph-db-1\"," +
                "\"displayName\" : \"null\"," +
                "\"description\" :\"Storage.PaaS.CosmosDbGremlinDatabase generated via SDK\"," +
                "\"type\" :\"Storage.PaaS.CosmosDbGremlinDatabase\"," +
                "\"version\" :\"1.0\"," +
                "\"parameters\" : {" +
                  "\"azureResourceGroup\" : {" +
                    "\"name\" : \"MyRg\"," +
                    "\"region\" : \"eastasia\"" +
                  "}," +
                  "\"throughput\" : 0," +
                  "\"TYPE\" :\"Storage.PaaS.CosmosDbGremlinDatabase\"," +
                  "\"entityName\" : \"Gremlin Database\"," +
                  "\"maxThroughput\" : 0" +
                "}," +
                "\"dependencies\" : [\"cosmos-graph-1\"]," +
                "\"links\" : [ ]," +
                "\"status\" :\"Instantiating\"," +
                "\"outputFields\" : { }," +
                "\"lastUpdated\" : \"${json-unit.any-string}\"," +
                "\"provider\" : \"AZURE\"" +
              "}," +
              "\"cosmos-graph-1\" : {" +
                "\"id\" : \"cosmos-graph-1\"," +
                "\"displayName\" : \"null\"," +
                "\"description\" : \"Storage.PaaS.CosmosDbAccount generated via SDK\"," +
                "\"type\" : \"Storage.PaaS.CosmosDbAccount\"," +
                "\"version\" : \"1.0\"," +
                "\"parameters\" : {" +
                  "\"azureResourceGroup\" : {" +
                    "\"name\" : \"MyRg\"," +
                    "\"region\" : \"eastasia\"" +
                  "}," +
                  "\"maxTotalThroughput\" : 500," +
                  "\"TYPE\" :\"Storage.PaaS.CosmosDbAccount\"" +
                "}," +
               "\"dependencies\" : [ ]," +
               "\"links\" : [ ]," +
               "\"status\": \"Instantiating\"," +
               "\"outputFields\" : { }," +
               "\"lastUpdated\" : \"${json-unit.any-string}\"," +
               "\"provider\" : \"AZURE\"" +
              "}" +
            "}," +
            "\"environment\" : {" +
              "\"id\" : \"prod\"," +
              "\"displayName\" : \"parent-id\"," +
              "\"parentId\" : \"folder\"," +
              "\"parentType\" : \"PROD\"" +
          "}}", true, false))
          .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")));

        liveSystemService.instantiate(buildLiveSystemCommand());

        verify(postRequestedFor(urlPathEqualTo("/livesystems/")));
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