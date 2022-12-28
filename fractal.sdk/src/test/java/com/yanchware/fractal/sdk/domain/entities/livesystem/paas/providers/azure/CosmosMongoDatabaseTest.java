
package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.valueobjects.ComponentType;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_GREMLIN_DATABASE;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_MONGO_DATABASE;

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