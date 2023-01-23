package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(setterPrefix = "with")
public class AzureResourceGroup implements Validatable {
  private static final String NAME_IS_EMPTY_OR_TOO_LONG = "[AzureResourceGroup Validation] name is empty or it exceeds 90 characters";
  private final static String REGION_IS_BLANK = "[AzureResourceGroup Validation] Region has not been defined and it is required";

  private String name;
  private AzureRegion region;

  private AzureResourceGroup() {
  }

  public static AzureResourceGroupBuilder builder() {
    return new AzureResourceGroupBuilder();
  }

  public static class AzureResourceGroupBuilder {
    private final AzureResourceGroup component;
    private final AzureResourceGroupBuilder builder;

    public AzureResourceGroupBuilder () {
      component = createComponent();
      builder = getBuilder();
    }

    protected AzureResourceGroup createComponent() {
      return new AzureResourceGroup();
    }

    protected AzureResourceGroupBuilder getBuilder() {
      return this;
    }

    public AzureResourceGroupBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    public AzureResourceGroupBuilder withRegion(AzureRegion region) {
      component.setRegion(region);
      return builder;
    }

    public AzureResourceGroup build(){
      Collection<String> errors = component.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
            "AzureResourceGroup validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return component;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (StringUtils.isBlank(name) || name.length() > 90) {
      errors.add(NAME_IS_EMPTY_OR_TOO_LONG);
    } 
    
    if(region == null) {
      errors.add(REGION_IS_BLANK);
    }

    return errors;
  }
}
