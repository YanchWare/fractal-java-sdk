package com.yanchware.fractal.sdk.domain.livesystem.saas;

import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.SAAS_UNMANAGED_BROKER;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class UnmanagedBrokerComponent extends com.yanchware.fractal.sdk.domain.blueprint.saas.UnmanagedBrokerComponent implements LiveSystemComponent {
  private final static String SECRET_VALUE_IS_BLANK = "[SaaSUnmanagedBrokerComponent Validation] Secret Value has not" +
    " been defined and it is required";

  private String secretName;
  private String secretValue;

  public static UnmanagedStorageComponentBuilder builder() {
    return new UnmanagedStorageComponentBuilder();
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.SAAS;
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (isBlank(secretValue)) {
      errors.add(SECRET_VALUE_IS_BLANK);
    }

    return errors;
  }

  public static class UnmanagedStorageComponentBuilder extends Builder<UnmanagedBrokerComponent,
    UnmanagedStorageComponentBuilder> {

    @Override
    protected UnmanagedBrokerComponent createComponent() {
      return new UnmanagedBrokerComponent();
    }

    @Override
    protected UnmanagedStorageComponentBuilder getBuilder() {
      return this;
    }

    /**
     * Name of the secret
     *
     * @param secretName
     */
    public UnmanagedStorageComponentBuilder withSecretName(String secretName) {
      component.setSecretName(secretName);
      return builder;
    }

    /**
     * Value of the secret
     *
     * @param secretValue
     */
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
}
