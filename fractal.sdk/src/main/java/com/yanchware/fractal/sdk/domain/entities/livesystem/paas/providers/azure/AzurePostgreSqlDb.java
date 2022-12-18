package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlDbImpl;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;

public class AzurePostgreSqlDb extends PaaSPostgreSqlDbImpl {
  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static AzurePostgreSqlDbBuilder builder() {
    return new AzurePostgreSqlDbBuilder();
  }

  public static class AzurePostgreSqlDbBuilder extends PaaSPostgreSqlDbImpl.Builder<AzurePostgreSqlDb, AzurePostgreSqlDb.AzurePostgreSqlDbBuilder> {

      @Override
      protected AzurePostgreSqlDbBuilder getBuilder() {
        return this;
      }

      @Override
      protected AzurePostgreSqlDb createComponent() {
        return new AzurePostgreSqlDb();
      }
    }

}