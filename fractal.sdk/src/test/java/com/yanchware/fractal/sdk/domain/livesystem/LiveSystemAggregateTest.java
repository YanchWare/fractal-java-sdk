package com.yanchware.fractal.sdk.domain.livesystem;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentType;
import com.yanchware.fractal.sdk.domain.exceptions.EnvironmentNotFoundException;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.AzureCosmosGremlinDatabase;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.AzureCosmosGremlinDbms;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@WireMockTest
class LiveSystemAggregateTest {

    private LiveSystemAggregate liveSystemAggregate;

    @BeforeEach
    void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
        var httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();

        var sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
        liveSystemAggregate = new LiveSystemsFactory(httpClient, sdkConfiguration, RetryRegistry.ofDefaults())
                .builder()
                .withId(new LiveSystemIdValue(UUID.randomUUID().toString(), UUID.randomUUID().toString()))
                .withStandardProvider(ProviderType.AZURE)
                .withEnvironmentId(new EnvironmentIdValue(
                        EnvironmentType.PERSONAL,
                        UUID.randomUUID(),
                        "test-env"
                ))
                .withComponent(AzureCosmosGremlinDbms.builder()
                        .withId("cosmos-graph-1")
                        .withMaxTotalThroughput(500)
                        .withAzureResourceGroup(
                                AzureResourceGroup.builder()
                                        .withName("MyRg")
                                        .withRegion(AzureRegion.EAST_ASIA)
                                        .build())
                        .withCosmosEntity(AzureCosmosGremlinDatabase.builder()
                                .withId("graph-db-1")
                                .build())
                        .build())
                .build();
    }

    @Test
    void should_throwEnvironmentNotFoundException_when_environmentDoesNotExist() {
        // Arrange
        var environmentId = liveSystemAggregate.getEnvironment().id();

        // Configure WireMock to return a 404 Not Found response
        stubFor(get(urlEqualTo("/environments/" + environmentId))
                .willReturn(notFound()));

        // Act & Assert
        assertThatThrownBy(() -> liveSystemAggregate.instantiate())
                .isInstanceOf(EnvironmentNotFoundException.class)
                .hasMessageContaining(
                        String.format("Unable to instantiate LiveSystem [id: '%s']. Environment [id: '%s'] not found",
                                liveSystemAggregate.getId(),
                                environmentId));

        // Verify that the request was made to the environment service
        verify(getRequestedFor(urlEqualTo("/environments/" + environmentId)));
    }

    @Test
    void should_throwEnvironmentException_when_badRequest() {
        // Arrange
        var environmentId = liveSystemAggregate.getEnvironment().id();

        // Configure WireMock to return a 500 Internal Server Error response
        stubFor(get(urlEqualTo("/environments/" + environmentId))
                .willReturn(badRequest()));

        // Act & Assert
        assertThatThrownBy(() -> liveSystemAggregate.instantiate())
                .isInstanceOf(EnvironmentNotFoundException.class)
                .hasMessageContaining(String.format("Unable to instantiate LiveSystem [id: '%s']. Environment [id: '%s'] not found",
                        liveSystemAggregate.getId(),
                        environmentId));

        // Verify that the request was made to the environment service
        verify(getRequestedFor(urlEqualTo("/environments/" + environmentId)));
    }

}
