
package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosTableEntity;
import com.yanchware.fractal.sdk.domain.values.ComponentType;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_TABLE;

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