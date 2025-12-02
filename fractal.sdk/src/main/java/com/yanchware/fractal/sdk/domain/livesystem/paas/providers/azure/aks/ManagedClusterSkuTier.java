package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public final class ManagedClusterSkuTier extends ExtendableEnum<ManagedClusterSkuTier> {
  /**
   * Static value Premium for ManagedClusterSkuTier.
   */

  public static final ManagedClusterSkuTier PREMIUM = fromString("Premium");

  /**
   * Static value Standard for ManagedClusterSkuTier.
   */
  public static final ManagedClusterSkuTier STANDARD = fromString("Standard");

  /**
   * Static value Free for ManagedClusterSkuTier.
   */
  public static final ManagedClusterSkuTier FREE = fromString("Free");

  /**
   * Creates a new instance of ManagedClusterSkuTier value.
   *
   * @deprecated Use the {@link #fromString(String)} factory method.
   */
  @Deprecated
  public ManagedClusterSkuTier() {
  }

  /**
   * Creates or finds a ManagedClusterSkuTier from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding ManagedClusterSkuTier.
   */
  @JsonCreator
  public static ManagedClusterSkuTier fromString(String name) {
    return fromString(name, ManagedClusterSkuTier.class);
  }

  /**
   * Gets known ManagedClusterSkuTier values.
   *
   * @return known ManagedClusterSkuTier values.
   */
  public static Collection<ManagedClusterSkuTier> values() {
    return values(ManagedClusterSkuTier.class);
  }
}
