package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

/**
 * <pre>
 * Specifies whether traffic is bypassed for Logging/Metrics/AzureServices. 
 * Possible values are any combination of Logging|Metrics|AzureServices.
 * 
 * For example:
 *  "Logging, Metrics"
 *  or None to bypass none of those traffics.
 * </pre>
 */
public final class AzureBypass extends ExtendableEnum<AzureBypass> {
  public static final AzureBypass NONE = fromString("None");
  public static final AzureBypass LOGGING = fromString("Logging");
  public static final AzureBypass METRICS = fromString("Metrics");
  public static final AzureBypass AZURE_SERVICES = fromString("AzureServices");


  /**
   * Creates or finds a Bypass from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding Bypass.
   */
  @JsonCreator
  public static AzureBypass fromString(String name) {
    return fromString(name, AzureBypass.class);
  }

  /**
   * Gets known Bypass values.
   *
   * @return known Bypass values.
   */
  public static Collection<AzureBypass> values() {
    return values(AzureBypass.class);
  }
}


