package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

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
  String connectionString;
  /**
   * Name of connection string.
   */
  String name;

  /**
   * Type of database.
   */
  AzureConnectionStringType databaseType;
}
