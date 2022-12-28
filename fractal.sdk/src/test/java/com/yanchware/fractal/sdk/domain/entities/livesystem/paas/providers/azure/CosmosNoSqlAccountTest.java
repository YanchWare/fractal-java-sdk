
package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosNoSqlDatabase;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosNoSqlDbms;
import com.yanchware.fractal.sdk.valueobjects.ComponentType;

import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_ACCOUNT;

public class CosmosNoSqlAccountTest extends CosmosAccountTest<AzureCosmosNoSqlDbms.AzureCosmosNoSqlDbmsBuilder> {


  @Override
  AzureCosmosNoSqlDbms.AzureCosmosNoSqlDbmsBuilder getBuilder() {
    return new AzureCosmosNoSqlDbms.AzureCosmosNoSqlDbmsBuilder();
  }

  @Override
  ComponentType getExpectedType() {
    return PAAS_COSMOS_ACCOUNT;
  }

  @Override
  AzureCosmosNoSqlDatabase getInvalidCosmosEntity() {
    return new AzureCosmosNoSqlDatabase();
  }

  @Override
  Collection<AzureCosmosNoSqlDatabase> getValidCosmosEntities() {
    return List.of(
      aNoSqlDb("db-a"),
      aNoSqlDb("db-b"),
      aNoSqlDb("db-c"));
  }

  private AzureCosmosNoSqlDatabase aNoSqlDb(String id) {
    var throughput = a(Integer.class);

    return AzureCosmosNoSqlDatabase.builder()
      .withCosmosAccount(a(String.class))
      .withThroughput(throughput)
      .withMaxThroughput(throughput + 1)
      .withId(id).build();
  }
}