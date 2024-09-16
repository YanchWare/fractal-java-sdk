
package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.AzureCosmosNoSqlDatabase;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.AzureCosmosNoSqlDbms;
import com.yanchware.fractal.sdk.domain.values.ComponentType;

import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_ACCOUNT;

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
      .withId(id).build();
  }
}