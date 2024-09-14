package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * Access tier for specific share. 
 * GpV2 account can choose between TransactionOptimized (default), Hot, and Cool. 
 * FileStorage account can choose Premium.
 */
public final class AzureFileShareAccessTier extends ExtendableEnum<AzureFileShareAccessTier> {
  public static final AzureFileShareAccessTier COOL = fromString("Cool");
  public static final AzureFileShareAccessTier HOT = fromString("Hot");
  public static final AzureFileShareAccessTier PREMIUM = fromString("Premium");
  public static final AzureFileShareAccessTier TRANSACTION_OPTIMIZED = fromString("TransactionOptimized");

  public AzureFileShareAccessTier() {
  }

  @JsonCreator
  public static AzureFileShareAccessTier fromString(String name) {
    return fromString(name, AzureFileShareAccessTier.class);
  }

  public static Collection<AzureFileShareAccessTier> values() {
    return values(AzureFileShareAccessTier.class);
  }

}
