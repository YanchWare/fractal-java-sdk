package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yanchware.fractal.sdk.domain.Validatable;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A container that holds related resources for an Azure solution.
 * The resource group can include all the resources for the solution, or only those resources that you want to manage
 * as a group.
 * You decide how you want to allocate resources to resource groups based on what makes the most sense for you  .
 */
public class AzureResourceGroup extends AzureProxyResource implements Validatable {
  @JsonIgnore
  public static final String NAME_IS_NULL_OR_EMPTY = "[AzureResourceGroup Validation] Name cannot be null or empty";
  @JsonIgnore
  public static final String NAME_IS_NOT_VALID = "[AzureResourceGroup Validation] Name can only include alphanumeric," +
    " underscore, parentheses, hyphen, period (except at end), and Unicode characters that match the allowed " +
    "characters";
  @JsonIgnore
  public final static String REGION_IS_BLANK = "[AzureResourceGroup Validation] Region has not been defined and it is" +
    " required";
  private static final String NAME_EXCEEDS_LENGTH_LIMIT = "[AzureResourceGroup Validation] Name exceeds 90 characters";
  private static final Pattern RESOURCE_GROUP_NAME_PATTERN = Pattern.compile("^[\\w()-.]+[\\w()-]$");

  public AzureResourceGroup() {
  }

  public AzureResourceGroup(String name, AzureRegion region, Map<String, String> tags) {
    super(name, region, tags);
  }

  public static AzureResourceGroupBuilder builder() {
    return new AzureResourceGroupBuilder();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();
    var name = this.getName();

    if (StringUtils.isBlank(name)) {
      errors.add(NAME_IS_NULL_OR_EMPTY);
    }

    if (StringUtils.isNotBlank(name) && !RESOURCE_GROUP_NAME_PATTERN.matcher(name).matches()) {
      errors.add(NAME_IS_NOT_VALID);
    }

    if (StringUtils.isNotBlank(name) && name.length() > 90) {
      errors.add(NAME_EXCEEDS_LENGTH_LIMIT);
    }

    if (getRegion() == null) {
      errors.add(REGION_IS_BLANK);
    }

    return errors;
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
}
