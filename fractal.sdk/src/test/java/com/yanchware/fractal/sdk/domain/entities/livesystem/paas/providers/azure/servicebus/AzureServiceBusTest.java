package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus.valueobjects.Encryption;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus.valueobjects.KeySource;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus.valueobjects.ServiceBusSku;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus.valueobjects.ServiceBusSkuTier;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureIdentityType;
import com.yanchware.fractal.sdk.domain.values.ComponentId;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion.WEST_EUROPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureServiceBusTest {

  @Test
  public void exceptionThrown_when_idIsLessThan6Characters() {
    assertThatThrownBy(() -> AzureServiceBus.builder().withId("comp-id").withName("test").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("valid Service Bus name must be between 6 and 50 characters of length");
  }

  @Test
  public void exceptionThrown_when_idIsMoreThan50Characters() {
    assertThatThrownBy(() -> AzureServiceBus.builder().withId("comp-id").withName("ttttttttttttttttttttttttttttttttttttttttttttttttttt").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("valid Service Bus name must be between 6 and 50 characters of length");
  }

  @Test
  public void noValidationErrors_when_serviceBusHasRequiredFields() {
    var azureResourceGroup = AzureResourceGroup.builder().withName("az-group").withRegion(WEST_EUROPE).build();
    
    var idOrName = "sb-test-x";
    
    var tags = Map.of("tagKey1", "tagValue1");
    
    var sku = ServiceBusSku.builder()
        .withTier(ServiceBusSkuTier.BASIC)
        .build();
    
    var encryption = Encryption.builder()
        .withKeySource(KeySource.MICROSOFT_KEYVAULT)
        .build();
    
    var queue = AzureServiceBusQueue.builder()
        .withId("queue-id")
        .withDisplayName("queue1")
        .build();
    
    var topic = AzureServiceBusTopic.builder()
        .withId("topic-id")
        .withDisplayName("topic1")
        .build();
    
    var serviceBus = AzureServiceBus.builder()
        .withId(idOrName)
        .withName(idOrName)
        .withRegion(AzureRegion.WEST_EUROPE)
        .withAzureResourceGroup(azureResourceGroup)
        .withTags(tags)
        .withSku(sku)
        .withIdentity(AzureIdentityType.USER_ASSIGNED)
        .withEncryption(encryption)
        .withDisableLocalAuth(Boolean.FALSE)
        .withZoneRedundant(Boolean.FALSE)
        .withQueue(queue)
        .withTopic(topic)
        .build();
    
    assertThat(serviceBus.validate()).isEmpty();
    
    assertThat(serviceBus)
        .asInstanceOf(InstanceOfAssertFactories.type(AzureServiceBus.class))
        .extracting(
            AzureServiceBus::getId,
            AzureServiceBus::getName,
            AzureServiceBus::getAzureRegion,
            AzureServiceBus::getAzureResourceGroup,
            AzureServiceBus::getTags,
            AzureServiceBus::getSku,
            AzureServiceBus::getIdentity,
            AzureServiceBus::getEncryption,
            AzureServiceBus::getDisableLocalAuth,
            AzureServiceBus::getZoneRedundant,
            AzureServiceBus::getQueues,
            AzureServiceBus::getTopics)
        
        .containsExactly(ComponentId.from(idOrName),
            idOrName,
            AzureRegion.WEST_EUROPE,
            azureResourceGroup,
            tags,
            sku,
            AzureIdentityType.USER_ASSIGNED,
            encryption,
            Boolean.FALSE,
            Boolean.FALSE,
            List.of(queue),
            List.of(topic));
  }
}