package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.aks;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AzureOutboundIpTest {
  @Test
  public void validationError_when_azureOutboundIpWithNullName() {
    assertThatThrownBy(() -> AzureOutboundIp.builder().withName(null).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("name is empty or it exceeds 80 characters");
  }
  @Test
  public void validationError_when_azureOutboundIpWithNameTooLong() {
    assertThatThrownBy(() -> AzureOutboundIp.builder().withName("fractalfractalfractalfractalfractalfractalfractalfractalfractalfractalfractalfractal").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("name is empty or it exceeds 80 characters");
  }
  @Test
  public void noValidationErrors_when_azureOutboundIpWithValidFields() {
    assertThat(AzureOutboundIp.builder().withName("fractal").build().validate()).isEmpty();
  }
}