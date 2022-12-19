package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import java.util.Collection;

public interface AzureCosmosAccount {

  Collection<AzureCosmosEntity> getCosmosEntities();
  void setMaxTotalThroughput(int maxTotalThroughput);
  void setPublicNetworkAccess(String publicNetworkAccessMode);
}
