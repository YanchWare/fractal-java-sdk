package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.Component;

import java.util.Collection;

public interface AzureCosmosAccount {

  <C extends Component & AzureCosmosEntity> Collection<C> getCosmosEntities();
  int getMaxTotalThroughput();

  void setMaxTotalThroughput(int maxTotalThroughput);
  void setPublicNetworkAccess(String publicNetworkAccessMode);
}
