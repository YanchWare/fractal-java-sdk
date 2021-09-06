package com.yanchware.fractal.sdk.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.yanchware.fractal.sdk.configuration.ServiceConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands.CreateBlueprintCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos.BlueprintComponentDto;
import com.yanchware.fractal.sdk.utils.LocalServiceConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.http.HttpClient;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;

public class BlueprintServiceTest {

    /*@Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort().dynamicHttpsPort());*/

    private WireMockServer wireMockServer;

    private BlueprintService blueprintService;

    private HttpClient httpClient;

    private ServiceConfiguration serviceConfiguration;

    @Before
    public void setUp() {
        wireMockServer = new WireMockServer(8443);
        httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        serviceConfiguration = new LocalServiceConfiguration();
        blueprintService = new BlueprintService(httpClient, serviceConfiguration);

        wireMockServer.start();
    }

    @After
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void exampleTest() throws InstantiatorException {
        stubFor(post("/blueprint/resource-group/fr/fr")
                .withHeader("Content-Type", containing("xml"))
                .willReturn(ok()
                        .withHeader("Content-Type", "text/xml")
                        .withBody("<response>SUCCESS</response>")));

        blueprintService.instantiate(buildBlueprintRequest(), "fr", "fr");

        verify(postRequestedFor(urlPathEqualTo("http://localhost:8080/blueprint/resource-group/fr/fr"))
                .withRequestBody(matching(".*message-1234.*"))
                .withHeader("Content-Type", equalTo("text/xml")));
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