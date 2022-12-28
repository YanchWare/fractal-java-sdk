package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import java.util.ArrayList;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class AzureCosmosUtilities {

  private final static String COSMOS_ACCOUNT_IS_BLANK_TEMPLATE = "Cosmos %s defined no connection to a Cosmos Account, and it is required";
  private final static String THROUGHPUT_IS_BLANK_TEMPLATE = "Cosmos %s defined no throughput, and it is required";
  private final static String MAX_THROUGHPUT_IS_BLANK_TEMPLATE = "Cosmos %s defined no max throughput defined, and it is required";
  private final static String MAX_THROUGHPUT_IS_SMALLER_TEMPLATE = "Cosmos %s has max throughput defined, but it is less than base throughput";

  public static Collection<String> validateCosmosEntity(AzureCosmosEntity cosmosEntity, String entityName) {
    var errors = new ArrayList<String>();

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

  public static Collection<String> validateCosmosAccount(AzureCosmosAccount cosmosAccount) {
    var errors = new ArrayList<String>();

    cosmosAccount.getCosmosEntities().stream()
      .map(AzureCosmosEntity::validate)
      .forEach(errors::addAll);

    return errors;
  }
}