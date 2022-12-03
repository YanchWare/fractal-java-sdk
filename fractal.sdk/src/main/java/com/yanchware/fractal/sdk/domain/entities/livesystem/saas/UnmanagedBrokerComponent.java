package com.yanchware.fractal.sdk.domain.entities.livesystem.saas;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.SAAS_UNMANAGED_BROKER;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class UnmanagedBrokerComponent extends com.yanchware.fractal.sdk.domain.entities.blueprint.saas.UnmanagedStorageComponent implements LiveSystemComponent {
  private final static String SECRET_VALUE_IS_BLANK = "[SaaSUnmanagedBrokerComponent Validation] Secret Value has not been defined and it is required";

  private String secretValue;

  @Override
  public ProviderType getProvider() {
    return ProviderType.SAAS;
  }

  public static UnmanagedStorageComponentBuilder builder() {
    return new UnmanagedStorageComponentBuilder();
  }

  public static class UnmanagedStorageComponentBuilder extends Builder<UnmanagedBrokerComponent, UnmanagedStorageComponentBuilder> {

    @Override
    protected UnmanagedBrokerComponent createComponent() {
      return new UnmanagedBrokerComponent();
    }

    @Override
    protected UnmanagedStorageComponentBuilder getBuilder() {
      return this;
    }

    public UnmanagedStorageComponentBuilder withSecretValue(String secretValue) {
      component.setSecretValue(secretValue);
      return builder;
    }

    @Override
    public UnmanagedBrokerComponent build() {
      component.setType(SAAS_UNMANAGED_BROKER);
      return super.build();
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (isBlank(secretValue)) {
      errors.add(SECRET_VALUE_IS_BLANK);
    }

    return errors;
  }
}
