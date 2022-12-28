package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureEntity;

import java.util.ArrayList;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface AzureCosmosEntity extends AzureEntity {
  String getCosmosAccount();
  void setCosmosAccount(String cosmosAccount);

  int getThroughput();
  void setThroughput(int throughput);

  int getMaxThroughput();
  void setMaxThroughput(int maxThroughput);

  static Collection<String> validateCosmosEntity(AzureCosmosEntity cosmosEntity, String entityName) {
    final var COSMOS_ACCOUNT_IS_BLANK_TEMPLATE = "[Cosmos %s Validation] Defined no connection to a Cosmos Account, and it is required";
    final var THROUGHPUT_IS_BLANK_TEMPLATE = "[Cosmos %s Validation] Defined no throughput, and it is required";
    final var MAX_THROUGHPUT_IS_BLANK_TEMPLATE = "[Cosmos %s Validation] Defined no max throughput, and it is required";
    final var MAX_THROUGHPUT_IS_SMALLER_TEMPLATE = "[Cosmos %s Validation] Defined max throughput defined, but it is less than base throughput";

    var errors = AzureEntity.validateAzureEntity(cosmosEntity, entityName);

    if (isBlank(cosmosEntity.getCosmosAccount())) {
      errors.add(String.format(COSMOS_ACCOUNT_IS_BLANK_TEMPLATE, entityName));
    }

    var throughput = cosmosEntity.getThroughput();
    if (throughput <= 0) {
      errors.add(String.format(THROUGHPUT_IS_BLANK_TEMPLATE, entityName));
    }

    var maxThroughput = cosmosEntity.getMaxThroughput();
    if (maxThroughput <= 0) {
      errors.add(String.format(MAX_THROUGHPUT_IS_BLANK_TEMPLATE, entityName));
    }

    if (maxThroughput < throughput) {
      errors.add(String.format(MAX_THROUGHPUT_IS_SMALLER_TEMPLATE, entityName));
    }

    return errors;
  }
}