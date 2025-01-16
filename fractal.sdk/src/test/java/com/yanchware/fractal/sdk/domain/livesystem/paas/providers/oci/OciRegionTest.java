package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class OciRegionTest {

  @Test
  public void ociActionConstants_shouldNotBeBlank() {
    OciRegion.values().forEach(x ->
      assertThat(x.toString()).isNotBlank());
  }

  @Test
  public void fromString_shouldReturnCorrespondingOciAction() {
    assertThat(OciRegion.fromString("ap-sydney-1"))
      .as("fromString should return AP_SYDNEY_1 for 'ap-sydney-1'")
      .isEqualTo(OciRegion.AP_SYDNEY_1);

    assertThat(OciRegion.fromString("ap-melbourne-1"))
      .as("fromString should return AP_MELBOURNE_1 for 'ap-melbourne-1'")
      .isEqualTo(OciRegion.AP_MELBOURNE_1);

    assertThat(OciRegion.fromString("sa-saopaulo-1"))
      .as("fromString should return SA_SAOPAULO_1 for 'sa-saopaulo-1'")
      .isEqualTo(OciRegion.SA_SAOPAULO_1);

    assertThat(OciRegion.fromString("sa-vinhedo-1"))
      .as("fromString should return SA_VINHEDO_1 for 'sa-vinhedo-1'")
      .isEqualTo(OciRegion.SA_VINHEDO_1);

    assertThat(OciRegion.fromString("ca-montreal-1"))
      .as("fromString should return CA_MONTREAL_1 for 'ca-montreal-1'")
      .isEqualTo(OciRegion.CA_MONTREAL_1);

    assertThat(OciRegion.fromString("ca-toronto-1"))
      .as("fromString should return CA_TORONTO_1 for 'ca-toronto-1'")
      .isEqualTo(OciRegion.CA_TORONTO_1);

    assertThat(OciRegion.fromString("sa-santiago-1"))
      .as("fromString should return SA_SANTIAGO_1 for 'sa-santiago-1'")
      .isEqualTo(OciRegion.SA_SANTIAGO_1);

    assertThat(OciRegion.fromString("sa-valparaiso-1"))
      .as("fromString should return SA_VALPARAISO_1 for 'sa-valparaiso-1'")
      .isEqualTo(OciRegion.SA_VALPARAISO_1);

    assertThat(OciRegion.fromString("sa-bogota-1"))
      .as("fromString should return SA_BOGOTA_1 for 'sa-bogota-1'")
      .isEqualTo(OciRegion.SA_BOGOTA_1);

    assertThat(OciRegion.fromString("eu-paris-1"))
      .as("fromString should return EU_PARIS_1 for 'eu-paris-1'")
      .isEqualTo(OciRegion.EU_PARIS_1);

    assertThat(OciRegion.fromString("eu-marseille-1"))
      .as("fromString should return EU_MARSEILLE_1 for 'eu-marseille-1'")
      .isEqualTo(OciRegion.EU_MARSEILLE_1);

    assertThat(OciRegion.fromString("eu-frankfurt-1"))
      .as("fromString should return EU_FRANKFURT_1 for 'eu-frankfurt-1'")
      .isEqualTo(OciRegion.EU_FRANKFURT_1);

    assertThat(OciRegion.fromString("ap-hyderabad-1"))
      .as("fromString should return AP_HYDERABAD_1 for 'ap-hyderabad-1'")
      .isEqualTo(OciRegion.AP_HYDERABAD_1);

    assertThat(OciRegion.fromString("ap-mumbai-1"))
      .as("fromString should return AP_MUMBAI_1 for 'ap-mumbai-1'")
      .isEqualTo(OciRegion.AP_MUMBAI_1);

    assertThat(OciRegion.fromString("il-jerusalem-1"))
      .as("fromString should return IL_JERUSALEM_1 for 'il-jerusalem-1'")
      .isEqualTo(OciRegion.IL_JERUSALEM_1);

    assertThat(OciRegion.fromString("eu-milan-1"))
      .as("fromString should return EU_MILAN_1 for 'eu-milan-1'")
      .isEqualTo(OciRegion.EU_MILAN_1);

    assertThat(OciRegion.fromString("ap-osaka-1"))
      .as("fromString should return AP_OSAKA_1 for 'ap-osaka-1'")
      .isEqualTo(OciRegion.AP_OSAKA_1);

    assertThat(OciRegion.fromString("ap-tokyo-1"))
      .as("fromString should return AP_TOKYO_1 for 'ap-tokyo-1'")
      .isEqualTo(OciRegion.AP_TOKYO_1);

    assertThat(OciRegion.fromString("mx-queretaro-1"))
      .as("fromString should return MX_QUERETARO_1 for 'mx-queretaro-1'")
      .isEqualTo(OciRegion.MX_QUERETARO_1);

    assertThat(OciRegion.fromString("mx-monterrey-1"))
      .as("fromString should return MX_MONTERREY_1 for 'mx-monterrey-1'")
      .isEqualTo(OciRegion.MX_MONTERREY_1);

    assertThat(OciRegion.fromString("eu-amsterdam-1"))
      .as("fromString should return EU_AMSTERDAM_1 for 'eu-amsterdam-1'")
      .isEqualTo(OciRegion.EU_AMSTERDAM_1);

    assertThat(OciRegion.fromString("me-riyadh-1"))
      .as("fromString should return ME_RIYADH_1 for 'me-riyadh-1'")
      .isEqualTo(OciRegion.ME_RIYADH_1);

    assertThat(OciRegion.fromString("me-jeddah-1"))
      .as("fromString should return ME_JEDDAH_1 for 'me-jeddah-1'")
      .isEqualTo(OciRegion.ME_JEDDAH_1);

    assertThat(OciRegion.fromString("eu-jovanovac-1"))
      .as("fromString should return EU_JOVANOVAC_1 for 'eu-jovanovac-1'")
      .isEqualTo(OciRegion.EU_JOVANOVAC_1);

    assertThat(OciRegion.fromString("singapore-1"))
      .as("fromString should return SINGAPORE_1 for 'singapore-1'")
      .isEqualTo(OciRegion.SINGAPORE_1);

    assertThat(OciRegion.fromString("ap-singapore-2"))
      .as("fromString should return AP_SINGAPORE_2 for 'ap-singapore-2'")
      .isEqualTo(OciRegion.AP_SINGAPORE_2);

    assertThat(OciRegion.fromString("af-johannesburg-1"))
      .as("fromString should return AF_JOHANNESBURG_1 for 'af-johannesburg-1'")
      .isEqualTo(OciRegion.AF_JOHANNESBURG_1);

    assertThat(OciRegion.fromString("ap-seoul-1"))
      .as("fromString should return AP_SEOUL_1 for 'ap-seoul-1'")
      .isEqualTo(OciRegion.AP_SEOUL_1);

    assertThat(OciRegion.fromString("ap-chuncheon-1"))
      .as("fromString should return AP_CHUNCHEON_1 for 'ap-chuncheon-1'")
      .isEqualTo(OciRegion.AP_CHUNCHEON_1);

    assertThat(OciRegion.fromString("eu-madrid-1"))
      .as("fromString should return EU_MADRID_1 for 'eu-madrid-1'")
      .isEqualTo(OciRegion.EU_MADRID_1);

    assertThat(OciRegion.fromString("eu-stockholm-1"))
      .as("fromString should return EU_STOCKHOLM_1 for 'eu-stockholm-1'")
      .isEqualTo(OciRegion.EU_STOCKHOLM_1);

    assertThat(OciRegion.fromString("eu-zurich-1"))
      .as("fromString should return EU_ZURICH_1 for 'eu-zurich-1'")
      .isEqualTo(OciRegion.EU_ZURICH_1);

    assertThat(OciRegion.fromString("me-abudhabi-1"))
      .as("fromString should return ME_ABUDHABI_1 for 'me-abudhabi-1'")
      .isEqualTo(OciRegion.ME_ABUDHABI_1);

    assertThat(OciRegion.fromString("me-dubai-1"))
      .as("fromString should return ME_DUBAI_1 for 'me-dubai-1'")
      .isEqualTo(OciRegion.ME_DUBAI_1);

    assertThat(OciRegion.fromString("uk-london-1"))
      .as("fromString should return UK_LONDON_1 for 'uk-london-1'")
      .isEqualTo(OciRegion.UK_LONDON_1);

    assertThat(OciRegion.fromString("uk-cardiff-1"))
      .as("fromString should return UK_CARDIFF_1 for 'uk-cardiff-1'")
      .isEqualTo(OciRegion.UK_CARDIFF_1);

    assertThat(OciRegion.fromString("us-ashburn-1"))
      .as("fromString should return US_ASHBURN_1 for 'us-ashburn-1'")
      .isEqualTo(OciRegion.US_ASHBURN_1);

    assertThat(OciRegion.fromString("us-chicago-1"))
      .as("fromString should return US_CHICAGO_1 for 'us-chicago-1'")
      .isEqualTo(OciRegion.US_CHICAGO_1);

    assertThat(OciRegion.fromString("us-phoenix-1"))
      .as("fromString should return US_PHOENIX_1 for 'us-phoenix-1'")
      .isEqualTo(OciRegion.US_PHOENIX_1);

    assertThat(OciRegion.fromString("us-sanjose-1"))
      .as("fromString should return US_SANJOSE_1 for 'us-sanjose-1'")
      .isEqualTo(OciRegion.US_SANJOSE_1);
  }

  @Test
  public void valuesMethod_shouldContainAllOciActionsWithCorrectSize() {
    Collection<OciRegion> values = OciRegion.values();

    assertThat(values)
      .as("Values should contain all specified OciRegions")
      .contains(
        OciRegion.AP_SYDNEY_1,
        OciRegion.AP_MELBOURNE_1,
        OciRegion.SA_SAOPAULO_1,
        OciRegion.SA_VINHEDO_1,
        OciRegion.CA_MONTREAL_1,
        OciRegion.CA_TORONTO_1,
        OciRegion.SA_SANTIAGO_1,
        OciRegion.SA_VALPARAISO_1,
        OciRegion.SA_BOGOTA_1,
        OciRegion.EU_PARIS_1,
        OciRegion.EU_MARSEILLE_1,
        OciRegion.EU_FRANKFURT_1,
        OciRegion.AP_HYDERABAD_1,
        OciRegion.AP_MUMBAI_1,
        OciRegion.IL_JERUSALEM_1,
        OciRegion.EU_MILAN_1,
        OciRegion.AP_OSAKA_1,
        OciRegion.AP_TOKYO_1,
        OciRegion.MX_QUERETARO_1,
        OciRegion.MX_MONTERREY_1,
        OciRegion.EU_AMSTERDAM_1,
        OciRegion.ME_RIYADH_1,
        OciRegion.ME_JEDDAH_1,
        OciRegion.EU_JOVANOVAC_1,
        OciRegion.SINGAPORE_1,
        OciRegion.AP_SINGAPORE_2,
        OciRegion.AF_JOHANNESBURG_1,
        OciRegion.AP_SEOUL_1,
        OciRegion.AP_CHUNCHEON_1,
        OciRegion.EU_MADRID_1,
        OciRegion.EU_STOCKHOLM_1,
        OciRegion.EU_ZURICH_1,
        OciRegion.ME_ABUDHABI_1,
        OciRegion.ME_DUBAI_1,
        OciRegion.UK_LONDON_1,
        OciRegion.UK_CARDIFF_1,
        OciRegion.US_ASHBURN_1,
        OciRegion.US_CHICAGO_1,
        OciRegion.US_PHOENIX_1,
        OciRegion.US_SANJOSE_1);
  }
}