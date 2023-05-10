package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.aks;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(setterPrefix = "with")
public class AzureKubernetesAddonProfile implements Validatable {
  private static final String ADDON_TO_ENABLE_VARIABLE_NOT_SET = "[AzureKubernetesAddonProfile Validation] addonToEnable variable is not set";
  
  private AzureKubernetesAddon addonToEnable;
  private Map<String, Object> config;
  
  private AzureKubernetesAddonProfile() {
  }

  public static AzureKubernetesAddonProfileBuilder builder() {
    return new AzureKubernetesAddonProfileBuilder();
  }

  public static class AzureKubernetesAddonProfileBuilder {
    private final AzureKubernetesAddonProfile component;
    private final AzureKubernetesAddonProfileBuilder builder;

    public AzureKubernetesAddonProfileBuilder () {
      component = createComponent();
      builder = getBuilder();
    }

    protected AzureKubernetesAddonProfile createComponent() {
      return new AzureKubernetesAddonProfile();
    }

    protected AzureKubernetesAddonProfileBuilder getBuilder() {
      return this;
    }

    public AzureKubernetesAddonProfileBuilder withAddonToEnable(AzureKubernetesAddon azureKubernetesAddon) {
      component.setAddonToEnable(azureKubernetesAddon);
      return builder;
    }
    
    public AzureKubernetesAddonProfileBuilder withConfig(Map<String, Object> config) {
      component.setConfig(config);
      return builder;
    }

    public AzureKubernetesAddonProfile build(){
      Collection<String> errors = component.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
            "AzureKubernetesAddonProfile validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return component;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (this.addonToEnable == null) {
      errors.add(ADDON_TO_ENABLE_VARIABLE_NOT_SET);
    }

    return errors;
  }
}