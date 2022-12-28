package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureEntity;

import java.util.Collection;

public interface AzureCosmosAccount extends AzureEntity {

  <C extends Component & AzureCosmosEntity> Collection<C> getCosmosEntities();
  int getMaxTotalThroughput();
  void setMaxTotalThroughput(int maxTotalThroughput);
  void setPublicNetworkAccess(String publicNetworkAccessMode);

  static Collection<String> validateCosmosAccount(AzureCosmosAccount cosmosAccount) {
    final var MAX_TOTAL_THROUGHPUT_IS_BLANK = "[Cosmos Account Validation] Defined no max total throughput defined, and it is required";

    var errors = AzureEntity.validateAzureEntity(cosmosAccount, "Cosmos Account");

    var maxTotalThroughput = cosmosAccount.getMaxTotalThroughput();
    if (maxTotalThroughput <= 0) {
      errors.add(String.format(MAX_TOTAL_THROUGHPUT_IS_BLANK));
    }

    cosmosAccount.getCosmosEntities().stream()
      .map(AzureCosmosEntity::validate)
      .forEach(errors::addAll);

    return errors;
  }
}
