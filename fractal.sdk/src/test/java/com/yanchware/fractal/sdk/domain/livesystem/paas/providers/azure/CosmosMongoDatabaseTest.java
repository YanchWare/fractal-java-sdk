
package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.AzureCosmosMongoDatabase;
import com.yanchware.fractal.sdk.domain.values.ComponentType;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_MONGO_DATABASE;

public class CosmosMongoDatabaseTest extends CosmosEntityTest<AzureCosmosMongoDatabase.AzureCosmosMongoDatabaseBuilder> {


  @Override
  AzureCosmosMongoDatabase.AzureCosmosMongoDatabaseBuilder getBuilder() {
    return new AzureCosmosMongoDatabase.AzureCosmosMongoDatabaseBuilder();
  }

  @Override
  ComponentType getExpectedType() {
    return PAAS_COSMOS_MONGO_DATABASE;
  }
}