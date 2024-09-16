package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.Validatable;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidAlphanumericsHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;

/**
 * App Service plans give you the flexibility to allocate specific apps to a given set of resources 
 * and further optimize your Azure resource utilization. This way, if you want to save money on your 
 * testing environment you can share a plan across multiple apps.
 */

@Getter
@NoArgsConstructor
public class AzureAppServicePlan extends AzureResource implements Validatable {

  private final static String NAME_NOT_VALID = "[AzureAppServicePlan Validation] The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters";
  private AzureOsType operatingSystem;
  private AzurePricingPlan pricingPlan;
  private Boolean zoneRedundancyEnabled;
  private Integer numberOfWorkers;

  public AzureAppServicePlan(String name, 
                             AzureRegion region, 
                             Map<String, String> tags, 
                             AzureResourceGroup azureResourceGroup, 
                             AzureOsType operatingSystem, 
                             AzurePricingPlan pricingPlan, 
                             Boolean zoneRedundancyEnabled,
                             Integer numberOfWorkers) {
    super(name, region, tags, azureResourceGroup);
    
    this.operatingSystem = operatingSystem;
    this.pricingPlan = pricingPlan;
    this.zoneRedundancyEnabled = zoneRedundancyEnabled;
    this.numberOfWorkers = numberOfWorkers;
  }

  public static AzureAppServicePlanBuilder builder() {
    return new AzureAppServicePlanBuilder();
  }
  
  public static class AzureAppServicePlanBuilder extends AzureResource.Builder<AzureAppServicePlanBuilder> {
    
    protected AzureResourceGroup azureResourceGroup;
    protected AzureOsType operatingSystem;
    protected AzurePricingPlan pricingPlan;
    protected Boolean zoneRedundancyEnabled;
    protected Integer numberOfWorkers;
    

    /**
     * A resource group is a collection of resources that share the same lifecycle, permissions, and policies.
     */
    public AzureAppServicePlanBuilder withAzureResourceGroup(AzureResourceGroup resourceGroup) {
      this.azureResourceGroup = resourceGroup;
      return this;
    }

    /**
     * App Service Plan operating system, either Windows or Linux
     * @param operatingSystem
     */
    public AzureAppServicePlanBuilder withOperatingSystem(AzureOsType operatingSystem) {
      this.operatingSystem = operatingSystem;
      return this;
    }

    /**
     * App Service plan pricing tier determines the location, features, cost and compute resources associated with your app.
     */
    public AzureAppServicePlanBuilder withPricingPlan(AzurePricingPlan pricingPlan) {
      this.pricingPlan = pricingPlan;
      return this;
    }

    /**
     * An App Service plan can be deployed as a zone redundant service in the regions that support it. 
     * This is a deployment time only decision. You can't make an App Service plan zone redundant after 
     * it has been deployed
     */
    public AzureAppServicePlanBuilder withZoneRedundancyEnabled() {
      this.zoneRedundancyEnabled = true;
      return this;
    }

    /**
     * Current number of instances assigned to the resource.
     */
    public AzureAppServicePlanBuilder withNumberOfWorkers(Integer numberOfWorkers) {
      this.numberOfWorkers = numberOfWorkers;
      return this;
    }

    public AzureAppServicePlan build(){
      var appServicePlan = new AzureAppServicePlan(name,
          region,
          tags,
          azureResourceGroup,
          operatingSystem,
          pricingPlan,
          zoneRedundancyEnabled,
          numberOfWorkers);
      
      var errors = appServicePlan.validate();

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

    var name = getName();
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
