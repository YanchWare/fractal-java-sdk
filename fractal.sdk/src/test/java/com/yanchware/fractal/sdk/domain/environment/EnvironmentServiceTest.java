package com.yanchware.fractal.sdk.domain.environment;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.yanchware.fractal.sdk.domain.environment.azure.AzureCloudAgent;
import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import com.yanchware.fractal.sdk.utils.StringHandler;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest
class EnvironmentServiceTest {
  private EnvironmentAggregate mockEnvironment;
  private EnvironmentService environmentService;

  @BeforeEach
  void setUp(WireMockRuntimeInfo wmRuntimeInfo) {    
    var httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();

    var sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
      EnvironmentsFactory environmentsFactory = new EnvironmentsFactory(httpClient, sdkConfiguration, RetryRegistry.ofDefaults());
    mockEnvironment = environmentsFactory.builder()
        .withName("Test Environment")
        .withShortName("test-env")
        .withEnvironmentType(EnvironmentType.PERSONAL)
        .withResourceGroup(UUID.randomUUID())
        .withOwnerId(UUID.randomUUID())
        .build();
    environmentService = new EnvironmentService(httpClient, sdkConfiguration, RetryRegistry.ofDefaults());
  }

  @Test
  void fetchEnvironment_should_ReturnEnvironmentIdDto() throws Exception {
    // Given
    var urlPattern = urlPathMatching("/environments/.*/.*/.*");

    String resourceGroupsJson = serialize(mockEnvironment.getResourceGroups());
    String parametersJson = serialize(mockEnvironment.toDto().parameters());

    var jsonResponse = String.format("""
            {
                "id": {
                    "type": "%s",
                    "ownerId": "%s",
                    "shortName": "%s"
                },
                "name": "%s",
                "resourceGroups": %s,
                "parameters": %s,
                "status": "Active",
                "createdAt": "2024-05-20T21:30:27.045813+00:00",
                "createdBy": "tests@yanchware.com",
                "updatedAt": "2024-05-20T21:30:27.045813+00:00",
                "updatedBy": "tests@yanchware.com"
            }
            """, mockEnvironment.getEnvironmentType(),
        mockEnvironment.getOwnerId(),
        mockEnvironment.getShortName(),
        mockEnvironment.getName(),
        resourceGroupsJson,
        parametersJson);

    stubFor(get(urlPattern)
        .willReturn(aResponse()
            .withStatus(200)
            .withBody(jsonResponse)
            .withHeader("Content-Type", "application/json")));

    // When
    var response = environmentService.fetch(mockEnvironment.getId());

    // Then
    verify(1, getRequestedFor(urlPattern));

    assertThat(response).isNotNull();
    assertThat(response.id().type().toString()).isEqualTo(mockEnvironment.getEnvironmentType().toString());
    assertThat(response.id().ownerId()).isEqualTo(mockEnvironment.getOwnerId());
    assertThat(response.id().shortName()).isEqualTo(mockEnvironment.getShortName());
    assertThat(response.name()).isEqualTo(mockEnvironment.getName());
    assertThat(response.resourceGroups()).containsExactlyElementsOf(mockEnvironment.getResourceGroups());
    assertThat(response.parameters()).isEqualTo(mockEnvironment.toDto().parameters());

  }

  @Test
  void createOrUpdateEnvironment_whenEnvironmentExists_shouldUpdateEnvironment() throws Exception {
    // Given
    var urlPattern = urlPathMatching("/environments/.*/.*/.*");

    var jsonResponse = String.format("""
            {
                "id": {
                    "type": "%s",
                    "ownerId": "%s",
                    "shortName": "%s"
                },
                "name": "%s",
                "resourceGroups": [
                    "ebc34d36-532c-45b4-aed9-94c2596d3840"
                ],
                "parameters": null,
                "status": "Active",
                "createdAt": "2024-05-20T21:30:27.045813+00:00",
                "createdBy": "tests@yanchware.com",
                "updatedAt": "2024-05-20T21:30:27.045813+00:00",
                "updatedBy": "tests@yanchware.com"
            }
            """, mockEnvironment.getEnvironmentType(),
        mockEnvironment.getOwnerId(),
        mockEnvironment.getShortName(),
        mockEnvironment.getName());

    stubFor(get(urlPattern)
        .willReturn(aResponse()
            .withStatus(200)
            .withBody(jsonResponse)
            .withHeader("Content-Type", "application/json")));

    stubFor(put(urlPattern)
        .willReturn(aResponse()
            .withStatus(200)));

    // When
    environmentService.update(
            mockEnvironment.getId(),
            mockEnvironment.getName(),
            mockEnvironment.getResourceGroups(),
            mockEnvironment.toDto().parameters());

    // Then
    verify(1, putRequestedFor(urlPattern));
    verify(0, postRequestedFor(urlPattern));
  }

