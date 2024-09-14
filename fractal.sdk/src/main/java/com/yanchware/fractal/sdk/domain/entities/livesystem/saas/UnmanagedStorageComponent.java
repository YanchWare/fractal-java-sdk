package com.yanchware.fractal.sdk.domain.entities.livesystem.saas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.SAAS_UNMANAGED_STORAGE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class UnmanagedStorageComponent extends com.yanchware.fractal.sdk.domain.entities.blueprint.saas.UnmanagedStorageComponent implements LiveSystemComponent {
  private final static String SECRET_VALUE_IS_BLANK = "[SaaSUnmanagedStorageComponent Validation] Secret Value has not been defined and it is required";

  private String secretName;
  private String secretValue;

  @Override
  public ProviderType getProvider() {
    return ProviderType.SAAS;
  }

  public static UnmanagedStorageComponentBuilder builder() {
    return new UnmanagedStorageComponentBuilder();
  }

  public static class UnmanagedStorageComponentBuilder extends Component.Builder<UnmanagedStorageComponent, UnmanagedStorageComponentBuilder> {

    @Override
    protected UnmanagedStorageComponent createComponent() {
      return new UnmanagedStorageComponent();
    }

    @Override
    protected UnmanagedStorageComponentBuilder getBuilder() {
      return this;
    }
    
    /**
     * Name of the secret
     * @param secretName
     */
    public UnmanagedStorageComponentBuilder withSecretName(String secretName) {
      component.setSecretName(secretName);
      return builder;
    }
    
    /**
     * Value of the secret
     * @param secretValue
     */
    public UnmanagedStorageComponentBuilder withSecretValue(String secretValue) {
      component.setSecretValue(secretValue);
      return builder;
    }

    @Override
    public UnmanagedStorageComponent build() {
      component.setType(SAAS_UNMANAGED_STORAGE);
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
