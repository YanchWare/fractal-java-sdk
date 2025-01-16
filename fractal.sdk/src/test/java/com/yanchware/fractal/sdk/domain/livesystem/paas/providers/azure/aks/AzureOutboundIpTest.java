package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion.WEST_EUROPE;
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
    assertThatThrownBy(() -> AzureOutboundIp.builder().withName(
      "fractalfractalfractalfractalfractalfractalfractalfractalfractalfractalfractalfractal").build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("name is empty or it exceeds 80 characters");
  }

  @Test
  public void noValidationErrors_when_azureOutboundIpWithValidFields() {
    assertThat(AzureOutboundIp.builder().withName("fractal").build().validate()).isEmpty();
  }

  @Test
  public void azureOutboundIpHasAllFields() {
    AzureOutboundIp outboundIp = AzureOutboundIp
      .builder()
      .withName("fractal")
      .withAzureResourceGroup(AzureResourceGroup
        .builder()
        .withName("name")
        .withRegion(WEST_EUROPE)
        .build())
      .build();
    assertThat(outboundIp.getName()).isEqualTo("fractal");
    assertThat(outboundIp.getAzureResourceGroup()).extracting("name", "region").containsExactly("name", WEST_EUROPE);
  }
}