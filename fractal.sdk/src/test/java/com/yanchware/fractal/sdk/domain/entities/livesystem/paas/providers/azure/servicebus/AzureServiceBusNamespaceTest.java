package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus.valueobjects.ServiceBusSku;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus.valueobjects.ServiceBusSkuTier;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion.EUROPE_WEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureServiceBusNamespaceTest {

  @Test
  public void exceptionThrown_when_idIsLessThan6Characters() {
    assertThatThrownBy(() -> AzureServiceBusNamespace.builder().withId("comp-id").withName("test").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("valid Service Bus name must be between 6 and 50 characters of length");
  }

  @Test
  public void exceptionThrown_when_idIsMoreThan50Characters() {
    assertThatThrownBy(() -> AzureServiceBusNamespace.builder().withId("comp-id").withName("ttttttttttttttttttttttttttttttttttttttttttttttttttt").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("valid Service Bus name must be between 6 and 50 characters of length");
  }

  @Test
  public void noValidationErrors_when_serviceBusHasRequiredFields() {
    AzureResourceGroup azureResourceGroup = AzureResourceGroup.builder().withName("az-group").withRegion(EUROPE_WEST).build();
    var serviceBusNamespace = AzureServiceBusNamespace.builder()
        .withId("sb-test-flavian-x")
        .withName("sb-test-flavian-x")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withAzureResourceGroup(azureResourceGroup)
        .withSku(ServiceBusSku.builder()
            .withCapacity(1)
            .withTier(ServiceBusSkuTier.BASIC)
            .build())
        .build();
    assertThat(serviceBusNamespace.validate()).isEmpty();
  }
}