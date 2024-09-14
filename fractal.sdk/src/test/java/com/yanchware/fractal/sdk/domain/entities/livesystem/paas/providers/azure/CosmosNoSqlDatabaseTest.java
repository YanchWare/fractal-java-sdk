
package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosNoSqlDatabase;
import com.yanchware.fractal.sdk.domain.values.ComponentType;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_NOSQL_DATABASE;

public class CosmosNoSqlDatabaseTest extends CosmosEntityTest<AzureCosmosNoSqlDatabase.AzureCosmosNoSqlDatabaseBuilder> {


  @Override
  AzureCosmosNoSqlDatabase.AzureCosmosNoSqlDatabaseBuilder getBuilder() {
    return new AzureCosmosNoSqlDatabase.AzureCosmosNoSqlDatabaseBuilder();
  }

  @Override
  ComponentType getExpectedType() {
    return PAAS_COSMOS_NOSQL_DATABASE;
  }
}