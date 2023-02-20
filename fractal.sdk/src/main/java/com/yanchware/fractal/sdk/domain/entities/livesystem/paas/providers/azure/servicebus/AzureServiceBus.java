package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSMessaging;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus.valueobjects.Encryption;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus.valueobjects.ServiceBusSku;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureIdentityType;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLowercaseLettersNumbersAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_SERVICE_BUS_NAMESPACE;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureServiceBus extends PaaSMessaging implements AzureResourceEntity, LiveSystemComponent {
  private static final Integer ID_MIN_LENGTH = 6;
  private static final Integer ID_MAX_LENGTH = 50;
  private final static String NAME_LENGTH_MISMATCH_TEMPLATE =
      "[AzureServiceBus validation] Service Bus name is illegal. A valid Service Bus name must be between " + ID_MIN_LENGTH +
          " and " + ID_MAX_LENGTH + " characters of length";
  private String name;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;
  private ServiceBusSku sku;
  private AzureIdentityType identity;
  private Encryption encryption;
  private Boolean disableLocalAuth;
  private Boolean zoneRedundant;
  private Collection<AzureServiceBusQueue> queues;
  private Collection<AzureServiceBusTopic> topics;

  protected AzureServiceBus() {
  }

  public static class AzureServiceBusBuilder extends Component.Builder<AzureServiceBus, AzureServiceBusBuilder> {

    @Override
    protected AzureServiceBus createComponent() {
      return new AzureServiceBus();
    }

    @Override
    protected AzureServiceBusBuilder getBuilder() {
      return this;
    }

    public AzureServiceBusBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    public AzureServiceBusBuilder withRegion(AzureRegion region) {
      component.setAzureRegion(region);
      return builder;
    }

    public AzureServiceBusBuilder withAzureResourceGroup(AzureResourceGroup azureResourceGroup) {
      component.setAzureResourceGroup(azureResourceGroup);
      return builder;
    }

    public AzureServiceBusBuilder withTags(Map<String, String> tags) {
      component.setTags(tags);
      return builder;
    }

    public AzureServiceBusBuilder withTag(String key, String value) {
      if (component.getTags() == null) {
        withTags(new HashMap<>());
      }

      component.getTags().put(key, value);
      return builder;
    }

    public AzureServiceBusBuilder withSku(ServiceBusSku sku) {
      component.setSku(sku);
      return builder;
    }

    public AzureServiceBusBuilder withIdentity(AzureIdentityType identity) {
      component.setIdentity(identity);
      return builder;
    }

    public AzureServiceBusBuilder withEncryption(Encryption encryption) {
      component.setEncryption(encryption);
      return builder;
    }

    public AzureServiceBusBuilder withZoneRedundant(Boolean zoneRedundant) {
      component.setZoneRedundant(zoneRedundant);
      return builder;
    }

    public AzureServiceBusBuilder withDisableLocalAuth(Boolean disableLocalAuth) {
      component.setDisableLocalAuth(disableLocalAuth);
      return builder;
    }

    public AzureServiceBusBuilder withQueue(AzureServiceBusQueue queue) {
      return withQueues(List.of(queue));
    }

    public AzureServiceBusBuilder withQueues(Collection<AzureServiceBusQueue> queues) {
      if (isBlank(queues)) {
        return builder;
      }

      if (isBlank(component.getQueues())) {
        component.setQueues(new ArrayList<>());
      }

      queues.forEach(queue -> queue.getDependencies().add(component.getId()));
      component.getQueues().addAll(queues);
      return builder;
    }

    public AzureServiceBusBuilder withTopic(AzureServiceBusTopic topic) {
      return withTopics(List.of(topic));
    }

    public AzureServiceBusBuilder withTopics(Collection<AzureServiceBusTopic> topics) {
      if (isBlank(topics)) {
        return builder;
      }

      if (isBlank(component.getTopics())) {
        component.setTopics(new ArrayList<>());
      }

      topics.forEach(topic -> topic.getDependencies().add(component.getId()));
      component.getTopics().addAll(topics);
      return builder;
    }

    @Override
    public AzureServiceBus build() {
      component.setType(PAAS_SERVICE_BUS_NAMESPACE);
      return super.build();
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (StringUtils.isNotBlank(name)) {
      var hasValidCharacters = isValidLowercaseLettersNumbersAndHyphens(name);
      var hasValidLengths = isValidStringLength(name, ID_MIN_LENGTH, ID_MAX_LENGTH);
      if (!hasValidCharacters || !hasValidLengths) {
        errors.add(NAME_LENGTH_MISMATCH_TEMPLATE);
      }
    }

    return errors;
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static AzureServiceBusBuilder builder() {
    return new AzureServiceBusBuilder();
  }
}