  @Test
  void createOrUpdateEnvironment_whenEnvironmentDoesNotExist_shouldCreateEnvironment() throws Exception {
    // Given
    var urlPattern = urlPathMatching("/environments/.*/.*/.*");

    stubFor(get(urlPattern)
        .willReturn(aResponse()
            .withStatus(404)));

    // Add stub for POST request
    stubFor(post(urlPattern)
        .willReturn(aResponse()
            .withStatus(201)));

    // When
    environmentService.create(
            mockEnvironment.getId(),
            mockEnvironment.getName(),
            mockEnvironment.getResourceGroups(),
            mockEnvironment.toDto().parameters());


    // Then
    verify(0, putRequestedFor(urlPattern));
    verify(1, postRequestedFor(urlPattern));
  }

  @Test
  void updateEnvironment_shouldExecutePutRequest() throws Exception {
    // Given
    var urlPattern = urlPathMatching("/environments/.*/.*/.*");

    var jsonResponse = String.format("""
            {
                "id": {
                    "type": "%s",
                    "ownerId": "%s",
                    "shortName": "%s"
                },
                "name": "%s",
                "resourceGroups": [
                    "ebc34d36-532c-45b4-aed9-94c2596d3840"
                ],
                "parameters": null,
                "status": "Active",
                "createdAt": "2024-05-20T21:30:27.045813+00:00",
                "createdBy": "tests@yanchware.com",
                "updatedAt": "2024-05-20T21:30:27.045813+00:00",
                "updatedBy": "tests@yanchware.com"
            }
            """, mockEnvironment.getEnvironmentType(),
        mockEnvironment.getOwnerId(),
        mockEnvironment.getShortName(),
        mockEnvironment.toDto().parameters());

    stubFor(put(urlPattern)
        .willReturn(aResponse()
            .withStatus(200)
            .withBody(jsonResponse)
            .withHeader("Content-Type", "application/json")));

    // When
    var response = environmentService.update(
            mockEnvironment.getId(),
            mockEnvironment.getName(),
            mockEnvironment.getResourceGroups(),
            mockEnvironment.toDto().parameters());


    // Then
    verify(1, putRequestedFor(urlPattern));
    assertThat(response).isNotNull();
    assertThat(response.id().type().toString()).isEqualTo(mockEnvironment.getEnvironmentType().toString());
    assertThat(response.id().ownerId()).isEqualTo(mockEnvironment.getOwnerId());
    assertThat(response.id().shortName()).isEqualTo(mockEnvironment.getShortName());

  }

  @Test
  void fetchCurrentInitialization_should_ReturnValidAzureInitializationRunResponse() throws Exception {
    // Given
    var urlPattern = urlPathMatching("/environments/.*/.*/.*/initializer/azure/status");

    var inputStream = getClass().getClassLoader()
        .getResourceAsStream("test-resources/fetchCurrentInitializationInProgressResponse.json");

    assertThat(inputStream).isNotNull();

    var fetchCurrentInitializationResponse = StringHandler.getStringFromInputStream(inputStream);

    // Replace the placeholders
    fetchCurrentInitializationResponse = replacePlaceholders(fetchCurrentInitializationResponse, geEnvironmentIdPlaceholders());

    stubFor(get(urlPattern)
        .willReturn(aResponse()
            .withStatus(200)
            .withBody(fetchCurrentInitializationResponse)
            .withHeader("Content-Type", "application/json")));

    // When
    var response = environmentService.fetchCurrentAzureInitialization(mockEnvironment.getId());

    // Then
    verify(1, getRequestedFor(urlPattern));

    assertThat(response).isNotNull();
    assertThat(response.environmentId().type().toString()).isEqualTo(mockEnvironment.getEnvironmentType().toString());
    assertThat(response.environmentId().ownerId()).isEqualTo(mockEnvironment.getOwnerId());
    assertThat(response.environmentId().shortName()).isEqualTo(mockEnvironment.getShortName());
    assertThat(response.steps()).isNotNull();
    assertThat(response.steps().size()).isGreaterThan(1);
  }

