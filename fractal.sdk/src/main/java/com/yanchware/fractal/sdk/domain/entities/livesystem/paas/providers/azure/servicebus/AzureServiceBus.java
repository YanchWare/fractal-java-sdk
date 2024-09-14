package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSMessaging;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus.valueobjects.Encryption;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus.valueobjects.ServiceBusSku;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureIdentityType;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLowercaseLettersNumbersAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;
import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_SERVICE_BUS_NAMESPACE;

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

    /**
     * Name of the component
     * Must be between 6 and 50 characters
     * @param name
     */
    public AzureServiceBusBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    /**
     * The region in which the component will be created
     *
     * @param region Azure region
     */
    public AzureServiceBusBuilder withRegion(AzureRegion region) {
      component.setAzureRegion(region);
      return builder;
    }

    /**
     * The resource group in which the component will be created
     *
     * @param azureResourceGroup Azure Resource Group reference
     */
    public AzureServiceBusBuilder withAzureResourceGroup(AzureResourceGroup azureResourceGroup) {
      component.setAzureResourceGroup(azureResourceGroup);
      return builder;
    }

    /**
     * Tags are name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public AzureServiceBusBuilder withTags(Map<String, String> tags) {
      component.setTags(tags);
      return builder;
    }

    /**
     * Tag is name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public AzureServiceBusBuilder withTag(String key, String value) {
      if (component.getTags() == null) {
        withTags(new HashMap<>());
      }

      component.getTags().put(key, value);
      return builder;
    }

    /**
     * Service Bus SKU
     * @param sku
     */
    public AzureServiceBusBuilder withSku(ServiceBusSku sku) {
      component.setSku(sku);
      return builder;
    }

    /**
     * Identity of the Service Bus
     * @param identity
     */
    public AzureServiceBusBuilder withIdentity(AzureIdentityType identity) {
      component.setIdentity(identity);
      return builder;
    }

    /**
     * Encryption properties for the Service Bus
     * @param encryption
     */
    public AzureServiceBusBuilder withEncryption(Encryption encryption) {
      component.setEncryption(encryption);
      return builder;
    }

    /**
     * If enabled, creates a Zone Redundant Service Bus in regions supported availability zones
     * @param zoneRedundant
     */
    public AzureServiceBusBuilder withZoneRedundant(Boolean zoneRedundant) {
      component.setZoneRedundant(zoneRedundant);
      return builder;
    }

    /**
     * Disabling local auth for the Service Bus
     * @param disableLocalAuth
     */
    public AzureServiceBusBuilder withDisableLocalAuth(Boolean disableLocalAuth) {
      component.setDisableLocalAuth(disableLocalAuth);
      return builder;
    }

    /**
     * Queue that will be created part of the Service Bus
     * @param queue
     */
    public AzureServiceBusBuilder withQueue(AzureServiceBusQueue queue) {
      return withQueues(List.of(queue));
    }

    /**
     * List of queues that will be created part of the Service Bus
     * @param queues
     */
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

    /**
     * Topic that will be created part of the Service Bus
     * @param topic
     */
    public AzureServiceBusBuilder withTopic(AzureServiceBusTopic topic) {
      return withTopics(List.of(topic));
    }

    /**
     * List of topics that will be created part of the Service Bus
     * @param topics
     */
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
