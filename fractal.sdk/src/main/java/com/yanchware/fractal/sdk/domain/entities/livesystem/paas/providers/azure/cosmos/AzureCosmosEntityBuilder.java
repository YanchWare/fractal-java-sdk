package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;

public abstract class AzureCosmosEntityBuilder<T extends Component, B extends AzureCosmosEntityBuilder<T, B>> extends Component.Builder<T, B>{

  public B withCosmosAccount(ComponentId cosmosAccount) {
    if (component instanceof AzureCosmosEntity){
      component.getDependencies().add(cosmosAccount);
    }

    return builder;
  }

  public B withName(String name) {
    if (component instanceof AzureCosmosEntity){
      ((AzureCosmosEntity) component).setName(name);
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
