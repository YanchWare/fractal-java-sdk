package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureNodePool;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureKubernetesServiceIpMaskTest {

  @ParameterizedTest
  @ValueSource(strings = {"255.255.255.256", "10.2.0.0/33", "1.1.1.1"})
  public void exceptionThrown_when_aksCreatedWithServiceIpMaskNotValid(String input) {
    assertThatThrownBy(() -> generateBuilder()
        .withServiceIpMask(input)
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Service IP Mask does not contain a valid ip with mask");
  }

  @ParameterizedTest
  @ValueSource(strings = {"255.255.255.256", "10.2.0.0/33", "1.1.1.1"})
  public void exceptionThrown_when_aksCreatedWithPodIpMaskNotValid(String input) {
    assertThatThrownBy(() -> generateBuilder()
        .withPodIpMask(input)
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Pod IP Mask does not contain a valid ip with mask");
  }

  @ParameterizedTest
  @ValueSource(strings = {"255.255.255.256", "10.2.0.0/33", "1.1.1.1"})
  public void exceptionThrown_when_aksCreatedWithVnetAddressSpaceIpMaskNotValid(String input) {
    assertThatThrownBy(() -> generateBuilder()
        .withVnetAddressSpaceIpMask(input)
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("VNet Address Space IP Mask does not contain a valid ip with mask");
  }

  @ParameterizedTest
  @ValueSource(strings = {"255.255.255.256", "10.2.0.0/33", "1.1.1.1"})
  public void exceptionThrown_when_aksCreatedWithVnetSubnetAddressIpMaskNotValid(String input) {
    assertThatThrownBy(() -> generateBuilder()
        .withVnetSubnetAddressIpMask(input)
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("VNet Subnet Address IP Mask does not contain a valid ip with mask");
  }

  @ParameterizedTest
  @ValueSource(strings = {"10.2.0.0/16", "10.2.0.255/16", "10.2.0.0/32"})
  public void exceptionThrown_when_aksCreatedWithServiceIpMaskValid(String input) {
    assertThat(generateBuilder()
        .withServiceIpMask(input)
        .build()
        .validate()).isEmpty();
  }

  @ParameterizedTest
  @ValueSource(strings = {"10.2.0.0/16", "10.2.0.255/16", "10.2.0.0/32"})
  public void exceptionThrown_when_aksCreatedWithPodIpMaskValid(String input) {
    assertThat(generateBuilder()
        .withPodIpMask(input)
        .build()
        .validate()).isEmpty();
  }

  @ParameterizedTest
  @ValueSource(strings = {"10.2.0.0/16", "10.2.0.255/16", "10.2.0.0/32"})
  public void exceptionThrown_when_aksCreatedWithVnetAddressSpaceIpMaskValid(String input) {
    assertThat(generateBuilder()
        .withVnetAddressSpaceIpMask(input)
        .build()
        .validate()).isEmpty();
  }

  @ParameterizedTest
  @ValueSource(strings = {"10.2.0.0/16", "10.2.0.255/16", "10.2.0.0/32"})
  public void exceptionThrown_when_aksCreatedWithVnetSubnetAddressIpMaskValid(String input) {
    assertThat(generateBuilder()
        .withVnetSubnetAddressIpMask(input)
        .build()
        .validate()).isEmpty();
  }

  private AzureKubernetesService.AzureKubernetesServiceBuilder generateBuilder() {
    return AzureKubernetesService.builder()
        .withId(ComponentId.from("test"))
        .withNodePool(AzureNodePool.builder()
            .withName("azure-node-pool-name")
            .withDiskSizeGb(30)
            .withInitialNodeCount(1)
            .withAutoscalingEnabled(false).build());
  }
}
