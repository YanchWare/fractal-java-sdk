package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public class HighAvailabilityMode extends ExtendableEnum<HighAvailabilityMode> {
  public static final HighAvailabilityMode DISABLED = fromString("Disabled");
  public static final HighAvailabilityMode ZONE_REDUNDANT = fromString("ZoneRedundant");
  public static final HighAvailabilityMode SAME_ZONE = fromString("SameZone");

  /**
   * Creates or finds a HighAvailabilityMode from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding HighAvailabilityMode.
   */
  @JsonCreator
  public static HighAvailabilityMode fromString(String name) {
    return fromString(name, HighAvailabilityMode.class);
  }

  /**
   * Gets known HighAvailabilityMode values.
   *
   * @return known HighAvailabilityMode values.
   */
  public static Collection<HighAvailabilityMode> values() {
    return values(HighAvailabilityMode.class);
  }
}
