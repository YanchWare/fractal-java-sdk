package com.yanchware.fractal.sdk.services;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.CreateBlueprintCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos.BlueprintComponentDto;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;

@WireMockTest
public class BlueprintServiceTest {

    @Test
    public void urlPathMatching_when_postRequestToBlueprint(WireMockRuntimeInfo wmRuntimeInfo) throws InstantiatorException {
        HttpClient httpClient = HttpClient.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .build();
        SdkConfiguration sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
        var blueprintService = new BlueprintService(httpClient, sdkConfiguration, RetryRegistry.ofDefaults());

        stubFor(post(urlPathMatching("/blueprints/resource-group/fr/fr"))
                .willReturn(aResponse()
                        .withStatus(202)
                        .withHeader("Content-Type", "application/json")));

        blueprintService.createBlueprint(buildBlueprintRequest(), "resource-group/fr:fr");

        verify(postRequestedFor(urlPathEqualTo("/blueprints/resource-group/fr/fr")));
    }

    private CreateBlueprintCommandRequest buildBlueprintRequest() {
        CreateBlueprintCommandRequest commandRequest = new CreateBlueprintCommandRequest();
        commandRequest.setDescription("desc");
        commandRequest.setPrivate(true);
        commandRequest.setComponents(List.of(buildBlueprintComponent()));
        return commandRequest;
    }

    private BlueprintComponentDto buildBlueprintComponent() {
        return BlueprintComponentDto.builder()
                .withId("0001")
                .withDisplayName("blueprint basic")
                .withDescription("blueprint basic desc")
                .withType("type")
                .withVersion("V0.1")
                .withParameters(emptyMap())
                .withDependencies(emptySet())
                .withLinks(emptySet())
                .withOutputFields(emptySet())
                .build();
    }
}