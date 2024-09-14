package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * <pre>
 * Routing Choice defines the kind of network routing opted by the user.
 * </pre>
 */
public final class AzureRoutingChoice extends ExtendableEnum<AzureRoutingChoice> {
  public static final AzureRoutingChoice MICROSOFT_ROUTING = fromString("MicrosoftRouting");
  public static final AzureRoutingChoice INTERNET_ROUTING = fromString("InternetRouting");
  

  /**
   * Creates or finds a RoutingChoice from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding RoutingChoice.
   */
  @JsonCreator
  public static AzureRoutingChoice fromString(String name) {
    return fromString(name, AzureRoutingChoice.class);
  }

  /**
   * Gets known RoutingChoice values.
   *
   * @return known RoutingChoice values.
   */
  public static Collection<AzureRoutingChoice> values() {
    return values(AzureRoutingChoice.class);
  }

}
