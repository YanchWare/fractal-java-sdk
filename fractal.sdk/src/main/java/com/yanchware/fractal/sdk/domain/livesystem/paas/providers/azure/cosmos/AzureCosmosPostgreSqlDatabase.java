package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.livesystem.paas.PaaSPostgreSqlDatabase;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_POSTGRESQL_DATABASE;

public class AzureCosmosPostgreSqlDatabase extends PaaSPostgreSqlDatabase {

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static final String TYPE = PAAS_COSMOS_POSTGRESQL_DATABASE.getId();

  public static AzureCosmosPostgreSqlDatabaseBuilder builder() {
    return new AzureCosmosPostgreSqlDatabaseBuilder();
  }

  public static class AzureCosmosPostgreSqlDatabaseBuilder extends PaaSPostgreSqlDatabase.Builder<AzureCosmosPostgreSqlDatabase, AzureCosmosPostgreSqlDatabaseBuilder> {
    @Override
    protected AzureCosmosPostgreSqlDatabase createComponent() {
      return new AzureCosmosPostgreSqlDatabase();
    }

    @Override
    protected AzureCosmosPostgreSqlDatabaseBuilder getBuilder() {
      return this;
    }

    @Override
    public AzureCosmosPostgreSqlDatabase build() {
      super.build();
      component.setType(PAAS_COSMOS_POSTGRESQL_DATABASE);
      return component;
    }
  }
}
