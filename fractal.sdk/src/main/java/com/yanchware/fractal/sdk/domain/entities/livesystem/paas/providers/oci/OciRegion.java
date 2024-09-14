package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.oci;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * Choose the OCI region that's right for you and your customers
 */
public final class OciRegion extends ExtendableEnum<OciRegion> {

  /**
   * (Australia East) Sydney
   */
  public static final OciRegion AP_SYDNEY_1 = fromString("ap-sydney-1");

  /**
   * (Australia Southeast) Melbourne
   */
  public static final OciRegion AP_MELBOURNE_1 = fromString("ap-melbourne-1");

  /**
   * Brazil East (Sao Paulo)
   */
  public static final OciRegion SA_SAOPAULO_1 = fromString("sa-saopaulo-1");

  /**
   * Brazil Southeast (Vinhedo)
   */
  public static final OciRegion SA_VINHEDO_1 = fromString("sa-vinhedo-1");

  /**
   * Canada Southeast (Montreal)
   */
  public static final OciRegion CA_MONTREAL_1 = fromString("ca-montreal-1");

  /**
   * Canada Southeast (Toronto)
   */
  public static final OciRegion CA_TORONTO_1 = fromString("ca-toronto-1");

  /**
   * Chile Central (Santiago)
   */
  public static final OciRegion SA_SANTIAGO_1 = fromString("sa-santiago-1");

  /**
   * Chile West (Valparaiso)
   */
  public static final OciRegion SA_VALPARAISO_1 = fromString("sa-valparaiso-1");

  /**
   * Colombia Central (Bogota)
   */
  public static final OciRegion SA_BOGOTA_1 = fromString("sa-bogota-1");

  /**
   * France Central (Paris)
   */
  public static final OciRegion EU_PARIS_1 = fromString("eu-paris-1");

  /**
   * France South (Marseille)
   */
  public static final OciRegion EU_MARSEILLE_1 = fromString("eu-marseille-1");

  /**
   * Germany Central (Frankfurt)
   */
  public static final OciRegion EU_FRANKFURT_1 = fromString("eu-frankfurt-1");

  /**
   * India South (Hyderabad)
   */
  public static final OciRegion AP_HYDERABAD_1 = fromString("ap-hyderabad-1");

  /**
   * India West (Mumbai)
   */
  public static final OciRegion AP_MUMBAI_1 = fromString("ap-mumbai-1");

  /**
   * Israel Central (Jerusalem)
   */
  public static final OciRegion IL_JERUSALEM_1 = fromString("il-jerusalem-1");

  /**
   * Italy Northwest (Milan)
   */
  public static final OciRegion EU_MILAN_1 = fromString("eu-milan-1");

  /**
   * Japan Central (Osaka)
   */
  public static final OciRegion AP_OSAKA_1 = fromString("ap-osaka-1");

  /**
   * Japan East (Tokyo)
   */
  public static final OciRegion AP_TOKYO_1 = fromString("ap-tokyo-1");

  /**
   * Mexico Central (Queretaro)
   */
  public static final OciRegion MX_QUERETARO_1 = fromString("mx-queretaro-1");

  /**
   * Mexico Northeast (Monterrey)
   */
  public static final OciRegion MX_MONTERREY_1 = fromString("mx-monterrey-1");

  /**
   * Netherlands Northwest (Amsterdam)
   */
  public static final OciRegion EU_AMSTERDAM_1 = fromString("eu-amsterdam-1");

  /**
   * Saudi Arabia Central (Riyadh)
   */
  public static final OciRegion ME_RIYADH_1 = fromString("me-riyadh-1");

  /**
   * Saudi Arabia West (Jeddah)
   */
  public static final OciRegion ME_JEDDAH_1 = fromString("me-jeddah-1");

  /**
   * Serbia Central (Jovanovac)
   */
  public static final OciRegion EU_JOVANOVAC_1 = fromString("eu-jovanovac-1");

  /**
   * Singapore (Singapore)
   */
  public static final OciRegion AP_SINGAPORE_1 = fromString("ap-singapore-1");

  /**
   * Singapore West (Singapore)
   */
  public static final OciRegion AP_SINGAPORE_2 = fromString("ap-singapore-2");

  /**
   * South Africa Central (Johannesburg)
   */
  public static final OciRegion AF_JOHANNESBURG_1 = fromString("af-johannesburg-1");

  /**
   * South Korea Central (Seoul)
   */
  public static final OciRegion AP_SEOUL_1 = fromString("ap-seoul-1");

  /**
   * South Korea North (Chuncheon)
   */
  public static final OciRegion AP_CHUNCHEON_1 = fromString("ap-chuncheon-1");

  /**
   * Spain Central (Madrid)
   */
  public static final OciRegion EU_MADRID_1 = fromString("eu-madrid-1");

  /**
   * Sweden Central (Stockholm)
   */
  public static final OciRegion EU_STOCKHOLM_1 = fromString("eu-stockholm-1");

  /**
   * Switzerland North (Zurich)
   */
  public static final OciRegion EU_ZURICH_1 = fromString("eu-zurich-1");

  /**
   * UAE Central (Abu Dhabi)
   */
  public static final OciRegion ME_ABUDHABI_1 = fromString("me-abudhabi-1");

  /**
   * UAE East (Dubai)
   */
  public static final OciRegion ME_DUBAI_1 = fromString("me-dubai-1");

  /**
   * UK South (London)
   */
  public static final OciRegion UK_LONDON_1 = fromString("uk-london-1");

  /**
   * UK West (Newport)
   */
  public static final OciRegion UK_CARDIFF_1 = fromString("uk-cardiff-1");

  /**
   * US East (Ashburn)
   */
  public static final OciRegion US_ASHBURN_1 = fromString("us-ashburn-1");

  /**
   * US Midwest (Chicago)
   */
  public static final OciRegion US_CHICAGO_1 = fromString("us-chicago-1");

  /**
   * US West (Phoenix)
   */
  public static final OciRegion US_PHOENIX_1 = fromString("us-phoenix-1");

  /**
   * US West (San Jose)
   */
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
