package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub;

import com.yanchware.fractal.sdk.domain.blueprint.paas.PaaSStreaming;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.paas.SupportedTlsVersions;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureIdentityType;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub.valueobjects.EventhubSku;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.servicebus.valueobjects.Encryption;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects.AzurePublicNetworkAccess;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_EVENTHUB_NAMESPACE;
import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLowercaseLettersNumbersAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureEventhubNamespace extends PaaSStreaming implements AzureResourceEntity, LiveSystemComponent {
  private static final Integer ID_MIN_LENGTH = 6;
  private static final Integer ID_MAX_LENGTH = 50;
  private final static String NAME_LENGTH_MISMATCH_TEMPLATE =
      "[AzureEventhub validation] Eventhub namespace is illegal. A valid Eventhub namespace must be between " + ID_MIN_LENGTH +
          " and " + ID_MAX_LENGTH + " characters of length";
  private String name;
  private String alternateName;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;
  private EventhubSku sku;
  private AzureIdentityType identity;
  private Encryption encryption;
  private Boolean disableLocalAuth;
  private Boolean zoneRedundant;
  private Boolean autoInflateEnabled;
  private Integer maximumThroughputUnits;
  private SupportedTlsVersions minimumTlsVersion;
  private AzurePublicNetworkAccess publicNetworkAccess;

  private Boolean kafkaEnabled;
  private Collection<AzureEventhubInstance> instances;

  protected AzureEventhubNamespace() {
  }

  /**
   * Builder class for constructing and configuring an instance of {@link AzureEventhubNamespace}.
   * This builder provides a fluent interface to specify settings and properties for the Azure Eventhub.
   * It is designed to customize various features such as SKU, region, tags, identity, encryption, scaling options, and more.
   */
  public static class AzureEventhubBuilder extends Builder<AzureEventhubNamespace, AzureEventhubBuilder> {

    @Override
    protected AzureEventhubNamespace createComponent() {
      return new AzureEventhubNamespace();
    }

    @Override
    protected AzureEventhubBuilder getBuilder() {
      return this;
    }

    /**
     * Assigns a specific name to the Azure Event Hub component.
     *
     * @param name the name to be assigned to the Azure Event Hub component
     * @return the current instance of AzureEventhubBuilder for method chaining
     */
    public AzureEventhubBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    /**
     * Sets an alternate name for the Azure Eventhub component.
     * This name is optional and can provide an additional identifier for the component.
     *
     * @param alternateName the alternate name to be assigned to the Azure Eventhub component.
     *                      It must be a valid string that adheres to any naming constraints.
     * @return the {@code AzureEventhubBuilder} instance with the specified alternate name applied.
     */
    public AzureEventhubBuilder withAlternateName(String alternateName) {
      component.setAlternateName(alternateName);
      return builder;
    }

    /**
     * The region in which the component will be created
     *
     * @param region Azure region
     */
    public AzureEventhubBuilder withRegion(AzureRegion region) {
      component.setAzureRegion(region);
      return builder;
    }

    /**
     * The resource group in which the component will be created
     *
     * @param azureResourceGroup Azure Resource Group reference
     */
    public AzureEventhubBuilder withAzureResourceGroup(AzureResourceGroup azureResourceGroup) {
      component.setAzureResourceGroup(azureResourceGroup);
      return builder;
    }

    /**
     * Tags are name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public AzureEventhubBuilder withTags(Map<String, String> tags) {
      component.setTags(tags);
      return builder;
    }

    /**
     * Tag is name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public AzureEventhubBuilder withTag(String key, String value) {
      if (component.getTags() == null) {
        withTags(new HashMap<>());
      }

      component.getTags().put(key, value);
      return builder;
    }

    /**
     * Sets the SKU (Eventhub Tier and Capacity) for the Azure Eventhub.
     *
     * @param sku the {@link EventhubSku} object that contains the configuration details,
     *            including the tier and capacity for the Eventhub.
     * @return the {@code AzureEventhubBuilder} instance with the updated SKU configuration.
     */
    public AzureEventhubBuilder withSku(EventhubSku sku) {
      component.setSku(sku);
      return builder;
    }

    /**
     * Configures the identity type for the Azure Eventhub. The identity type determines the
     * authentication mechanism to be used, such as system-assigned or user-assigned managed identities.
     *
     * @param identity the {@link AzureIdentityType} specifying the type of identity to be assigned
     *                 to the Azure Eventhub. Supported values include {@code SYSTEM_ASSIGNED},
     *                 {@code USER_ASSIGNED}, {@code NONE}, or {@code SYSTEM_ASSIGNED_USER_ASSIGNED}.
     * @return the {@code AzureEventhubBuilder} instance with the specified identity configuration.
     */
    public AzureEventhubBuilder withIdentity(AzureIdentityType identity) {
      component.setIdentity(identity);
      return builder;
    }

    /**
     * Configures the encryption settings for the Azure Eventhub. Encryption ensures that
     * the data is secured both at rest and in transit based on the provided configuration.
     *
     * @param encryption the {@link Encryption} object that contains the encryption settings,
     *                   including Key Vault properties, key source, and infrastructure encryption requirements.
     * @return the {@code AzureEventhubBuilder} instance with the updated encryption configuration.
     */
    public AzureEventhubBuilder withEncryption(Encryption encryption) {
      component.setEncryption(encryption);
      return builder;
    }

    /**
     * Enables or disables the auto-inflate feature for the Azure Eventhub. Auto-inflate allows
     * the Eventhub to automatically scale up its throughput units based on usage to meet demand.
     *
     * @param autoInflateEnabled a Boolean value indicating whether auto-inflate should be enabled.
     *                           If set to {@code true}, auto-inflate will be enabled for the Eventhub.
     * @return the {@code AzureEventhubBuilder} instance with the auto-inflate setting updated.
     */
    public AzureEventhubBuilder withAutoInflateEnabled(Boolean autoInflateEnabled) {
      component.setAutoInflateEnabled(autoInflateEnabled);
      return builder;
    }

    /**
     * Configures whether Kafka protocol should be enabled for the Azure Eventhub.
     *
     * @param kafkaEnabled a Boolean value indicating whether to enable Kafka support.
     *                      If set to {@code true}, Kafka will be enabled for the Eventhub.
     * @return the {@code AzureEventhubBuilder} instance with the updated configuration.
     */
    public AzureEventhubBuilder withKafkaEnabled(Boolean kafkaEnabled) {
      component.setKafkaEnabled(kafkaEnabled);
      return builder;
    }

    /**
     * Configures whether the Azure Eventhub should be zone redundant. Zone redundancy helps ensure
     * high availability and fault tolerance by distributing resources across multiple availability zones.
     *
     * @param zoneRedundant a Boolean value indicating whether to enable zone redundancy.
     *                      If set to {@code true}, zone redundancy will be enabled.
     * @return the {@code AzureEventhubBuilder} instance with the updated configuration.
     */
    public AzureEventhubBuilder withZoneRedundant(Boolean zoneRedundant) {
      component.setZoneRedundant(zoneRedundant);
      return builder;
    }

    /**
     * Configures the minimum TLS (Transport Layer Security) version to be used
     * for the Azure Eventhub. This setting ensures that the Eventhub enforces
     * the specified TLS version or higher for secure communications.
     *
     * @param minimumTlsVersion the {@link SupportedTlsVersions} specifying the minimum
     *                          TLS version. Supported options include {@code ONE_ZERO},
     *                          {@code ONE_ONE}, and {@code ONE_TWO}.
     * @return the {@code AzureEventhubBuilder} instance with the specified minimum
     *         TLS version applied.
     */
    public AzureEventhubBuilder withMinimumTlsVersion(SupportedTlsVersions minimumTlsVersion) {
      component.setMinimumTlsVersion(minimumTlsVersion);
      return builder;
    }

    /**
     * Disables local authentication for the Azure Eventhub.
     *
     * @param disableLocalAuth a Boolean value indicating whether to disable local authentication.
     *                         If set to {@code true}, local authentication will be disabled.
     * @return the {@code AzureEventhubBuilder} instance with the updated configuration.
     */
    public AzureEventhubBuilder withDisableLocalAuth(Boolean disableLocalAuth) {
      component.setDisableLocalAuth(disableLocalAuth);
      return builder;
    }

    /**
     * Sets the maximum throughput units for the Azure Eventhub. Throughput units determine the capacity of the Eventhub,
     * impacting performance and cost.
     *
     * @param maximumThroughputUnits the maximum number of throughput units for the Eventhub. It can be null if no specific limit is required.
     * @return the {@code AzureEventhubBuilder} instance with the updated configuration.
     */
    public AzureEventhubBuilder withMaximumThroughputUnits(Integer maximumThroughputUnits) {
      component.setMaximumThroughputUnits(maximumThroughputUnits);
      return builder;
    }

    /**
     * Configures the Azure Event Hub with the specified public network access setting.
     *
     * @param publicNetworkAccess the public network access setting to be used for the Azure Event Hub
     * @return the updated instance of AzureEventhubBuilder for method chaining
     */
    public AzureEventhubBuilder withPublicNetworkAccess(AzurePublicNetworkAccess publicNetworkAccess) {
      component.setPublicNetworkAccess(publicNetworkAccess);
      return builder;
    }

    /**
     * Adds a single Azure Eventhub instance to the builder.
     *
     * @param instance the {@link AzureEventhubInstance} to be added to the builder
     * @return the {@code AzureEventhubBuilder} instance with the updated configuration
     */
    public AzureEventhubBuilder withInstance(AzureEventhubInstance instance) {
      return withInstances(List.of(instance));
    }

    /**
     * Adds a collection of Azure Eventhub instances to the builder. The provided instances
     * will have their dependencies updated with the component's ID and will be added to the
     * existing instances of the component.
     *
     * @param instances a collection of {@link AzureEventhubInstance} objects to be added
     *                  to the builder. If the collection is null or empty, no action will be taken.
     * @return the {@code AzureEventhubBuilder} instance with the updated configuration.
     */
    public AzureEventhubBuilder withInstances(Collection<AzureEventhubInstance> instances) {
      if (isBlank(instances)) {
        return builder;
      }

      if (isBlank(component.getInstances())) {
        component.setInstances(new ArrayList<>());
      }

      instances.forEach(queue -> queue.getDependencies().add(component.getId()));
      component.getInstances().addAll(instances);
      return builder;
    }

    @Override
    public AzureEventhubNamespace build() {
      component.setType(PAAS_EVENTHUB_NAMESPACE);
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

  public static AzureEventhubBuilder builder() {
    return new AzureEventhubBuilder();
  }
}
