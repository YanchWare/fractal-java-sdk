package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureConnectionStringInfo {
  /**
   * Connection string value.
   */
  private String connectionString;
  /**
   * Name of connection string.
   */
  private String name;

  /**
   * Type of database.
   */
  private AzureConnectionStringType databaseType;
}
