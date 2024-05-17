package com.yanchware.fractal.sdk.services;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.EnvironmentType;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest
class EnvironmentsServiceTest {

  private EnvironmentsService environmentsService;
  private Environment mockEnvironment;
  
  @BeforeEach
  void setUp(WireMockRuntimeInfo wmRuntimeInfo) {
//    MockitoAnnotations.openMocks(this);
//    wireMockServer = new WireMockServer(8080);
//    wireMockServer.start();
//    WireMock.configureFor("localhost", 8080);

    var httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();
    
    var sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
    environmentsService = new EnvironmentsService(httpClient, sdkConfiguration, RetryRegistry.ofDefaults());
    mockEnvironment = Environment.builder()
        .withName("Test Environment")
        .withShortName("test-env")
        .withEnvironmentType(EnvironmentType.PERSONAL)
        .withResourceGroup(UUID.randomUUID())
        .withOwnerId(UUID.randomUUID())
        .build();
//    mockEnvironment = new Environment();
//    // Initialize the mockEnvironment fields as required
//    when(mockSdkConfiguration.getEnvironmentsEndpoint()).thenReturn("http://localhost:8080/environments");
  }
  
  @Test
  void createOrUpdateEnvironment_when_environmentExists_shouldUpdateEnvironment() throws Exception {
    // Given
    var urlPattern = urlPathMatching("/environments/.*/.*/.*");
    stubFor(get(urlPattern)
        .willReturn(aResponse()
            .withStatus(200)
            .withBody("{}")));

    // When
    environmentsService.createOrUpdateEnvironment(mockEnvironment);

    // Then
    verify(1, getRequestedFor(urlPattern));
  }

}