package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class AzureResourceGroup extends AzureProxyResource implements Validatable {
  private static final String NAME_IS_EMPTY_OR_TOO_LONG = "[AzureResourceGroup Validation] name is empty or it exceeds 90 characters";
  private final static String REGION_IS_BLANK = "[AzureResourceGroup Validation] Region has not been defined and it is required";

  public AzureResourceGroup() {
  }

  public AzureResourceGroup(String name, AzureRegion region, Map<String, String> tags) {
    super(name, region, tags);
  }

  public static AzureResourceGroupBuilder builder() {
    return new AzureResourceGroupBuilder();
  }

  public static class AzureResourceGroupBuilder extends AzureProxyResource.Builder<AzureResourceGroupBuilder> {

    @Override
    public AzureResourceGroup build() {
      var azureResourceGroup = new AzureResourceGroup(name, region, tags);

      var errors = azureResourceGroup.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
            "AzureResourceGroup validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return azureResourceGroup;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();
    var name = this.getName();
    if (StringUtils.isBlank(name) || name.length() > 90) {
      errors.add(NAME_IS_EMPTY_OR_TOO_LONG);
    }

    if (getRegion() == null) {
      errors.add(REGION_IS_BLANK);
    }

    return errors;
  }
}
