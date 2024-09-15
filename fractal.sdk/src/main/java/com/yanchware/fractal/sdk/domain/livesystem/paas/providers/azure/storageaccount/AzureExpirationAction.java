package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * <pre>
 * The SAS expiration action. 
 * Can only be Log.
 * </pre>
 */
public final class AzureExpirationAction extends ExtendableEnum<AzureExpirationAction> {
  public static final AzureExpirationAction LOG = fromString("Log");

  /**
   * Creates or finds a ExpirationAction from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding ExpirationAction.
   */
  @JsonCreator
  public static AzureExpirationAction fromString(String name) {
    return fromString(name, AzureExpirationAction.class);
  }

  /**
   * Gets known ExpirationAction values.
   *
   * @return known ExpirationAction values.
   */
  public static Collection<AzureExpirationAction> values() {
    return values(AzureExpirationAction.class);
  }

}
