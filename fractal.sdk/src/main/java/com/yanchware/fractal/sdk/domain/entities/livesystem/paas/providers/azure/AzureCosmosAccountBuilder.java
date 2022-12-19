package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.Component;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_ACCOUNT;

public abstract class AzureCosmosAccountBuilder<T extends Component, B extends Component.Builder<T, B>> extends Component.Builder<T, B> {

  private static final String PUBLIC_NETWORK_ACCESS_ENABLED_MODE = "Enabled";
  private static final String PUBLIC_NETWORK_ACCESS_DISABLED_MODE = "Disabled";

  public B withMaxTotalThroughput(int maxTotalThroughput) {
    if (component instanceof AzureCosmosAccount){
      ((AzureCosmosAccount) component).setMaxTotalThroughput(maxTotalThroughput);
    }

    return builder;
  }

  public B enablePublicNetworkAccess() {
    if (component instanceof AzureCosmosAccount){
      ((AzureCosmosAccount) component).setPublicNetworkAccess(PUBLIC_NETWORK_ACCESS_ENABLED_MODE);
    }

    return builder;
  }

  public B disablePublicNetworkAccess() {
    if (component instanceof AzureCosmosAccount){
      ((AzureCosmosAccount) component).setPublicNetworkAccess(PUBLIC_NETWORK_ACCESS_DISABLED_MODE);
    }

    return builder;
  }

  @Override
  public T build() {
    component.setType(PAAS_COSMOS_ACCOUNT);
    return super.build();
  }
}
