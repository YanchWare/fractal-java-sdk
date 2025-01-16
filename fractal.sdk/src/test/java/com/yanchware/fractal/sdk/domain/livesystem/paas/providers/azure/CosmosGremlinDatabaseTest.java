package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.AzureCosmosGremlinDatabase;
import com.yanchware.fractal.sdk.domain.values.ComponentType;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_GREMLIN_DATABASE;

public class CosmosGremlinDatabaseTest extends CosmosEntityTest<AzureCosmosGremlinDatabase.AzureCosmosGremlinDatabaseBuilder> {


  @Override
  AzureCosmosGremlinDatabase.AzureCosmosGremlinDatabaseBuilder getBuilder() {
    return new AzureCosmosGremlinDatabase.AzureCosmosGremlinDatabaseBuilder();
  }

  @Override
  ComponentType getExpectedType() {
    return PAAS_COSMOS_GREMLIN_DATABASE;
  }
}