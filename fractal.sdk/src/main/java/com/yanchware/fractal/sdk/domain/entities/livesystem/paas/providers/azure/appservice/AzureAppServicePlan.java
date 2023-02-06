package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.*;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.aks.AzureActiveDirectoryProfile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidAlphanumericsHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;

/**
 * App Service plans give you the flexibility to allocate specific apps to a given set of resources 
 * and further optimize your Azure resource utilization. This way, if you want to save money on your 
 * testing environment you can share a plan across multiple apps.
 */
@Getter
@Setter
public class AzureAppServicePlan implements AzureEntity, Validatable {

  private final static String NAME_NOT_VALID = "[AzureAppServicePlan Validation] The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters";

  private String name;
  
  /**
   * A resource group is a collection of resources that share the same lifecycle, permissions, and policies.
   */
  private AzureResourceGroup azureResourceGroup;
  private AzureRegion azureRegion;
  private AzureOsType operatingSystem;
  private AzurePricingPlan pricingPlan;

  /**
   * An App Service plan can be deployed as a zone redundant service in the regions that support it. 
   * This is a deployment time only decision. You can't make an App Service plan zone redundant after 
   * it has been deployed
   */
  private Boolean enableZoneRedundancy;

  /**
   * Tags are name/value pairs that enable you to categorize resources and view consolidated billing by 
   * applying the same tag to multiple resources and resource groups.
   */
  private Map<String, String> tags;

  public static AzureAppServicePlanBuilder builder() {
    return new AzureAppServicePlanBuilder();
  }
  
  public static class AzureAppServicePlanBuilder {

    private final AzureAppServicePlan appServicePlan;
    private final AzureAppServicePlanBuilder builder;

    public AzureAppServicePlanBuilder () {
      appServicePlan = createComponent();
      builder = getBuilder();
    }

    protected AzureAppServicePlan createComponent() {
      return new AzureAppServicePlan();
    }

    protected AzureAppServicePlanBuilder getBuilder() {
      return this;
    }

    public AzureAppServicePlanBuilder withName(String name) {
      appServicePlan.setName(name);
      return builder;
    }

    public AzureAppServicePlanBuilder withAzureResourceGroup(AzureResourceGroup resourceGroup) {
      appServicePlan.setAzureResourceGroup(resourceGroup);
      return builder;
    }

    public AzureAppServicePlanBuilder withAzureRegion(AzureRegion region) {
      appServicePlan.setAzureRegion(region);
      return builder;
    }

    public AzureAppServicePlanBuilder withOperatingSystem(AzureOsType operatingSystem) {
      appServicePlan.setOperatingSystem(operatingSystem);
      return builder;
    }

    public AzureAppServicePlanBuilder withPricingPlan(AzurePricingPlan pricingPlan) {
      appServicePlan.setPricingPlan(pricingPlan);
      return builder;
    }

    public AzureAppServicePlanBuilder enableZoneRedundancy() {
      appServicePlan.setEnableZoneRedundancy(true);
      return builder;
    }

    public AzureAppServicePlanBuilder withTags(Map<String, String> tags) {
      appServicePlan.setTags(tags);
      return builder;
    }

    public AzureAppServicePlanBuilder withTag(String key, String value) {
      if (appServicePlan.getTags() == null) {
        withTags(new HashMap<>());
      }

      appServicePlan.getTags().put(key, value);
      return builder;
    }

    public AzureAppServicePlan build(){
      Collection<String> errors = appServicePlan.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
            "AzureAppServicePlan validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return appServicePlan;
    }
    
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if(StringUtils.isBlank(name)) {
      errors.add(NAME_NOT_VALID);
    } else {
      var hasValidCharacters = isValidAlphanumericsHyphens(name);
      var hasValidLengths = isValidStringLength(name, 1, 40);
      if (!hasValidCharacters || !hasValidLengths) {
        errors.add(NAME_NOT_VALID);
      }
    }
    
    return errors;
  }
}
