package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cdn;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSNetworkCdnProfile;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;

/**
 * App Service plans give you the flexibility to allocate specific apps to a given set of resources 
 * and further optimize your Azure resource utilization. This way, if you want to save money on your 
 * testing environment you can share a plan across multiple apps.
 */

@Getter
@Setter
@NoArgsConstructor
public class AzureCdnProfile extends PaaSNetworkCdnProfile implements AzureResourceEntity, LiveSystemComponent {

  private final static String NAME_NOT_VALID = "[AzureCdnProfile Validation] The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters";
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

    public AzureCdnProfileBuilder withIdentity(AzureManagedServiceIdentity identity) {
      component.setIdentity(identity);
      return builder;
    }

    public AzureCdnProfileBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    public AzureCdnProfileBuilder withOriginResponseTimeoutSeconds(Integer originResponseTimeoutSeconds) {
      component.setOriginResponseTimeoutSeconds(originResponseTimeoutSeconds);
      return builder;
    }

    public AzureCdnProfileBuilder withSku(AzureCdnSku sku) {
      component.setSku(sku);
      return builder;
    }

    public AzureCdnProfile build(){
      return super.build();
    }
    
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    
    
    return errors;
  }
}
