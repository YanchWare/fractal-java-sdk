package com.yanchware.fractal.sdk.services;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.EnvironmentType;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import com.yanchware.fractal.sdk.utils.StringHandler;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest
class EnvironmentsServiceTest {

  private EnvironmentsService environmentsService;
  private Environment mockEnvironment;

  @BeforeEach
  void setUp(WireMockRuntimeInfo wmRuntimeInfo) {    
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

  }

  @Test
  void fetchEnvironment_should_ReturnEnvironmentIdDto() throws Exception {
    // Given
    var urlPattern = urlPathMatching("/environments/.*/.*/.*");

    String resourceGroupsJson = serialize(mockEnvironment.getResourceGroups());
    String parametersJson = serialize(mockEnvironment.getParameters());


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
    var response = environmentsService.fetchEnvironment(mockEnvironment);

    // Then
    verify(1, getRequestedFor(urlPattern));

    assertThat(response).isNotNull();
    assertThat(response.getId().getType().toString()).isEqualTo(mockEnvironment.getEnvironmentType().toString());
    assertThat(response.getId().getOwnerId()).isEqualTo(mockEnvironment.getOwnerId());
    assertThat(response.getId().getShortName()).isEqualTo(mockEnvironment.getShortName());
    assertThat(response.getName()).isEqualTo(mockEnvironment.getName());
    assertThat(response.getResourceGroups()).containsExactlyElementsOf(mockEnvironment.getResourceGroups());
    assertThat(response.getParameters()).isEqualTo(mockEnvironment.getParameters());

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
    environmentsService.createOrUpdateEnvironment(mockEnvironment);

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
    environmentsService.createOrUpdateEnvironment(mockEnvironment);

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
        mockEnvironment.getParameters());

    stubFor(put(urlPattern)
        .willReturn(aResponse()
            .withStatus(200)
            .withBody(jsonResponse)
            .withHeader("Content-Type", "application/json")));

    // When
    var response = environmentsService.updateEnvironment(mockEnvironment);

    // Then
    verify(1, putRequestedFor(urlPattern));
    assertThat(response).isNotNull();
    assertThat(response.getId().getType().toString()).isEqualTo(mockEnvironment.getEnvironmentType().toString());
    assertThat(response.getId().getOwnerId()).isEqualTo(mockEnvironment.getOwnerId());
    assertThat(response.getId().getShortName()).isEqualTo(mockEnvironment.getShortName());

  }

  @Test
  void fetchCurrentInitialization_should_ReturnValidInitializationRunResponse() throws Exception {
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
    var response = environmentsService.fetchCurrentInitialization(mockEnvironment);

    // Then
    verify(1, getRequestedFor(urlPattern));

    assertThat(response).isNotNull();
    assertThat(response.getEnvironmentId().getType().toString()).isEqualTo(mockEnvironment.getEnvironmentType().toString());
    assertThat(response.getEnvironmentId().getOwnerId()).isEqualTo(mockEnvironment.getOwnerId());
    assertThat(response.getEnvironmentId().getShortName()).isEqualTo(mockEnvironment.getShortName());
    assertThat(response.getSteps()).isNotNull();
    assertThat(response.getSteps().size()).isGreaterThan(1);
  }

  @Test
  void InitializeSubscription_should_HandleInitializationInProgress() throws Exception {
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
    environmentsService.InitializeSubscription(mockEnvironment, Duration.ofSeconds(2));

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