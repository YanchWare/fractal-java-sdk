package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSMessaging;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLowercaseLettersNumbersAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_SERVICE_BUS_RELAY;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureServiceRelay extends PaaSMessaging implements AzureEntity, LiveSystemComponent {
  private static final Integer ID_MIN_LENGTH = 6;
  private static final Integer ID_MAX_LENGTH = 50;
  private final static String NAME_LENGTH_MISMATCH_TEMPLATE =
      "[AzureServiceRelayNamespace validation] Relay name is illegal. A valid Relay name must be between " +  ID_MIN_LENGTH +
          " and " + ID_MAX_LENGTH + " characters of length";
  private String name;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;

  protected AzureServiceRelay() {
  }

  public static class AzureServiceRelayBuilder extends Component.Builder<AzureServiceRelay, AzureServiceRelayBuilder> {

    @Override
    protected AzureServiceRelay createComponent() {
      return new AzureServiceRelay();
    }

    @Override
    protected AzureServiceRelayBuilder getBuilder() {
      return this;
    }

    public AzureServiceRelayBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    public AzureServiceRelayBuilder withRegion(AzureRegion region) {
      component.setAzureRegion(region);
      return builder;
    }

    public AzureServiceRelayBuilder withAzureResourceGroup(AzureResourceGroup azureResourceGroup) {
      component.setAzureResourceGroup(azureResourceGroup);
      return builder;
    }

    public AzureServiceRelayBuilder withTags(Map<String, String> tags) {
      component.setTags(tags);
      return builder;
    }

    public AzureServiceRelayBuilder withTag(String key, String value) {
      if (component.getTags() == null) {
        withTags(new HashMap<>());
      }

      component.getTags().put(key, value);
      return builder;
    }

    @Override
    public AzureServiceRelay build() {
      component.setType(PAAS_SERVICE_BUS_RELAY);
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

  public static AzureServiceRelayBuilder builder() {
    return new AzureServiceRelayBuilder();
  }
}
