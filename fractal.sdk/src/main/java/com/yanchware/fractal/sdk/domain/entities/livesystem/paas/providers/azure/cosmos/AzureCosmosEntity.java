package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureEntity;

import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface AzureCosmosEntity extends AzureEntity {
  String getCosmosAccount();
  void setCosmosAccount(String cosmosAccount);

  int getThroughput();
  void setThroughput(int throughput);

  int getMaxThroughput();
  void setMaxThroughput(int maxThroughput);

  String getEntityName();

  static Collection<String> validateCosmosEntity(AzureCosmosEntity cosmosEntity) {
    final var COSMOS_ACCOUNT_IS_BLANK_TEMPLATE = "[Cosmos %s Validation] Defined no connection to a Cosmos Account, and it is required";
    final var MAX_THROUGHPUT_AND_THROUGHPUT_ARE_DEFINED_TEMPLATE = "[Cosmos %s Validation] Defined both throughput and max throughput. Only one of them can be defined and not both";

    var errors = AzureEntity.validateAzureEntity(cosmosEntity, cosmosEntity.getEntityName());

    if (isBlank(cosmosEntity.getCosmosAccount())) {
      errors.add(String.format(COSMOS_ACCOUNT_IS_BLANK_TEMPLATE, cosmosEntity.getEntityName()));
    }

    var throughput = cosmosEntity.getThroughput();
    var maxThroughput = cosmosEntity.getMaxThroughput();
    if (maxThroughput > 0 && throughput > 0) {
      errors.add(String.format(MAX_THROUGHPUT_AND_THROUGHPUT_ARE_DEFINED_TEMPLATE, cosmosEntity.getEntityName()));
    }

    return errors;
  }
}