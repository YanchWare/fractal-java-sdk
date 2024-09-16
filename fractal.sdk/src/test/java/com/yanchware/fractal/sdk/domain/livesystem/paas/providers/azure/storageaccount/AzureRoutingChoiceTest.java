package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureRoutingChoiceTest {
  @Test
  public void azureRoutingChoiceConstants_shouldNotBeNull() {
    assertThat(AzureRoutingChoice.MICROSOFT_ROUTING)
        .as("MICROSOFT_ROUTING constant should not be null")
        .isNotNull();

    assertThat(AzureRoutingChoice.INTERNET_ROUTING)
        .as("INTERNET_ROUTING constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureRoutingChoice() {
    assertThat(AzureRoutingChoice.fromString("MicrosoftRouting"))
        .as("fromString should return MICROSOFT_ROUTING for 'MicrosoftRouting'")
        .isEqualTo(AzureRoutingChoice.MICROSOFT_ROUTING);
    
    assertThat(AzureRoutingChoice.fromString("InternetRouting"))
        .as("fromString should return INTERNET_ROUTING for 'InternetRouting'")
        .isEqualTo(AzureRoutingChoice.INTERNET_ROUTING);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureRoutingChoicesWithCorrectSize() {
    Collection<AzureRoutingChoice> values = AzureRoutingChoice.values();

    assertThat(values)
        .as("Values should contain MICROSOFT_ROUTING, and INTERNET_ROUTING and have exactly 2 values")
        .containsExactlyInAnyOrder(AzureRoutingChoice.MICROSOFT_ROUTING, AzureRoutingChoice.INTERNET_ROUTING);
  }

}