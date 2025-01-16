package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * <pre>
 * The network action.
 * </pre>
 */
public final class AzureNetworkAction extends ExtendableEnum<AzureNetworkAction> {
  public static final AzureNetworkAction ALLOW = fromString("Allow");

  /**
   * Creates or finds an Azure Network Action from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding AzureNetworkAction.
   */
  @JsonCreator
  public static AzureNetworkAction fromString(String name) {
    return fromString(name, AzureNetworkAction.class);
  }

  /**
   * Gets known Azure Network Action values.
   *
   * @return known Azure Network Action values.
   */
  public static Collection<AzureNetworkAction> values() {
    return values(AzureNetworkAction.class);
  }
}
