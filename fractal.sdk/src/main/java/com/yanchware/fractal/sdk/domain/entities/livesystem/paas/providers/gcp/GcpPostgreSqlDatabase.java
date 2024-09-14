package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.gcp;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlDatabase;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;

public class GcpPostgreSqlDatabase extends PaaSPostgreSqlDatabase {
  private final static String SCHEMA_IS_BLANK = "[GcpPostgreSqlDatabase ] schema defined was either empty or blank and it is required";

  private String name;
  
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