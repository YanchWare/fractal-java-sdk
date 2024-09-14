package com.yanchware.fractal.sdk.domain.entities.livesystem.saas;

import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.SAAS_UNMANAGED_SECURITY;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class UnmanagedSecurityComponent extends com.yanchware.fractal.sdk.domain.entities.blueprint.saas.UnmanagedSecurityComponent implements LiveSystemComponent {
  private final static String SECRET_VALUE_IS_BLANK = "[SaaSUnmanagedSecurityComponent Validation] Secret Value has not been defined and it is required";

  private String secretName;
  private String secretValue;

  @Override
  public ProviderType getProvider() {
    return ProviderType.SAAS;
  }

  public static ExternalSecurityComponentBuilder builder() {
    return new ExternalSecurityComponentBuilder();
  }

  public static class ExternalSecurityComponentBuilder extends Builder<UnmanagedSecurityComponent, ExternalSecurityComponentBuilder> {

    @Override
    protected UnmanagedSecurityComponent createComponent() {
      return new UnmanagedSecurityComponent();
    }

    @Override
    protected ExternalSecurityComponentBuilder getBuilder() {
      return this;
    }

    /**
     * Sets the name of the secret.
     * 
     * @param secretName The name of the secret.
     * @return The builder instance for method chaining.
     */
    public ExternalSecurityComponentBuilder withSecretName(String secretName) {
      component.setSecretName(secretName);
      return builder;
    }

    /**
     * Sets the value of the secret.
     * 
     * @param secretValue The value of the secret.
     * @return The builder instance for method chaining.
     */
    public ExternalSecurityComponentBuilder withSecretValue(String secretValue) {
      component.setSecretValue(secretValue);
      return builder;
    }

    @Override
    public UnmanagedSecurityComponent build() {
      component.setType(SAAS_UNMANAGED_SECURITY);
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
