package com.yanchware.fractal.sdk.services;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.CreateBlueprintCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos.BlueprintComponentDto;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.net.http.HttpClient;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;

public class BlueprintServiceTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8090);

    private BlueprintService blueprintService;

    @Before
    public void setUp() {
        SdkConfiguration sdkConfiguration = new LocalSdkConfiguration();
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        blueprintService = new BlueprintService(httpClient, sdkConfiguration);
    }

    @Test
    @Ignore
    public void urlPathMatching_when_postRequestToBlueprint() throws InstantiatorException {
        stubFor(post(urlPathMatching("/blueprint/resource-group/fr/fr"))
                .willReturn(aResponse()
                        .withStatus(202)
                        .withHeader("Content-Type", "application/json")));

        blueprintService.createBlueprint(buildBlueprintRequest(), "resource-group/fr:fr");

        verify(postRequestedFor(urlPathEqualTo("/blueprint/resource-group/fr/fr")));
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
                .id("0001")
                .displayName("blueprint basic")
                .description("blueprint basic desc")
                .type("type")
                .version("V0.1")
                .parameters(emptyMap())
                .dependencies(emptySet())
                .links(emptySet())
                .outputFields(emptyMap())
                .build();
    }
}