
package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.valueobjects.ComponentType;

import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_ACCOUNT;

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
      .withCosmosAccount(a(String.class))
      .withThroughput(throughput)
      .withMaxThroughput(throughput + 1)
      .withId(id).build();
  }
}