  @Test
  void initializeSubscription_should_HandleInitializationInProgress() throws Exception {
    // Given
    var urlPatternStatus = urlPathMatching("/environments/.*/.*/.*/initializer/azure/status");
    var placeholders = geEnvironmentIdPlaceholders();
    
    var inputStreamInProgress = getClass().getClassLoader()
        .getResourceAsStream("test-resources/fetchCurrentInitializationInProgressResponse.json");
    assertThat(inputStreamInProgress).isNotNull();

    var fetchCurrentInitializationInProgressResponse = StringHandler.getStringFromInputStream(inputStreamInProgress);
    fetchCurrentInitializationInProgressResponse = replacePlaceholders(fetchCurrentInitializationInProgressResponse, placeholders);

    // Mocking fetchCurrentInitialization response for "Completed" status
    var inputStreamCompleted = getClass().getClassLoader()
        .getResourceAsStream("test-resources/fetchCurrentInitializationCompletedResponse.json");
    assertThat(inputStreamCompleted).isNotNull();

    var fetchCurrentInitializationCompletedResponse = StringHandler.getStringFromInputStream(inputStreamCompleted);
    fetchCurrentInitializationCompletedResponse = replacePlaceholders(fetchCurrentInitializationCompletedResponse, placeholders);

    stubFor(get(urlPatternStatus)
        .inScenario("Initialization Scenario")
        .whenScenarioStateIs(Scenario.STARTED)
        .willReturn(aResponse()
            .withStatus(200)
            .withBody(fetchCurrentInitializationInProgressResponse)
            .withHeader("Content-Type", "application/json"))
        .willSetStateTo("InProgress1"));

    stubFor(get(urlPatternStatus)
        .inScenario("Initialization Scenario")
        .whenScenarioStateIs("InProgress1")
        .willReturn(aResponse()
            .withStatus(200)
            .withBody(fetchCurrentInitializationInProgressResponse)
            .withHeader("Content-Type", "application/json"))
        .willSetStateTo("InProgress2"));

    stubFor(get(urlPatternStatus)
        .inScenario("Initialization Scenario")
        .whenScenarioStateIs("InProgress2")
        .willReturn(aResponse()
            .withStatus(200)
            .withBody(fetchCurrentInitializationCompletedResponse)
            .withHeader("Content-Type", "application/json"))
        .willSetStateTo("Completed"));

    // When
    CloudAgentEntity cloudAgent = new AzureCloudAgent(
            mockEnvironment.getId(),
            environmentService,
            AzureRegion.WEST_EUROPE,
            UUID.randomUUID(),
            UUID.randomUUID(),
            Collections.emptyMap());
    cloudAgent.initialize();

    // Then
    verify(3, getRequestedFor(urlPatternStatus)); 
  }

  private Map<String, String> geEnvironmentIdPlaceholders() {
    return Map.of(
        "$ENVIRONMENT_TYPE", mockEnvironment.getEnvironmentType().toString(),
        "$ENVIRONMENT_OWNER_ID", mockEnvironment.getOwnerId().toString(),
        "$ENVIRONMENT_SHORT_NAME", mockEnvironment.getShortName()
    );
  }

  private static String replacePlaceholders(String json, Map<String, String> placeholders) {
    for (Map.Entry<String, String> entry : placeholders.entrySet()) {
      json = json.replace(entry.getKey(), entry.getValue());
    }
    return json;
  }
}