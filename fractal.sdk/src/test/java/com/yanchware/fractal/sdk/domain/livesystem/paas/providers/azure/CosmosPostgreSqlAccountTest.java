
package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.AzureCosmosPostgreSqlDatabase;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.AzureCosmosPostgreSqlDbms;
import com.yanchware.fractal.sdk.domain.values.ComponentType;

import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_ACCOUNT;

public class CosmosPostgreSqlAccountTest extends CosmosAccountTest<AzureCosmosPostgreSqlDbms.AzureCosmosPostgreSqlDbmsBuilder> {


  @Override
  AzureCosmosPostgreSqlDbms.AzureCosmosPostgreSqlDbmsBuilder getBuilder() {
    return new AzureCosmosPostgreSqlDbms.AzureCosmosPostgreSqlDbmsBuilder();
  }

  @Override
  ComponentType getExpectedType() {
    return PAAS_COSMOS_ACCOUNT;
  }

  @Override
  AzureCosmosPostgreSqlDatabase getInvalidCosmosEntity() {
    return new AzureCosmosPostgreSqlDatabase();
  }

  @Override
  Collection<AzureCosmosPostgreSqlDatabase> getValidCosmosEntities() {
    return List.of(
      aPostgreSqlDb("db-a"),
      aPostgreSqlDb("db-b"),
      aPostgreSqlDb("db-c"));
  }

  private AzureCosmosPostgreSqlDatabase aPostgreSqlDb(String id) {
    var throughput = a(Integer.class);

    return AzureCosmosPostgreSqlDatabase.builder()
      .withId(id).build();
  }
}