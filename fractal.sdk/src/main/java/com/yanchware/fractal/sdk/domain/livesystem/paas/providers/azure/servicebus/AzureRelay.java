package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.servicebus;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.blueprint.paas.PaaSMessaging;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_SERVICE_BUS_RELAY;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLowercaseLettersNumbersAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureRelay extends PaaSMessaging implements AzureResourceEntity, LiveSystemComponent {
  private static final Integer ID_MIN_LENGTH = 6;
  private static final Integer ID_MAX_LENGTH = 50;
  private final static String NAME_LENGTH_MISMATCH_TEMPLATE =
      "[AzureRelay validation] Relay name is illegal. A valid Relay name must be between " + ID_MIN_LENGTH +
          " and " + ID_MAX_LENGTH + " characters of length";
  private String name;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;

  protected AzureRelay() {
  }

  public static class AzureRelayBuilder extends Component.Builder<AzureRelay, AzureRelayBuilder> {

    @Override
    protected AzureRelay createComponent() {
      return new AzureRelay();
    }

    @Override
    protected AzureRelayBuilder getBuilder() {
      return this;
    }

    /**
     * Name of the component
     * Must be between 6 and 50 characters
     *
     * @param name
     */
    public AzureRelayBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    /**
     * The region in which the component will be created
     *
     * @param region Azure region
     */
    public AzureRelayBuilder withRegion(AzureRegion region) {
      component.setAzureRegion(region);
      return builder;
    }

    /**
     * The resource group in which the component will be created
     *
     * @param azureResourceGroup Azure Resource Group reference
     */
    public AzureRelayBuilder withAzureResourceGroup(AzureResourceGroup azureResourceGroup) {
      component.setAzureResourceGroup(azureResourceGroup);
      return builder;
    }

    /**
     * Tags are name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public AzureRelayBuilder withTags(Map<String, String> tags) {
      component.setTags(tags);
      return builder;
    }

    /**
     * Tag is name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public AzureRelayBuilder withTag(String key, String value) {
      if (component.getTags() == null) {
        withTags(new HashMap<>());
      }

      component.getTags().put(key, value);
      return builder;
    }

    @Override
    public AzureRelay build() {
      component.setType(PAAS_SERVICE_BUS_RELAY);
      return super.build();
    }
  }

  @Override
  public Collection<String> validate() {
    var errors = super.validate();

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

  public static AzureRelayBuilder builder() {
    return new AzureRelayBuilder();
  }
}
