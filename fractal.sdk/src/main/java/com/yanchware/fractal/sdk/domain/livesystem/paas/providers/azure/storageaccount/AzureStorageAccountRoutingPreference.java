package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * Routing preference defines the type of network.
 * Either microsoft or internet routing to be used to deliver the user data.
 * The default option is microsoft routing.
 * </pre>
 */
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
     * <pre>
     * A boolean flag which indicates whether internet routing storage endpoints are to be published
     * </pre>
     */
    public AzureStorageAccountRoutingPreferenceBuilder withPublishInternetEndpoints(Boolean publishInternetEndpoints) {
      instance.setPublishInternetEndpoints(publishInternetEndpoints);
      return builder;
    }
    
    /**
     * <pre>
     * A boolean flag which indicates whether microsoft routing storage endpoints are to be published
     * </pre>
     */
    public AzureStorageAccountRoutingPreferenceBuilder withPublishMicrosoftEndpoints(Boolean publishMicrosoftEndpoints) {
      instance.setPublishMicrosoftEndpoints(publishMicrosoftEndpoints);
      return builder;
    }
    
    /**
     * <pre>
     * Routing Choice defines the kind of network routing opted by the user.
     * </pre>
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
