
package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosPostgreSqlDatabase;
import com.yanchware.fractal.sdk.domain.values.ComponentType;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_POSTGRESQL_DATABASE;

public class CosmosPostgreDatabaseTest extends CosmosEntityTest<AzureCosmosPostgreSqlDatabase.AzureCosmosPostgreSqlDatabaseBuilder> {


  @Override
  AzureCosmosPostgreSqlDatabase.AzureCosmosPostgreSqlDatabaseBuilder getBuilder() {
    return new AzureCosmosPostgreSqlDatabase.AzureCosmosPostgreSqlDatabaseBuilder();
  }

  @Override
  ComponentType getExpectedType() {
    return PAAS_COSMOS_POSTGRESQL_DATABASE;
  }
}