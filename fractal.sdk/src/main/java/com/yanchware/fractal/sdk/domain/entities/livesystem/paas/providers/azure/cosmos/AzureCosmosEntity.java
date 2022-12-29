package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureEntity;

import java.util.Collection;

public interface AzureCosmosEntity extends AzureEntity {
  int getThroughput();
  void setThroughput(int throughput);

  int getMaxThroughput();
  void setMaxThroughput(int maxThroughput);

  String getEntityName();

  static Collection<String> validateCosmosEntity(AzureCosmosEntity cosmosEntity) {
    final var MAX_THROUGHPUT_AND_THROUGHPUT_ARE_DEFINED_TEMPLATE = "[Cosmos %s Validation] Defined both throughput and max throughput. Only one of them can be defined and not both";

    var errors = AzureEntity.validateAzureEntity(cosmosEntity, cosmosEntity.getEntityName());

    var throughput = cosmosEntity.getThroughput();
    var maxThroughput = cosmosEntity.getMaxThroughput();
    if (maxThroughput > 0 && throughput > 0) {
      errors.add(String.format(MAX_THROUGHPUT_AND_THROUGHPUT_ARE_DEFINED_TEMPLATE, cosmosEntity.getEntityName()));
    }

    return errors;
  }
}