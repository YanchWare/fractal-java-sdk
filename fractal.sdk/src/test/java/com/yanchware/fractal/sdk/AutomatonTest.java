package com.yanchware.fractal.sdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.yanchware.fractal.sdk.domain.accounts.EntityStatus;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.EnvironmentResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemIdValue;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupType;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@WireMockTest
public class AutomatonTest extends TestWithFixture {
    private static final String SHORT_NAME = "rg-sdk";
    private static final String DISPLAY_NAME = "SDK RG";
    
    @Test
    public void success_when_deleteLiveSystem(WireMockRuntimeInfo wmRuntimeInfo) throws URISyntaxException, InstantiatorException, JsonProcessingException {
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        var sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
        Automaton.initializeAutomaton(httpClient, sdkConfiguration);
        var sut = Automaton.getInstance();

        var liveSystemIds = aListOf(LiveSystemIdValue.class);
        var environmentResponse = a(EnvironmentResponse.class);
        var environmentId = environmentResponse.id();

        stubFor(get(urlPathMatching(String.format("/environments/%s/%s/%s", environmentId.type(), environmentId.ownerId(), environmentId.shortName())))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                                .writeValueAsString(environmentResponse))));

        stubFor(delete(urlPathMatching("/livesystems/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));

        sut.delete(EnvironmentIdValue.fromDto(environmentResponse.id()), liveSystemIds);

        for (var liveSystemId : liveSystemIds) {
            verify(deleteRequestedFor(urlPathEqualTo(String.format("/livesystems/%s", liveSystemId))));
        }
    }

    @Test
    public void instantiateAccount_createsPersonalResourceGroup_whenNotExists(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
        var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        var sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
        Automaton.initializeAutomaton(httpClient, sdkConfiguration);
        var sut = Automaton.getInstance();

        var getUrl = urlPathMatching("/accounts/accounts/resourcegroups/" + SHORT_NAME);
        stubFor(get(getUrl).willReturn(aResponse().withStatus(404)));

        stubFor(post(getUrl)
                .withRequestBody(matchingJsonPath("$.DisplayName", equalTo(DISPLAY_NAME)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                  {"Id":"%s/%s/%s","DisplayName":"%s","Status":"%s"}
                                """.formatted(ResourceGroupType.PERSONAL, UUID.randomUUID(), SHORT_NAME, DISPLAY_NAME, EntityStatus.ACTIVE))));

        var accountAggregate = sut.getAccountBuilder()
                .withResourceGroup(SHORT_NAME, DISPLAY_NAME)
                .build();

        sut.instantiate(accountAggregate);

        verify(1, getRequestedFor(getUrl));
        verify(1, postRequestedFor(getUrl).withRequestBody(matchingJsonPath("$.DisplayName", equalTo(DISPLAY_NAME))));
    }

    @Test
    public void instantiateOrganization_createsOrganizationalResourceGroup_whenNotExists(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
        var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        var sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
        Automaton.initializeAutomaton(httpClient, sdkConfiguration);
        var sut = Automaton.getInstance();

        var orgId = java.util.UUID.randomUUID();

        var path = String.format("/accounts/organizations/%s/resourcegroups/%s", orgId, SHORT_NAME);
        var getUrl = urlPathMatching(path);
        stubFor(get(getUrl).willReturn(aResponse().withStatus(404)));

        stubFor(post(getUrl)
                .withRequestBody(matchingJsonPath("$.DisplayName", equalTo(DISPLAY_NAME)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"Id":"%s/%s/%s","DisplayName":"%s","Status":"%s"}
                                """.formatted(ResourceGroupType.ORGANIZATIONAL, UUID.randomUUID(), SHORT_NAME, DISPLAY_NAME, EntityStatus.ACTIVE))));

        var orgAggregate = sut.getOrganizationBuilder()
                .withResourceGroup(orgId, SHORT_NAME, DISPLAY_NAME)
                .build();

        sut.instantiate(orgAggregate);

        verify(1, getRequestedFor(getUrl));
        verify(1, postRequestedFor(getUrl).withRequestBody(matchingJsonPath("$.DisplayName", equalTo(DISPLAY_NAME))));
    }

    @Test
    public void instantiateAccount_skipsWhenExists(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
        var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        var sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
        Automaton.initializeAutomaton(httpClient, sdkConfiguration);
        var sut = Automaton.getInstance();

        var getUrl = urlPathMatching("/accounts/accounts/resourcegroups/" + SHORT_NAME);

        // Existing RG → GET returns 200, so no POST should be issued
        stubFor(get(getUrl)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"Id":"%s/%s/%s","DisplayName":"%s","Status":"%s"}
                                """.formatted(ResourceGroupType.PERSONAL, UUID.randomUUID(), SHORT_NAME, DISPLAY_NAME, EntityStatus.ACTIVE))));


        var accountAggregate = sut.getAccountBuilder()
                .withResourceGroup(SHORT_NAME, DISPLAY_NAME)
                .build();

        sut.instantiate(accountAggregate);

        verify(1, getRequestedFor(getUrl));
        verify(0, postRequestedFor(getUrl));
    }

    @Test
    public void instantiateOrganization_skipsWhenExists(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
        var httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
        var sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
        Automaton.initializeAutomaton(httpClient, sdkConfiguration);
        var sut = Automaton.getInstance();

        var orgId = java.util.UUID.randomUUID();
        var path = String.format("/accounts/organizations/%s/resourcegroups/%s", orgId, SHORT_NAME);
        var getUrl = urlPathMatching(path);

        // Existing org RG → GET returns 200, so no POST should be issued
        stubFor(get(getUrl)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"Id":"%s/%s/%s","DisplayName":"%s","Status":"%s"}
                                """.formatted(ResourceGroupType.ORGANIZATIONAL, UUID.randomUUID(), SHORT_NAME, DISPLAY_NAME, EntityStatus.ACTIVE))));


        var orgAggregate = sut.getOrganizationBuilder()
                .withResourceGroup(orgId, SHORT_NAME, DISPLAY_NAME)
                .build();

        sut.instantiate(orgAggregate);

        verify(1, getRequestedFor(getUrl));
        verify(0, postRequestedFor(getUrl));
    }

    @Test
    void automaton_provides_account_and_organization_builders() throws Exception {
        var httpClient = HttpClient.newHttpClient();
        var sdkConfig = new LocalSdkConfiguration("http://localhost:8080");
        Automaton.initializeAutomaton(httpClient, sdkConfig);

        var automaton = Automaton.getInstance();

        assertThat(automaton.getAccountBuilder()).isNotNull();
        assertThat(automaton.getAccountBuilder().withResourceGroup(SHORT_NAME, DISPLAY_NAME).build()).isNotNull();

        assertThat(automaton.getOrganizationBuilder()).isNotNull();
        assertThat(automaton.getOrganizationBuilder()
                .withResourceGroup(UUID.randomUUID(), SHORT_NAME, DISPLAY_NAME)
                .build()).isNotNull();
    }
}
