package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.gcp;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlDatabase;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;

public class GcpPostgreSqlDatabase extends PaaSPostgreSqlDatabase {
  @Override
  public ProviderType getProvider() {
    return ProviderType.GCP;
  }

  public static GcpPostgreSqlDbBuilder builder() {
    return new GcpPostgreSqlDbBuilder();
  }

  public static class GcpPostgreSqlDbBuilder extends Builder<GcpPostgreSqlDatabase, GcpPostgreSqlDatabase.GcpPostgreSqlDbBuilder> {

      @Override
      protected GcpPostgreSqlDbBuilder getBuilder() {
        return this;
      }

      @Override
      protected GcpPostgreSqlDatabase createComponent() {
        return new GcpPostgreSqlDatabase();
      }
    }

}