
package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.AzureCosmosTableDbms;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.AzureCosmosTableEntity;
import com.yanchware.fractal.sdk.domain.values.ComponentType;

import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_ACCOUNT;

public class CosmosTableAccountTest extends CosmosAccountTest<AzureCosmosTableDbms.AzureCosmosTableDbmsBuilder> {


  @Override
  AzureCosmosTableDbms.AzureCosmosTableDbmsBuilder getBuilder() {
    return new AzureCosmosTableDbms.AzureCosmosTableDbmsBuilder();
  }

  @Override
  ComponentType getExpectedType() {
    return PAAS_COSMOS_ACCOUNT;
  }

  @Override
  AzureCosmosTableEntity getInvalidCosmosEntity() {
    return new AzureCosmosTableEntity();
  }

  @Override
  Collection<AzureCosmosTableEntity> getValidCosmosEntities() {
    return List.of(
      aTable("db-a"),
      aTable("db-b"),
      aTable("db-c"));
  }

  private AzureCosmosTableEntity aTable(String id) {
    var throughput = a(Integer.class);

    return AzureCosmosTableEntity.builder()
      .withId(id).build();
  }
}