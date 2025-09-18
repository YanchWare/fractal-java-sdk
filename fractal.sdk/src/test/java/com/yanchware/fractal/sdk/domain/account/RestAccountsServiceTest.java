package com.yanchware.fractal.sdk.domain.account;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.yanchware.fractal.sdk.domain.accounts.EntityStatus;
import com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupType;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest
class RestAccountsServiceTest {
    private RestAccountsService accountsService;
    private static final String SHORT_NAME = "rg-personal";
    private static final String DISPLAY_NAME = "Personal Resource Group";

    @BeforeEach
    void setUp(WireMockRuntimeInfo wm) {
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        var sdkConfiguration = new LocalSdkConfiguration(wm.getHttpBaseUrl());
        accountsService = new RestAccountsService(httpClient, sdkConfiguration, RetryRegistry.ofDefaults());
    }

    @Test
    void upsertPersonalResourceGroup_postsToCorrectUrl_andReturnsResponse() throws Exception {
        // Given
        var urlPattern = urlPathMatching("/accounts/resourcegroups/" + SHORT_NAME);
        var accountId = UUID.randomUUID().toString();
        stubFor(post(urlPattern)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                  {"Id":"%s/%s/%s","DisplayName":"%s","Status":"%s"}
                                """.formatted(ResourceGroupType.PERSONAL, accountId, SHORT_NAME, DISPLAY_NAME, EntityStatus.ACTIVE))));
        // When
        var resp = accountsService.upsertPersonalResourceGroup(SHORT_NAME, DISPLAY_NAME);

        // Then
        verify(1, postRequestedFor(urlPattern));
        assertThat(resp).isNotNull();
        assertThat(resp.DisplayName()).isEqualTo(DISPLAY_NAME);
    }

    @Test
    void upsertPersonalResourceGroup_sendsDisplayNameInBody() throws Exception {
        // Given
        var urlPattern = urlPathMatching("/accounts/resourcegroups/" + SHORT_NAME);
        var accountId = UUID.randomUUID().toString();
        stubFor(post(urlPattern)
                .withRequestBody(matchingJsonPath("$.DisplayName", equalTo(DISPLAY_NAME)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"Id":"%s/%s/%s","DisplayName":"%s","Status":"%s"}
                                """.formatted(ResourceGroupType.PERSONAL, accountId, SHORT_NAME, DISPLAY_NAME, EntityStatus.ACTIVE))));

        // When
        var resp = accountsService.upsertPersonalResourceGroup(SHORT_NAME, DISPLAY_NAME);

        // Then
        verify(postRequestedFor(urlPattern).withRequestBody(matchingJsonPath("$.DisplayName", equalTo(DISPLAY_NAME))));
        assertThat(resp).isNotNull();
        assertThat(resp.DisplayName()).isEqualTo(DISPLAY_NAME);
    }

    @Test
    void upsertPersonalResourceGroup_throwsInstantiatorException_on500() {
        // Given
        var urlPattern = urlPathMatching("/accounts/resourcegroups/" + SHORT_NAME);

        stubFor(post(urlPattern)
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\":\"Internal Server Error\"}")));

        // When / Then
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                accountsService.upsertPersonalResourceGroup(SHORT_NAME, DISPLAY_NAME)
        ).isInstanceOf(com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException.class);
    }

    @Test
    void getPersonalResourceGroupByShortName_returnsResponse_on200() throws Exception {
        // Given
        var accountId = java.util.UUID.randomUUID();
        var urlPattern = urlPathMatching("/accounts/resourcegroups/" + SHORT_NAME);

        stubFor(get(urlPattern)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                    {"Id":"%s/%s/%s","DisplayName":"%s","Status":"%s"}
                                """.formatted(ResourceGroupType.PERSONAL, accountId, SHORT_NAME, DISPLAY_NAME, EntityStatus.ACTIVE))));

        // When
        var resp = accountsService.getPersonalResourceGroupByShortName(SHORT_NAME);

        // Then
        verify(1, getRequestedFor(urlPattern));
        assertThat(resp).isNotNull();
        assertThat(resp.DisplayName()).isEqualTo(DISPLAY_NAME);
    }

    @Test
    void getPersonalResourceGroupByShortName_throwsInstantiatorException_on500() {
        // Given
        var urlPattern = urlPathMatching("/accounts/resourcegroups/" + SHORT_NAME);

        stubFor(get(urlPattern)
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\":\"Internal Server Error\"}")));

        // When / Then
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                accountsService.getPersonalResourceGroupByShortName(SHORT_NAME)
        ).isInstanceOf(com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException.class);
        verify(3, getRequestedFor(urlPattern));
    }

    @Test
    void getPersonalResourceGroupByShortName_returnsNull_on404() throws Exception {
        // Given
        var urlPattern = urlPathMatching("/accounts/resourcegroups/" + SHORT_NAME);

        stubFor(get(urlPattern).willReturn(aResponse().withStatus(404)));

        // When
        var resp = accountsService.getPersonalResourceGroupByShortName(SHORT_NAME);

        // Then
        verify(1, getRequestedFor(urlPattern));
        assertThat(resp).isNull();
    }

    @Test
    void upsertOrganizationalResourceGroup_postsToCorrectUrl_andReturnsResponse() throws Exception {
        // Given
        var organizationId = UUID.randomUUID();
        var urlPattern = urlPathMatching("/accounts/organizations/" + organizationId + "/resourcegroups/" + SHORT_NAME);

        stubFor(post(urlPattern)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                  {"Id":"%s/%s/%s","DisplayName":"%s","Status":"%s"}
                                """.formatted(ResourceGroupType.ORGANIZATIONAL, organizationId, SHORT_NAME, DISPLAY_NAME, EntityStatus.ACTIVE))));

        // When
        var resp = accountsService.upsertOrganizationalResourceGroup(organizationId, SHORT_NAME, DISPLAY_NAME);

        // Then
        verify(1, postRequestedFor(urlPattern));
        assertThat(resp).isNotNull();
        assertThat(resp.DisplayName()).isEqualTo(DISPLAY_NAME);
    }

    @Test
    void upsertOrganizationalResourceGroup_sendsDisplayNameInBody() throws Exception {
        // Given
        var organizationId = java.util.UUID.randomUUID();
        var urlPattern = urlPathMatching("/accounts/organizations/" + organizationId + "/resourcegroups/" + SHORT_NAME);

        stubFor(post(urlPattern)
                .withRequestBody(matchingJsonPath("$.DisplayName", equalTo(DISPLAY_NAME)))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {"Id":"%s/%s/%s","DisplayName":"%s","Status":"%s"}
                                """.formatted(ResourceGroupType.ORGANIZATIONAL, organizationId, SHORT_NAME, DISPLAY_NAME, EntityStatus.ACTIVE))));


        // When
        var resp = accountsService.upsertOrganizationalResourceGroup(organizationId, SHORT_NAME, DISPLAY_NAME);

        // Then
        verify(postRequestedFor(urlPattern).withRequestBody(matchingJsonPath("$.DisplayName", equalTo(DISPLAY_NAME))));
        assertThat(resp).isNotNull();
        assertThat(resp.DisplayName()).isEqualTo(DISPLAY_NAME);
    }

    @Test
    void upsertOrganizationalResourceGroup_throwsInstantiatorException_on500() {
        // Given
        var organizationId = java.util.UUID.randomUUID();
        var urlPattern = urlPathMatching("/accounts/organizations/" + organizationId + "/resourcegroups/" + SHORT_NAME);

        stubFor(post(urlPattern)
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\":\"Internal Server Error\"}")));

        // When / Then
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                accountsService.upsertOrganizationalResourceGroup(organizationId, SHORT_NAME, DISPLAY_NAME)
        ).isInstanceOf(com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException.class);
    }

    @Test
    void getOrganizationalResourceGroupByShortName_returnsResponse_on200() throws Exception {
        // Given
        var organizationId = java.util.UUID.randomUUID();
        var urlPattern = urlPathMatching("/accounts/organizations/" + organizationId + "/resourcegroups/" + SHORT_NAME);

        stubFor(get(urlPattern)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                  {"Id":"%s/%s/%s","DisplayName":"%s","Status":"%s"}
                                """.formatted(ResourceGroupType.ORGANIZATIONAL, organizationId, SHORT_NAME, DISPLAY_NAME, EntityStatus.ACTIVE))));

        // When
        var resp = accountsService.getOrganizationalResourceGroupByShortName(organizationId, SHORT_NAME);

        // Then
        verify(1, getRequestedFor(urlPattern));
        assertThat(resp).isNotNull();
        assertThat(resp.DisplayName()).isEqualTo(DISPLAY_NAME);
    }

    @Test
    void getOrganizationalResourceGroupByShortName_throwsInstantiatorException_on500() {
        // Given
        var organizationId = java.util.UUID.randomUUID();
        var urlPattern = urlPathMatching("/accounts/organizations/" + organizationId + "/resourcegroups/" + SHORT_NAME);

        stubFor(get(urlPattern)
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"message\":\"Internal Server Error\"}")));

        // When / Then
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                accountsService.getOrganizationalResourceGroupByShortName(organizationId, SHORT_NAME)
        ).isInstanceOf(com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException.class);

        verify(3, getRequestedFor(urlPattern));
    }

    @Test
    void getOrganizationalResourceGroupByShortName_returnsNull_on404() throws Exception {
        // Given
        var organizationId = java.util.UUID.randomUUID();
        var urlPattern = urlPathMatching("/accounts/organizations/" + organizationId + "/resourcegroups/" + SHORT_NAME);

        stubFor(get(urlPattern).willReturn(aResponse().withStatus(404)));

        // When
        var resp = accountsService.getOrganizationalResourceGroupByShortName(organizationId, SHORT_NAME);

        // Then
        verify(1, getRequestedFor(urlPattern));
        assertThat(resp).isNull();
    }
}
