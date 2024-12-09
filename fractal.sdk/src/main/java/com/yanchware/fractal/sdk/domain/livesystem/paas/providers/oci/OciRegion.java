package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * Choose the OCI region that's right for you and your customers
 */
public final class OciRegion extends ExtendableEnum<OciRegion> {

  public static final OciRegion AP_SYDNEY_1 = fromString("ap-sydney-1");
  public static final OciRegion AP_MELBOURNE_1 = fromString("ap-melbourne-1");
  public static final OciRegion SA_SAOPAULO_1 = fromString("sa-saopaulo-1");
  public static final OciRegion SA_VINHEDO_1 = fromString("sa-vinhedo-1");
  public static final OciRegion CA_MONTREAL_1 = fromString("ca-montreal-1");
  public static final OciRegion CA_TORONTO_1 = fromString("ca-toronto-1");
  public static final OciRegion SA_SANTIAGO_1 = fromString("sa-santiago-1");
  public static final OciRegion SA_VALPARAISO_1 = fromString("sa-valparaiso-1");
  public static final OciRegion SA_BOGOTA_1 = fromString("sa-bogota-1");
  public static final OciRegion EU_PARIS_1 = fromString("eu-paris-1");
  public static final OciRegion EU_MARSEILLE_1 = fromString("eu-marseille-1");
  public static final OciRegion EU_FRANKFURT_1 = fromString("eu-frankfurt-1");
  public static final OciRegion AP_HYDERABAD_1 = fromString("ap-hyderabad-1");
  public static final OciRegion AP_MUMBAI_1 = fromString("ap-mumbai-1");
  public static final OciRegion IL_JERUSALEM_1 = fromString("il-jerusalem-1");
  public static final OciRegion EU_MILAN_1 = fromString("eu-milan-1");
  public static final OciRegion AP_OSAKA_1 = fromString("ap-osaka-1");
  public static final OciRegion AP_TOKYO_1 = fromString("ap-tokyo-1");
  public static final OciRegion MX_QUERETARO_1 = fromString("mx-queretaro-1");
  public static final OciRegion MX_MONTERREY_1 = fromString("mx-monterrey-1");
  public static final OciRegion EU_AMSTERDAM_1 = fromString("eu-amsterdam-1");
  public static final OciRegion ME_RIYADH_1 = fromString("me-riyadh-1");
  public static final OciRegion ME_JEDDAH_1 = fromString("me-jeddah-1");
  public static final OciRegion EU_JOVANOVAC_1 = fromString("eu-jovanovac-1");
  public static final OciRegion SINGAPORE_1 = fromString("singapore-1");
  public static final OciRegion AP_SINGAPORE_2 = fromString("ap-singapore-2");
  public static final OciRegion AF_JOHANNESBURG_1 = fromString("af-johannesburg-1");
  public static final OciRegion AP_SEOUL_1 = fromString("ap-seoul-1");
  public static final OciRegion AP_CHUNCHEON_1 = fromString("ap-chuncheon-1");
  public static final OciRegion EU_MADRID_1 = fromString("eu-madrid-1");
  public static final OciRegion EU_STOCKHOLM_1 = fromString("eu-stockholm-1");
  public static final OciRegion EU_ZURICH_1 = fromString("eu-zurich-1");
  public static final OciRegion ME_ABUDHABI_1 = fromString("me-abudhabi-1");
  public static final OciRegion ME_DUBAI_1 = fromString("me-dubai-1");
  public static final OciRegion UK_LONDON_1 = fromString("uk-london-1");
  public static final OciRegion UK_CARDIFF_1 = fromString("uk-cardiff-1");
  public static final OciRegion US_ASHBURN_1 = fromString("us-ashburn-1");
  public static final OciRegion US_CHICAGO_1 = fromString("us-chicago-1");
  public static final OciRegion US_PHOENIX_1 = fromString("us-phoenix-1");
  public static final OciRegion US_SANJOSE_1 = fromString("us-sanjose-1");

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
