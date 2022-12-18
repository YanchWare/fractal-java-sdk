package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.gcp;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlDbImpl;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;

public class GcpPostgreSqlDb extends PaaSPostgreSqlDbImpl {
  @Override
  public ProviderType getProvider() {
    return ProviderType.GCP;
  }

  public static GcpPostgreSqlDbBuilder builder() {
    return new GcpPostgreSqlDbBuilder();
  }

  public static class GcpPostgreSqlDbBuilder extends Builder<GcpPostgreSqlDb, GcpPostgreSqlDb.GcpPostgreSqlDbBuilder> {

      @Override
      protected GcpPostgreSqlDbBuilder getBuilder() {
        return this;
      }

      @Override
      protected GcpPostgreSqlDb createComponent() {
        return new GcpPostgreSqlDb();
      }
    }

}