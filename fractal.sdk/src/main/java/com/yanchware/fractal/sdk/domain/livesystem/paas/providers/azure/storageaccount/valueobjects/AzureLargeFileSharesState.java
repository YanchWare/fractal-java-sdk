package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * <pre>
 * Allow large file shares if sets to Enabled. It cannot be disabled once it is enabled.
 * </pre>
 */
public final class AzureLargeFileSharesState extends ExtendableEnum<AzureLargeFileSharesState> {
  public static final AzureLargeFileSharesState DISABLED = fromString("Disabled");
  
  public static final AzureLargeFileSharesState ENABLED = fromString("Enabled");

  /**
   * Creates or finds a AzureLargeFileSharesState from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding AzureLargeFileSharesState.
   */
  @JsonCreator
  public static AzureLargeFileSharesState fromString(String name) {
    return fromString(name, AzureLargeFileSharesState.class);
  }

  /**
   * Gets known AzureLargeFileSharesState values.
   *
   * @return known AzureLargeFileSharesState values.
   */
  public static Collection<AzureLargeFileSharesState> values() {
    return values(AzureLargeFileSharesState.class);
  }
}
