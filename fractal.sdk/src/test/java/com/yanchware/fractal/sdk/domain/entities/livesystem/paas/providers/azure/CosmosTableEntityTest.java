
package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.valueobjects.ComponentType;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_TABLE;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_POSTGRESQL_DATABASE;

public class CosmosTableEntityTest extends CosmosEntityTest<AzureCosmosTableEntity.AzureCosmosTableEntityBuilder> {


  @Override
  AzureCosmosTableEntity.AzureCosmosTableEntityBuilder getBuilder() {
    return new AzureCosmosTableEntity.AzureCosmosTableEntityBuilder();
  }

  @Override
  ComponentType getExpectedType() {
    return PAAS_COSMOS_TABLE;
  }
}