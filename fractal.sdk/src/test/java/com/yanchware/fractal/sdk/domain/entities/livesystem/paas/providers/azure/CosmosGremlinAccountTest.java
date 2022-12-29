
package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosGremlinDatabase;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosGremlinDbms;
import com.yanchware.fractal.sdk.valueobjects.ComponentType;

import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_ACCOUNT;

public class CosmosGremlinAccountTest extends CosmosAccountTest<AzureCosmosGremlinDbms.AzureCosmosGremlinDbmsBuilder> {


  @Override
  AzureCosmosGremlinDbms.AzureCosmosGremlinDbmsBuilder getBuilder() {
    return new AzureCosmosGremlinDbms.AzureCosmosGremlinDbmsBuilder();
  }

  @Override
  ComponentType getExpectedType() {
    return PAAS_COSMOS_ACCOUNT;
  }

  @Override
  AzureCosmosGremlinDatabase getInvalidCosmosEntity() {
    return new AzureCosmosGremlinDatabase();
  }

  @Override
  Collection<AzureCosmosGremlinDatabase> getValidCosmosEntities() {
    return List.of(
      aGremlinDb("db-a"),
      aGremlinDb("db-b"),
      aGremlinDb("db-c"));
  }

  private AzureCosmosGremlinDatabase aGremlinDb(String id) {
    return AzureCosmosGremlinDatabase.builder()
      .withId(id).build();
  }
}