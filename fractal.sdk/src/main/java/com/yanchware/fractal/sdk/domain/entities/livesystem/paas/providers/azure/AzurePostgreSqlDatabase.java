package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlDatabase;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;

public class AzurePostgreSqlDatabase extends PaaSPostgreSqlDatabase {
  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static AzurePostgreSqlDbBuilder builder() {
    return new AzurePostgreSqlDbBuilder();
  }

  public static class AzurePostgreSqlDbBuilder extends PaaSPostgreSqlDatabase.Builder<AzurePostgreSqlDatabase, AzurePostgreSqlDatabase.AzurePostgreSqlDbBuilder> {

      @Override
      protected AzurePostgreSqlDbBuilder getBuilder() {
        return this;
      }

      @Override
      protected AzurePostgreSqlDatabase createComponent() {
        return new AzurePostgreSqlDatabase();
      }
    }

}