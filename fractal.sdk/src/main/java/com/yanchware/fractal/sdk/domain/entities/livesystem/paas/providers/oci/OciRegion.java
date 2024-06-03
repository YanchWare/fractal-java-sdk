package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.oci;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

/**
 * Choose the OCI region that's right for you and your customers
 */
public final class OciRegion extends ExtendableEnum<OciRegion> {

  /**
   * (EU) Zurich
   */
  public static final OciRegion EU_ZURICH_1 = fromString("eu-zurich-1");

  /**
   * Creates or finds a OciRegion from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding OciRegion.
   */
  @JsonCreator
  public static OciRegion fromString(String name) {
    return fromString(name, OciRegion.class);
  }

  /**
   * Gets known OciRegion values.
   *
   * @return known OciRegion values.
   */
  public static Collection<OciRegion> values() {
    return values(OciRegion.class);
  }
}
