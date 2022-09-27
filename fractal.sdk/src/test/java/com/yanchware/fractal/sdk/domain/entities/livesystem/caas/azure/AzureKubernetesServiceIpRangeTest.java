package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.yanchware.fractal.sdk.utils.TestUtils.getBasicAks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureKubernetesServiceIpRangeTest {

  @ParameterizedTest
  @ValueSource(strings = {"255.255.255.256", "10.2.0.0/33", "1.1.1.1"})
  public void exceptionThrown_when_aksCreatedWithServiceIpRangeNotValid(String input) {
    assertThatThrownBy(() -> getBasicAks()
        .withServiceIpRange(input)
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Service IP Range does not contain a valid ip with mask");
  }

  @ParameterizedTest
  @ValueSource(strings = {"255.255.255.256", "10.2.0.0/33", "1.1.1.1"})
  public void exceptionThrown_when_aksCreatedWithPodIpRangeNotValid(String input) {
    assertThatThrownBy(() -> getBasicAks()
        .withPodIpRange(input)
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Pod IP Range does not contain a valid ip with mask");
  }

  @ParameterizedTest
  @ValueSource(strings = {"255.255.255.256", "10.2.0.0/33", "1.1.1.1"})
  public void exceptionThrown_when_aksCreatedWithVnetAddressSpaceIpRangeNotValid(String input) {
    assertThatThrownBy(() -> getBasicAks()
        .withVnetAddressSpaceIpRange(input)
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("VNet Address Space IP Range does not contain a valid ip with mask");
  }

  @ParameterizedTest
  @ValueSource(strings = {"255.255.255.256", "10.2.0.0/33", "1.1.1.1"})
  public void exceptionThrown_when_aksCreatedWithVnetSubnetAddressIpRangeNotValid(String input) {
    assertThatThrownBy(() -> getBasicAks()
        .withVnetSubnetAddressIpRange(input)
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("VNet Subnet Address IP Range does not contain a valid ip with mask");
  }

  @ParameterizedTest
  @ValueSource(strings = {"10.2.0.0/16", "10.2.0.255/16", "10.2.0.0/32"})
  public void exceptionThrown_when_aksCreatedWithServiceIpRangeValid(String input) {
    assertThat(getBasicAks()
        .withServiceIpRange(input)
        .build()
        .validate()).isEmpty();
  }

  @ParameterizedTest
  @ValueSource(strings = {"10.2.0.0/16", "10.2.0.255/16", "10.2.0.0/32"})
  public void exceptionThrown_when_aksCreatedWithPodIpRangeValid(String input) {
    assertThat(getBasicAks()
        .withPodIpRange(input)
        .build()
        .validate()).isEmpty();
  }

  @ParameterizedTest
  @ValueSource(strings = {"10.2.0.0/16", "10.2.0.255/16", "10.2.0.0/32"})
  public void exceptionThrown_when_aksCreatedWithVnetAddressSpaceIpRangeValid(String input) {
    assertThat(getBasicAks()
        .withVnetAddressSpaceIpRange(input)
        .build()
        .validate()).isEmpty();
  }

  @ParameterizedTest
  @ValueSource(strings = {"10.2.0.0/16", "10.2.0.255/16", "10.2.0.0/32"})
  public void exceptionThrown_when_aksCreatedWithVnetSubnetAddressIpRangeValid(String input) {
    assertThat(getBasicAks()
        .withVnetSubnetAddressIpRange(input)
        .build()
        .validate()).isEmpty();
  }
}
