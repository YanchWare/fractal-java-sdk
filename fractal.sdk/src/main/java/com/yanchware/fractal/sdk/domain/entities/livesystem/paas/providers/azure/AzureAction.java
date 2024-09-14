package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * <pre>
 * Specifies action of allow or deny when no other rules match.
 * </pre>
 */
public final class AzureAction extends ExtendableEnum<AzureAction> {
  public static final AzureAction ALLOW = fromString("Allow");
  public static final AzureAction DENY = fromString("Deny");


  /**
   * Creates or finds a AzureAction from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding AzureAction.
   */
  @JsonCreator
  public static AzureAction fromString(String name) {
    return fromString(name, AzureAction.class);
  }

  /**
   * Gets known AzureAction values.
   *
   * @return known AzureAction values.
   */
  public static Collection<AzureAction> values() {
    return values(AzureAction.class);
  }
}
