package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureNodePool;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureMachineType.STANDARD_B2S;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureRegion.EUROPE_WEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureKubernetesServiceIpRangeTest {

  @ParameterizedTest
  @ValueSource(strings = {"255.255.255.256", "10.2.0.0/33", "1.1.1.1"})
  public void exceptionThrown_when_aksCreatedWithServiceIpRangeNotValid(String input) {
    assertThatThrownBy(() -> generateBuilder()
        .withServiceIpRange(input)
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Service IP Range does not contain a valid ip with mask");
  }

  @ParameterizedTest
  @ValueSource(strings = {"255.255.255.256", "10.2.0.0/33", "1.1.1.1"})
  public void exceptionThrown_when_aksCreatedWithPodIpRangeNotValid(String input) {
    assertThatThrownBy(() -> generateBuilder()
        .withPodIpRange(input)
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Pod IP Range does not contain a valid ip with mask");
  }

  @ParameterizedTest
  @ValueSource(strings = {"255.255.255.256", "10.2.0.0/33", "1.1.1.1"})
  public void exceptionThrown_when_aksCreatedWithVnetAddressSpaceIpRangeNotValid(String input) {
    assertThatThrownBy(() -> generateBuilder()
        .withVnetAddressSpaceIpRange(input)
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("VNet Address Space IP Range does not contain a valid ip with mask");
  }

  @ParameterizedTest
  @ValueSource(strings = {"255.255.255.256", "10.2.0.0/33", "1.1.1.1"})
  public void exceptionThrown_when_aksCreatedWithVnetSubnetAddressIpRangeNotValid(String input) {
    assertThatThrownBy(() -> generateBuilder()
        .withVnetSubnetAddressIpRange(input)
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("VNet Subnet Address IP Range does not contain a valid ip with mask");
  }

  @ParameterizedTest
  @ValueSource(strings = {"10.2.0.0/16", "10.2.0.255/16", "10.2.0.0/32"})
  public void exceptionThrown_when_aksCreatedWithServiceIpRangeValid(String input) {
    assertThat(generateBuilder()
        .withServiceIpRange(input)
        .build()
        .validate()).isEmpty();
  }

  @ParameterizedTest
  @ValueSource(strings = {"10.2.0.0/16", "10.2.0.255/16", "10.2.0.0/32"})
  public void exceptionThrown_when_aksCreatedWithPodIpRangeValid(String input) {
    assertThat(generateBuilder()
        .withPodIpRange(input)
        .build()
        .validate()).isEmpty();
  }

  @ParameterizedTest
  @ValueSource(strings = {"10.2.0.0/16", "10.2.0.255/16", "10.2.0.0/32"})
  public void exceptionThrown_when_aksCreatedWithVnetAddressSpaceIpRangeValid(String input) {
    assertThat(generateBuilder()
        .withVnetAddressSpaceIpRange(input)
        .build()
        .validate()).isEmpty();
  }

  @ParameterizedTest
  @ValueSource(strings = {"10.2.0.0/16", "10.2.0.255/16", "10.2.0.0/32"})
  public void exceptionThrown_when_aksCreatedWithVnetSubnetAddressIpRangeValid(String input) {
    assertThat(generateBuilder()
        .withVnetSubnetAddressIpRange(input)
        .build()
        .validate()).isEmpty();
  }

  private AzureKubernetesService.AzureKubernetesServiceBuilder generateBuilder() {
    return AzureKubernetesService.builder()
        .withId(ComponentId.from("test"))
        .withRegion(EUROPE_WEST)
        .withNodePool(AzureNodePool.builder()
            .withMachineType(STANDARD_B2S)
            .withName("azure")
            .withDiskSizeGb(30)
            .withInitialNodeCount(1)
            .withAutoscalingEnabled(false).build());
  }
}
