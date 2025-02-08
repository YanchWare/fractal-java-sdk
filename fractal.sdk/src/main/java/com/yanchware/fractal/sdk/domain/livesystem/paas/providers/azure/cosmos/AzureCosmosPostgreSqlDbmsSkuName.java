package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public class AzureCosmosPostgreSqlDbmsSkuName extends ExtendableEnum<AzureCosmosPostgreSqlDbmsSkuName> {
  public static final AzureCosmosPostgreSqlDbmsSkuName STANDARD_D4DS_V5 = fromString("Standard_D4ds_v5");
  public static final AzureCosmosPostgreSqlDbmsSkuName STANDARD_D8DS_V5 = fromString("Standard_D8ds_v5");
  public static final AzureCosmosPostgreSqlDbmsSkuName STANDARD_D16DS_V5 = fromString("Standard_D16ds_v5");
  public static final AzureCosmosPostgreSqlDbmsSkuName STANDARD_D32DS_V5 = fromString("Standard_D32ds_v5");
  public static final AzureCosmosPostgreSqlDbmsSkuName STANDARD_D48DS_V5 = fromString("Standard_D48ds_v5");
  public static final AzureCosmosPostgreSqlDbmsSkuName STANDARD_D64DS_V5 = fromString("Standard_D64ds_v5");
  public static final AzureCosmosPostgreSqlDbmsSkuName STANDARD_D96DS_V5 = fromString("Standard_D96ds_v5");

  /**
   * Creates or finds a AzureCosmosPostgreSqlDbmsSkuName from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding AzureCosmosPostgreSqlDbmsSkuName.
   */
  @JsonCreator
  public static AzureCosmosPostgreSqlDbmsSkuName fromString(String name) {
    return fromString(name, AzureCosmosPostgreSqlDbmsSkuName.class);
  }

  /**
   * Gets known AzureCosmosPostgreSqlDbmsSkuName values.
   *
   * @return known AzureCosmosPostgreSqlDbmsSkuName values.
   */
  public static Collection<AzureCosmosPostgreSqlDbmsSkuName> values() {
    return values(AzureCosmosPostgreSqlDbmsSkuName.class);
  }
}
