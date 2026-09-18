package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * Deprecated. Compute sizes for {@link AzureCosmosPostgreSqlDbms}, which Azure no longer
 * provisions.   * cluster returns HTTP 400 ("Provisioning new Azure Cosmos DB for PostgreSQL clusters is no longer
  * supported as part of service retirement"), on every subscription, so a topology declaring one can
  * no longer be deployed. Clusters that already exist keep running and are still reconciled.
  *
  * <p>For new workloads use Azure Database for PostgreSQL with Elastic Clusters, which carries the
  * same Citus distributed-PostgreSQL features. See
  * <a href="https://azure.microsoft.com/en-us/updates?id=556085">the Azure retirement notice</a>.
 */
@Deprecated
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
