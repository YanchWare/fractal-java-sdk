package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.servicebus;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion.WEST_EUROPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureRelayTest {
  @Test
  public void exceptionThrown_when_idIsLessThan6Characters() {
    assertThatThrownBy(() -> AzureRelay.builder().withId("comp-id").withName("test").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("valid Relay name must be between 6 and 50 characters of length");
  }

  @Test
  public void exceptionThrown_when_idIsMoreThan50Characters() {
    assertThatThrownBy(() -> AzureRelay.builder().withId("comp-id").withName("ttttttttttttttttttttttttttttttttttttttttttttttttttt").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("valid Relay name must be between 6 and 50 characters of length");
  }

  @Test
  public void noValidationErrors_when_relayHasRequiredFields() {
    AzureResourceGroup azureResourceGroup = AzureResourceGroup.builder().withName("az-group").withRegion(WEST_EUROPE).build();
    var relay = AzureRelay.builder()
        .withId("relay-test-x")
        .withName("relay-test-x")
        .withRegion(AzureRegion.WEST_EUROPE)
        .withAzureResourceGroup(azureResourceGroup)
        .build();
    assertThat(relay.validate()).isEmpty();
  }
}
