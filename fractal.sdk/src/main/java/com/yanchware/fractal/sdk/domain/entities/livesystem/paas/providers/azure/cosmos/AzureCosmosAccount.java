package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureEntity;

import java.util.Collection;

public interface AzureCosmosAccount extends AzureEntity {

  <C extends Component & AzureCosmosEntity> Collection<C> getCosmosEntities();
  int getMaxTotalThroughput();
  void setMaxTotalThroughput(int maxTotalThroughput);
  void setPublicNetworkAccess(String publicNetworkAccessMode);

  static Collection<String> validateCosmosAccount(AzureCosmosAccount cosmosAccount, String accountType) {
    var errors = AzureEntity.validateAzureEntity(cosmosAccount, accountType);

    cosmosAccount.getCosmosEntities().stream()
      .map(AzureCosmosEntity::validateCosmosEntity)
      .forEach(errors::addAll);

    return errors;
  }
}
