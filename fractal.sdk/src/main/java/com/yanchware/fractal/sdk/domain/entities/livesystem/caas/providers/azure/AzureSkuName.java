package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureSkuName {
  B_GEN5_1("B_Gen5_1"),
  B_GEN5_2("B_Gen5_2"),
  GP_GEN5_2("GP_Gen5_2"),
  GP_GEN5_4("GP_Gen5_4"),
  GP_GEN5_8("GP_Gen5_8"),
  GP_GEN5_16("GP_Gen5_16"),
  GP_GEN5_32("GP_Gen5_32"),
  GP_GEN5_64("GP_Gen5_64"),
  MO_GEN5_2("MO_Gen5_2"),
  MO_GEN5_4("MO_Gen5_4"),
  MO_GEN5_8("MO_Gen5_8"),
  MO_GEN5_16("MO_Gen5_16"),
  MO_GEN5_32("MO_Gen5_32");

  private final String id;

  AzureSkuName(final String id) {
    this.id = id;
  }

  @JsonValue
  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return id;
  }
}
