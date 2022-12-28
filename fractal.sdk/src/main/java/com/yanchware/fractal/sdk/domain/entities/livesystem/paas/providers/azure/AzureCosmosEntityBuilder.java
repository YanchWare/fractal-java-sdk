package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.Component;

public abstract class AzureCosmosEntityBuilder<T extends Component, B extends AzureCosmosEntityBuilder<T, B>> extends Component.Builder<T, B>{

  public B withCosmosAccount(String cosmosAccount) {
    if (component instanceof AzureCosmosEntity){
      ((AzureCosmosEntity) component).setCosmosAccount(cosmosAccount);
    }

    return builder;
  }

  public B withThroughput(int throughput) {
    if (component instanceof AzureCosmosEntity){
      ((AzureCosmosEntity) component).setThroughput(throughput);
    }

    return builder;
  }

  public B withMaxThroughput(int maxThroughput) {
    if (component instanceof AzureCosmosEntity){
      ((AzureCosmosEntity) component).setMaxThroughput(maxThroughput);
    }

    return builder;
  }
}
