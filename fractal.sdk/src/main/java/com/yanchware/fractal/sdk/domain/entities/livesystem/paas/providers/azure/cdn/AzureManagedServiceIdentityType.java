package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cdn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

/**
 * Type of managed service identity (where both SystemAssigned and UserAssigned types are allowed)
 */
public class AzureManagedServiceIdentityType extends ExtendableEnum<AzureManagedServiceIdentityType> {

  /**
   * Creates or finds a AzureCdnSku from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding AzureCdnSku.
   */
  @JsonCreator
  public static AzureCdnSku fromString(String name) {
    return fromString(name, AzureCdnSku.class);
  }

  /**
   * Gets known AzureCdnSku values.
   *
   * @return known AzureCdnSku values.
   */
  public static Collection<AzureCdnSku> values() {
    return values(AzureCdnSku.class);
  }

}
