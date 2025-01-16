package com.yanchware.fractal.sdk.domain.livesystem.caas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceManagementTest {

  private ResourceManagement.ResourceManagementBuilder builder;

  @BeforeEach
  void setUp() {
    builder = ResourceManagement.builder();
  }

  @Test
  void resourceRequestsAreCorrectlyAssigned_when_withRequestsIsCalled() {
    ContainerResources requests = new ContainerResources();
    requests.setCpu("100m");
    requests.setMemory("256Mi");

    ResourceManagement resourceManagement = builder.withRequests(requests).build();

    assertThat(resourceManagement.getRequests())
      .as("Resource requests should be correctly assigned")
      .isNotNull()
      .usingRecursiveComparison()
      .isEqualTo(requests);
  }

  @Test
  void resourceLimitsAreCorrectlyAssigned_when_withLimitsIsCalled() {
    ContainerResources limits = new ContainerResources();
    limits.setCpu("500m");
    limits.setMemory("1Gi");

    ResourceManagement resourceManagement = builder.withLimits(limits).build();

    assertThat(resourceManagement.getLimits())
      .as("Resource limits should be correctly assigned")
      .isNotNull()
      .usingRecursiveComparison()
      .isEqualTo(limits);
  }
}