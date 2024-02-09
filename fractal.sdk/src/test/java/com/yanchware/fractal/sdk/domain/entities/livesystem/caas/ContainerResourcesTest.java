package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContainerResourcesTest {

  @Test
  void cpuValueIsSetCorrectly_when_withCpuIsCalledWithValidInput() {
    String cpu = "250m"; // Quarter of a CPU core
    ContainerResources resources = ContainerResources.builder().withCpu(cpu).build();
    
    assertThat(resources.getCpu())
        .as("CPU should be set correctly when withCpu is called with valid input")
        .isEqualTo(cpu);
  }

  @Test
  void memoryValueIsSetCorrectly_when_withMemoryIsCalledWithValidInput() {
    String memory = "64Mi"; // 64 Mebibytes
    ContainerResources resources = ContainerResources.builder().withMemory(memory).build();
    
    assertThat(resources.getMemory())
        .as("Memory should be set correctly when withMemory is called with valid input")
        .isEqualTo(memory);
  }

  @Test
  void noErrorsReturned_when_ValidateCalledWithValidCpuAndMemory() {
    ContainerResources resources = ContainerResources.builder().withCpu("500m").withMemory("1Gi").build();
    
    assertThat(resources.validate())
        .as("No validation errors should be returned with valid CPU and memory")
        .isEmpty();
  }

  @Test
  void exceptionThrown_when_ValidateCalledWithInvalidCpu() {
    assertThatThrownBy(() -> ContainerResources.builder().withCpu("-1").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("CPU quantity is not in a valid format");
  }

  @Test
  void exceptionThrown_when_ValidateCalledWithToLowCpu() {
    assertThatThrownBy(() -> ContainerResources.builder().withCpu("0.0001").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("CPU quantity must be at least 0.001 CPU (1m) for decimal values");
  }

  @Test
  void exceptionThrown_when_ValidateCalledWithInvalidMemorySuffix() {
    assertThatThrownBy(() -> ContainerResources.builder().withMemory("500m").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("The 'm' suffix is not valid for memory quantities.");
  }

  @Test
  void exceptionThrown_when_ValidateCalledWithInvalidMemoryInputFormat() {
    assertThatThrownBy(() -> ContainerResources.builder().withMemory("-500").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Memory quantity is not in a valid format");
  }
}