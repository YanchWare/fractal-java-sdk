package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.aks;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(setterPrefix = "with")
public class AzureOutboundIp implements Validatable {
  private static final String NAME_IS_EMPTY_OR_TOO_LONG = "[AzureOutboundIp Validation] name is empty or it exceeds 80 characters";
  private String name;
  private AzureResourceGroup azureResourceGroup;
  
  private AzureOutboundIp() {
  }

  public static AzureOutboundIpBuilder builder() {
    return new AzureOutboundIpBuilder();
  }

  public static class AzureOutboundIpBuilder {
    private final AzureOutboundIp component;
    private final AzureOutboundIpBuilder builder;

    public AzureOutboundIpBuilder () {
      component = createComponent();
      builder = getBuilder();
    }

    protected AzureOutboundIp createComponent() {
      return new AzureOutboundIp();
    }

    protected AzureOutboundIpBuilder getBuilder() {
      return this;
    }

    public AzureOutboundIpBuilder withName(String outboundIpName) {
      component.setName(outboundIpName);
      return builder;
    }
    
    public AzureOutboundIpBuilder withResourceGroup(AzureResourceGroup azureResourceGroup) {
      component.setAzureResourceGroup(azureResourceGroup);
      return builder;
    }

    public AzureOutboundIp build(){
      Collection<String> errors = component.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
            "AzureOutboundIp validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return component;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (StringUtils.isBlank(name) || name.length() > 80) {
      errors.add(NAME_IS_EMPTY_OR_TOO_LONG);
    }

    return errors;
  }

}
