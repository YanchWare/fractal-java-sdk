package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion.EUROPE_WEST;
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
    AzureResourceGroup azureResourceGroup = AzureResourceGroup.builder().withName("az-group").withRegion(EUROPE_WEST).build();
    var relay = AzureRelay.builder()
        .withId("relay-test-x")
        .withName("relay-test-x")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withAzureResourceGroup(azureResourceGroup)
        .build();
    assertThat(relay.validate()).isEmpty();
  }
}