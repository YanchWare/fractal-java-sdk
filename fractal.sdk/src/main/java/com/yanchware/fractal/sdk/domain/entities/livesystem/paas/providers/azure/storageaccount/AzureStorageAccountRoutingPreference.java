package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountRoutingPreference {

  private Boolean publishInternetEndpoints;
  private Boolean publishMicrosoftEndpoints;
  private AzureRoutingChoice routingChoice;
  

  public static AzureStorageAccountRoutingPreferenceBuilder builder() {
    return new AzureStorageAccountRoutingPreferenceBuilder();
  }

  public static class AzureStorageAccountRoutingPreferenceBuilder {
    private final AzureStorageAccountRoutingPreference instance;
    private final AzureStorageAccountRoutingPreferenceBuilder builder;

    public AzureStorageAccountRoutingPreferenceBuilder() {
      this.instance = new AzureStorageAccountRoutingPreference();
      this.builder = this;
    }

    /**
     * A boolean flag which indicates whether internet routing storage endpoints are to be published
     */
    public AzureStorageAccountRoutingPreferenceBuilder withPublishInternetEndpoints(Boolean publishInternetEndpoints) {
      instance.setPublishInternetEndpoints(publishInternetEndpoints);
      return builder;
    }
    
    /**
     * A boolean flag which indicates whether microsoft routing storage endpoints are to be published
     */
    public AzureStorageAccountRoutingPreferenceBuilder withPublishMicrosoftEndpoints(Boolean publishMicrosoftEndpoints) {
      instance.setPublishMicrosoftEndpoints(publishMicrosoftEndpoints);
      return builder;
    }
    
    /**
     * Routing Choice defines the kind of network routing opted by the user.
     */
    public AzureStorageAccountRoutingPreferenceBuilder withRoutingChoice(AzureRoutingChoice routingChoice) {
      instance.setRoutingChoice(routingChoice);
      return builder;
    }
    
    public AzureStorageAccountRoutingPreference build() {
      return instance;
    }
  }
}
