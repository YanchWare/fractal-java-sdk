package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cdn;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.blueprint.paas.PaaSNetworkCdnProfile;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_CDN_PROFILE;

/**
 * Represents an Azure CDN Profile within the SDK, encapsulating the configuration for a Content Delivery Network (CDN)
 * provided by Azure. This class includes configurations such as the resource group, region, identity, SKU, and other
 * CDN-specific settings. It supports validation of its fields to ensure compliance with Azure's requirements and SDK's
 * design principles.
 * <p>
 * Use the {@link AzureCdnProfileBuilder} to create instances of this class, ensuring that all mandatory fields are
 * properly set and validated. Note that certain fields like 'name' and 'originResponseTimeoutSeconds' have specific
 * validation rules that must be adhered to, as detailed in the builder method documentation.
 * </p>
 */

@Getter
@Setter
@NoArgsConstructor
public class AzureCdnProfile extends PaaSNetworkCdnProfile implements AzureResourceEntity, LiveSystemComponent {
  private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z0-9][A-Za-z0-9-]*[A-Za-z0-9]$");

  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private AzureManagedServiceIdentity identity;
  private String name;
  private Integer originResponseTimeoutSeconds;
  private AzureCdnSku sku;
  private Map<String, String> tags;

  public static AzureCdnProfileBuilder builder() {
    return new AzureCdnProfileBuilder();
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static class AzureCdnProfileBuilder extends Component.Builder<AzureCdnProfile, AzureCdnProfileBuilder> {

    @Override
    protected AzureCdnProfile createComponent() {
      return new AzureCdnProfile();
    }

    @Override
    protected AzureCdnProfileBuilder getBuilder() {
      return this;
    }

    /**
     * <pre>
     * Configures the identity for the CDN Profile, allowing for both system assigned and user assigned identities.
     * This identity is used for authentication within Azure to access or modify the CDN Profile.</pre>
     * @param identity The managed service identity configuration.
     * @return The builder instance for chaining.
     */
    public AzureCdnProfileBuilder withIdentity(AzureManagedServiceIdentity identity) {
      component.setIdentity(identity);
      return builder;
    }

    /**
     * <pre>
     * Sets the name for the Azure CDN Profile. The name must be unique within the resource group and must follow Azure's
     * naming conventions.
     * 
     * Validation rules:
     *   - Name can contain letters, numbers, and hyphens.
     *   - The first and last characters must be a letter or a number.
     *   - Maximum length is 260 characters.
     *   - Minimum length is 1 character.
     * 
     * Attempting to set a name that violates these rules will result in a validation error.</pre>
     * @param name The unique name of the CDN Profile.
     * @return The builder instance for chaining.
     */
    public AzureCdnProfileBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    /**
     * <pre>
     * Configures the origin response timeout in seconds for forwarding requests to the CDN origin. This setting determines
     * how long the CDN waits for a response from the origin server before the request is considered failed.
     * 
     * Setting an appropriate timeout is crucial for optimizing content delivery performance, balancing the need for content
     * freshness against minimizing user-perceived latency.
     * 
     * <strong>Note on acceptable values:</strong>
     *   - The value must be an integer between 16 and 240, inclusive. Values outside this range will be rejected.
     *   - Choosing a value too close to the lower limit may lead to increased failed requests during intermittent
     *     network instability, while setting it too high could cause unnecessary delays for your users.</pre>
     * 
     * @param originResponseTimeoutSeconds The desired number of seconds to wait for a response from the origin server.
     * @return The builder instance for chaining further configuration calls.
     */
    public AzureCdnProfileBuilder withOriginResponseTimeoutSeconds(Integer originResponseTimeoutSeconds) {
      component.setOriginResponseTimeoutSeconds(originResponseTimeoutSeconds);
      return builder;
    }

    /**
     * <pre>
     * Specifies the SKU (Stock Keeping Unit) for the Azure CDN Profile, which determines the pricing tier, feature
     * set, and the provider of the CDN services. The SKU choice impacts various aspects of the CDN service,
     * including global distribution capabilities, content acceleration, security features, and cost.
     * 
     * The <code><strong>AzureCdnSku</strong></code> is an extendable enum, allowing for future expansion. 
     * Current predefined SKUs include:
     *   - <code><strong>STANDARD_EDGIO</strong></code> Standard tier provided by Verizon. Suitable for general content delivery needs
     *      with a balance of performance and cost.
     *   - <code><strong>PREMIUM_EDGIO</strong></code> Premium tier provided by Verizon, offering advanced security features and
     *      analytics on top of the Standard tier services.
     *   - <code><strong>STANDARD_MICROSOFT</strong></code> Standard tier provided by Microsoft, offering competitive content delivery
     *      services with integrated Azure features.
     *   - <code><strong>STANDARD_AZURE_FRONT_DOOR</strong></code> A variant of Azure Front Door services in the standard tier, optimized
     *      for global and dynamic content acceleration.
     *   - <code><strong>PREMIUM_AZURE_FRONT_DOOR</strong></code> The premium version of Azure Front Door services, providing additional
     *      security and optimization features for highly dynamic and secure applications.</pre>
     * 
     * @important.note: Direct SKU updates post-creation of the CDN profile are not supported through this SDK. Changing the SKU
     *     requires creating a new CDN profile with the desired SKU and, if necessary, deleting the old one. 
     *     Selecting the correct SKU is crucial for aligning service capabilities with your application needs and
     *     budget. Review the detailed comparison of features and pricing in Azure CDN documentation to make an
     *     informed choice.
     * @param sku The desired SKU for the CDN Profile, selected from the predefined <code>AzureCdnSku</code> values.
     * @return The builder instance for chaining further configuration calls.
     */
    public AzureCdnProfileBuilder withSku(AzureCdnSku sku) {
      component.setSku(sku);
      return builder;
    }

    /**
     * <pre>
     * Configures the Azure region where the CDN Profile will be deployed. The region is a critical
     * configuration that dictates the geographical location of the deployed resources, affecting
     * latency, availability, and compliance. Utilizing the {@link AzureRegion} allows for flexibility in specifying 
     * regions, accommodating cases where specific regions might not be explicitly listed in the enum.
     *
     * In scenarios where automation scripts or tools require dynamic region specification or if a
     * particular region is known to exist but is not present in the {@link AzureRegion} enum,
     * <code>AzureRegion.fromString(String name)</code> can be used to extend the enum dynamically.
     * This approach ensures that the builder remains compatible with future Azure regions without
     * needing constant updates.
     *
     * Usage example:
     * builder.withRegion(AzureRegion.EAST_US); // Using a predefined region
     * builder.withRegion(AzureRegion.fromString("custom_region_name")); // Extending the enum for a custom or future region
     * </pre>
     * @param region the Azure region as an {@link AzureRegion} where the Web App will be deployed.
     *               This can either be one of the predefined regions or an extended enum value
     *               created through <code>AzureRegion.fromString(String name)</code>.
     * @return the <code>AzureWebAppBuilder</code> instance for chaining further configuration calls.
     */
    public AzureCdnProfileBuilder withRegion(AzureRegion region) {
      component.setAzureRegion(region);
      return builder;
    }

    /**
     * <pre>
     * Sets the Azure resource group for the CDN Profile. The resource group name must adhere to Azure's
     * naming conventions, specifically matching the regex pattern <code>^[-\w\._\(\)]+[^\.]$</code>, which
     * ensures the name is valid and conforms to Azure's standards for resource naming.
     *
     * The resource group is a crucial organizational unit within Azure that holds related resources for
     * an Azure solution. Specifying the correct resource group is essential for resource management and
     * billing. This method enables setting the resource group to which the CDN Profile belongs.
     *
     * Example usage:
     *
     * {@code AzureResourceGroup.builder()
     *   .withName("rg-sample-group")
     *   .withRegion(AzureRegion.WEST_EUROPE)
     * .build();}
     * </pre>
     *
     * @param resourceGroup the <code>AzureResourceGroup</code> representing the resource group to which the
     *                      Web App will belong.
     * @return the <code>AzureWebAppBuilder</code> instance for chaining further configuration calls.
     */
    public AzureCdnProfileBuilder withResourceGroup(AzureResourceGroup resourceGroup) {
      component.setAzureResourceGroup(resourceGroup);
      return builder;
    }

    public AzureCdnProfile build(){
      component.setType(PAAS_CDN_PROFILE);
      return super.build();
    }
    
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    if (identity != null && identity.getType() == null) {
      errors.add("[AzureCdnProfile Validation] Type is required when identity is provided.");
    }

    var nameToValidate = name != null ? name : getId().getValue();
    if (nameToValidate != null){
      if((nameToValidate.isEmpty() || nameToValidate.length() > 260)) {
        errors.add("[AzureCdnProfile Validation] The profile name must be between 1 and 260 characters long.");
      }

      if (!NAME_PATTERN.matcher(nameToValidate).matches()) {
        errors.add("[AzureCdnProfile Validation] The profile name must start and end with a letter or number and can only contain letters, numbers, and hyphens.");
      }
    }
    
    var isSkuAllowed = sku == AzureCdnSku.STANDARD_AZURE_FRONT_DOOR || sku == AzureCdnSku.PREMIUM_AZURE_FRONT_DOOR;
    if (originResponseTimeoutSeconds != null && !isSkuAllowed) {
      errors.add("[AzureCdnProfile Validation] OriginResponseTimeoutSeconds can only be provided when sku is STANDARD_AZURE_FRONT_DOOR or PREMIUM_AZURE_FRONT_DOOR.");
    }

    if (originResponseTimeoutSeconds != null) {
      if (originResponseTimeoutSeconds < 16) {
        errors.add("[AzureCdnProfile Validation] Property 'OriginResponseTimeoutSeconds' must be 16 or greater.");
      } else if (originResponseTimeoutSeconds > 240) {
        errors.add("[AzureCdnProfile Validation] Property 'OriginResponseTimeoutSeconds' exceeds the allowed limit of 240.");
      }
    }
    
    return errors;
  }
}
