package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;

import java.util.Collection;

public interface AzureCosmosEntity {

  String getName();
  void setName(String name);

  String getCosmosAccount();
  void setCosmosAccount(String cosmosAccount);

  int getThroughput();
  void setThroughput(int throughput);

  int getMaxThroughput();
  void setMaxThroughput(int maxThroughput);
  Collection<String> validate();
}