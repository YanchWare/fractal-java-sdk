package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * <pre>
 * Allow or disallow public network access to Storage Account.
 * Value is optional but if passed in, must be 'Enabled' or 'Disabled'.
 * </pre>
 */
public final class AzurePublicNetworkAccess extends ExtendableEnum<AzurePublicNetworkAccess> {
  public static final AzurePublicNetworkAccess ENABLED = fromString("Enabled");

  public static final AzurePublicNetworkAccess DISABLED = fromString("Disabled");


  /**
   * Creates or finds a PublicNetworkAccess from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding PublicNetworkAccess.
   */
  @JsonCreator
  public static AzurePublicNetworkAccess fromString(String name) {
    return fromString(name, AzurePublicNetworkAccess.class);
  }

  /**
   * Gets known PublicNetworkAccess values.
   *
   * @return known PublicNetworkAccess values.
   */
  public static Collection<AzurePublicNetworkAccess> values() {
    return values(AzurePublicNetworkAccess.class);
  }
}

