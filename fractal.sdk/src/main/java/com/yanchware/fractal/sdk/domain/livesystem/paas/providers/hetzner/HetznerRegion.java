package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.hetzner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * Choose the Hetzner region that's right for you and your customers
 */
public final class HetznerRegion extends ExtendableEnum<HetznerRegion> {

  public static final HetznerRegion DE_FALKENSTEIN_1 = fromString("fsn1");
  public static final HetznerRegion DE_NUREMBGERG_1 = fromString("nbg1");
  public static final HetznerRegion FI_HELSINKI_1 = fromString("hel1");
  public static final HetznerRegion US_US_ASHBURN_VA = fromString("ash");
  public static final HetznerRegion US_HILLSBORO_OR = fromString("hil");
  public static final HetznerRegion SG_SINGAPORE = fromString("sin");

  /**
   * Creates or finds a HetznerRegion from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding HetznerRegion.
   */
  @JsonCreator
  public static HetznerRegion fromString(String name) {
    return fromString(name, HetznerRegion.class);
  }

  /**
   * Gets known HetznerRegion values.
   *
   * @return known HetznerRegion values.
   */
  public static Collection<HetznerRegion> values() {
    return values(HetznerRegion.class);
  }
}
