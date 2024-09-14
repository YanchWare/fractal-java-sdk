
package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosMongoDatabase;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosMongoDbms;
import com.yanchware.fractal.sdk.domain.values.ComponentType;

import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_ACCOUNT;

public class CosmosMongoAccountTest extends CosmosAccountTest<AzureCosmosMongoDbms.AzureCosmosMongoDbmsBuilder> {


  @Override
  AzureCosmosMongoDbms.AzureCosmosMongoDbmsBuilder getBuilder() {
    return new AzureCosmosMongoDbms.AzureCosmosMongoDbmsBuilder();
  }

  @Override
  ComponentType getExpectedType() {
    return PAAS_COSMOS_ACCOUNT;
  }

  @Override
  AzureCosmosMongoDatabase getInvalidCosmosEntity() {
    return new AzureCosmosMongoDatabase();
  }

  @Override
  Collection<AzureCosmosMongoDatabase> getValidCosmosEntities() {
    return List.of(
      aMongoDb("db-a"),
      aMongoDb("db-b"),
      aMongoDb("db-c"));
  }

  private AzureCosmosMongoDatabase aMongoDb(String id) {
    var throughput = a(Integer.class);

    return AzureCosmosMongoDatabase.builder()
      .withId(id).build();
  }
}