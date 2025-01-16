package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public interface AzureCosmosEntity extends AzureResourceEntity {
  static Collection<String> validateCosmosEntity(AzureCosmosEntity cosmosEntity) {
    final var MAX_THROUGHPUT_AND_THROUGHPUT_ARE_DEFINED_TEMPLATE = "[Cosmos %s Validation] Defined both throughput " +
      "and max throughput. Only one of them can be defined and not both";
    final var NAME_IS_NOT_VALID_TEMPLATE = "[Cosmos %s Validation] The Name is invalid. Ensure to provide a unique " +
      "non-empty string less than '255' characters";

    var errors = AzureResourceEntity.validateAzureResourceEntity(cosmosEntity, cosmosEntity.getEntityName());

    var throughput = cosmosEntity.getThroughput();
    var maxThroughput = cosmosEntity.getMaxThroughput();
    if (maxThroughput > 0 && throughput > 0) {
      errors.add(String.format(MAX_THROUGHPUT_AND_THROUGHPUT_ARE_DEFINED_TEMPLATE, cosmosEntity.getEntityName()));
    }

    var name = cosmosEntity.getName();
    if (StringUtils.isNotBlank(name) && name.length() > 254) {
      errors.add(String.format(NAME_IS_NOT_VALID_TEMPLATE, cosmosEntity.getEntityName()));
    }

    return errors;
  }

  int getThroughput();

  void setThroughput(int throughput);

  int getMaxThroughput();

  void setMaxThroughput(int maxThroughput);

  String getEntityName();
}