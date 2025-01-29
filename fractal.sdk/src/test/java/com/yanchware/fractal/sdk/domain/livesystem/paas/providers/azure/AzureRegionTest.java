package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureRegionTest {

  @Test
  public void azureActionConstants_shouldNotBeBlank() {
    AzureRegion.values().forEach(x ->
            assertThat(x.toString()).isNotBlank());
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureAction() {
    assertThat(AzureRegion.fromString("eastus"))
        .as("fromString should return EAST_US for 'eastus'")
        .isEqualTo(AzureRegion.EAST_US);

    assertThat(AzureRegion.fromString("eastus2"))
        .as("fromString should return EAST_US2 for 'eastus2'")
        .isEqualTo(AzureRegion.EAST_US2);

    assertThat(AzureRegion.fromString("southcentralus"))
        .as("fromString should return SOUTH_CENTRAL_US for 'southcentralus'")
        .isEqualTo(AzureRegion.SOUTH_CENTRAL_US);

    assertThat(AzureRegion.fromString("westus2"))
        .as("fromString should return WEST_US2 for 'westus2'")
        .isEqualTo(AzureRegion.WEST_US2);

    assertThat(AzureRegion.fromString("westus3"))
        .as("fromString should return WEST_US_3 for 'westus3'")
        .isEqualTo(AzureRegion.WEST_US3);

    assertThat(AzureRegion.fromString("australiaeast"))
        .as("fromString should return AUSTRALIA_EAST for 'australiaeast'")
        .isEqualTo(AzureRegion.AUSTRALIA_EAST);

    assertThat(AzureRegion.fromString("southeastasia"))
        .as("fromString should return SOUTHEAST_ASIA for 'southeastasia'")
        .isEqualTo(AzureRegion.SOUTHEAST_ASIA);

    assertThat(AzureRegion.fromString("northeurope"))
        .as("fromString should return NORTH_EUROPE for 'northeurope'")
        .isEqualTo(AzureRegion.NORTH_EUROPE);

    assertThat(AzureRegion.fromString("swedencentral"))
        .as("fromString should return SWEDEN_CENTRAL for 'swedencentral'")
        .isEqualTo(AzureRegion.SWEDEN_CENTRAL);

    assertThat(AzureRegion.fromString("uksouth"))
        .as("fromString should return UK_SOUTH for 'uksouth'")
        .isEqualTo(AzureRegion.UK_SOUTH);

    assertThat(AzureRegion.fromString("westeurope"))
        .as("fromString should return WEST_EUROPE for 'westeurope'")
        .isEqualTo(AzureRegion.WEST_EUROPE);

    assertThat(AzureRegion.fromString("centralus"))
        .as("fromString should return CENTRAL_US for 'centralus'")
        .isEqualTo(AzureRegion.CENTRAL_US);

    assertThat(AzureRegion.fromString("southafricanorth"))
        .as("fromString should return SOUTH_AFRICA_NORTH for 'southafricanorth'")
        .isEqualTo(AzureRegion.SOUTH_AFRICA_NORTH);

    assertThat(AzureRegion.fromString("centralindia"))
        .as("fromString should return CENTRAL_INDIA for 'centralindia'")
        .isEqualTo(AzureRegion.CENTRAL_INDIA);

    assertThat(AzureRegion.fromString("eastasia"))
        .as("fromString should return EAST_ASIA for 'eastasia'")
        .isEqualTo(AzureRegion.EAST_ASIA);

    assertThat(AzureRegion.fromString("japaneast"))
        .as("fromString should return JAPAN_EAST for 'japaneast'")
        .isEqualTo(AzureRegion.JAPAN_EAST);

    assertThat(AzureRegion.fromString("koreacentral"))
        .as("fromString should return KOREA_CENTRAL for 'koreacentral'")
        .isEqualTo(AzureRegion.KOREA_CENTRAL);

    assertThat(AzureRegion.fromString("newzealandnorth"))
        .as("fromString should return NEWZEALAND_NORTH for 'newzealandnorth'")
        .isEqualTo(AzureRegion.NEWZEALAND_NORTH);

    assertThat(AzureRegion.fromString("canadacentral"))
        .as("fromString should return CANADA_CENTRAL for 'canadacentral'")
        .isEqualTo(AzureRegion.CANADA_CENTRAL);

    assertThat(AzureRegion.fromString("francecentral"))
        .as("fromString should return FRANCE_CENTRAL for 'francecentral'")
        .isEqualTo(AzureRegion.FRANCE_CENTRAL);

    assertThat(AzureRegion.fromString("germanywestcentral"))
        .as("fromString should return GERMANY_WEST_CENTRAL for 'germanywestcentral'")
        .isEqualTo(AzureRegion.GERMANY_WEST_CENTRAL);

    assertThat(AzureRegion.fromString("italynorth"))
        .as("fromString should return ITALY_NORTH for 'italynorth'")
        .isEqualTo(AzureRegion.ITALY_NORTH);

    assertThat(AzureRegion.fromString("norwayeast"))
        .as("fromString should return NORWAY_EAST for 'norwayeast'")
        .isEqualTo(AzureRegion.NORWAY_EAST);

    assertThat(AzureRegion.fromString("polandcentral"))
        .as("fromString should return POLAND_CENTRAL for 'polandcentral'")
        .isEqualTo(AzureRegion.POLAND_CENTRAL);

    assertThat(AzureRegion.fromString("spaincentral"))
        .as("fromString should return SPAIN_CENTRAL for 'spaincentral'")
        .isEqualTo(AzureRegion.SPAIN_CENTRAL);

    assertThat(AzureRegion.fromString("switzerlandnorth"))
        .as("fromString should return SWITZERLAND_NORTH for 'switzerlandnorth'")
        .isEqualTo(AzureRegion.SWITZERLAND_NORTH);

    assertThat(AzureRegion.fromString("mexicocentral"))
        .as("fromString should return SWITZERLAND_NORTH for 'mexicocentral'")
        .isEqualTo(AzureRegion.MEXICO_CENTRAL);

    assertThat(AzureRegion.fromString("uaenorth"))
        .as("fromString should return UAE_NORTH for 'uaenorth'")
        .isEqualTo(AzureRegion.UAE_NORTH);

    assertThat(AzureRegion.fromString("brazilsouth"))
        .as("fromString should return BRAZIL_SOUTH for 'brazilsouth'")
        .isEqualTo(AzureRegion.BRAZIL_SOUTH);

    assertThat(AzureRegion.fromString("israelcentral"))
        .as("fromString should return ISRAEL_CENTRAL for 'israelcentral'")
        .isEqualTo(AzureRegion.ISRAEL_CENTRAL);

    assertThat(AzureRegion.fromString("centralusstage"))
        .as("fromString should return CENTRAL_US_STAGE for 'qatarcencentralusstagetral'")
        .isEqualTo(AzureRegion.CENTRAL_US_STAGE);

    assertThat(AzureRegion.fromString("eastusstage"))
        .as("fromString should return EAST_US_STAGE for 'eastusstage'")
        .isEqualTo(AzureRegion.EAST_US_STAGE);

    assertThat(AzureRegion.fromString("eastus2stage"))
        .as("fromString should return EAST_US2_STAGE for 'eastus2stage'")
        .isEqualTo(AzureRegion.EAST_US2_STAGE);

    assertThat(AzureRegion.fromString("northcentralusstage"))
        .as("fromString should return NORTH_CENTRAL_US_STAGE for 'northcentralusstage'")
        .isEqualTo(AzureRegion.NORTH_CENTRAL_US_STAGE);

    assertThat(AzureRegion.fromString("southcentralusstage"))
        .as("fromString should return SOUTH_CENTRAL_US_STAGE for 'southcentralusstage'")
        .isEqualTo(AzureRegion.SOUTH_CENTRAL_US_STAGE);

    assertThat(AzureRegion.fromString("westusstage"))
        .as("fromString should return WEST_US_STAGE for 'westusstage'")
        .isEqualTo(AzureRegion.WEST_US_STAGE);

    assertThat(AzureRegion.fromString("westus2stage"))
        .as("fromString should return WEST_US2_STAGE for 'westus2stage'")
        .isEqualTo(AzureRegion.WEST_US2_STAGE);

    assertThat(AzureRegion.fromString("asia"))
        .as("fromString should return ASIA for 'asia'")
        .isEqualTo(AzureRegion.ASIA);

    assertThat(AzureRegion.fromString("asiapacific"))
        .as("fromString should return ASIA_PACIFIC for 'asiapacific'")
        .isEqualTo(AzureRegion.ASIA_PACIFIC);

    assertThat(AzureRegion.fromString("australia"))
        .as("fromString should return AUSTRALIA for 'australia'")
        .isEqualTo(AzureRegion.AUSTRALIA);

    assertThat(AzureRegion.fromString("brazil"))
        .as("fromString should return BRAZIL for 'brazil'")
        .isEqualTo(AzureRegion.BRAZIL);

    assertThat(AzureRegion.fromString("canada"))
        .as("fromString should return CANADA for 'canada'")
        .isEqualTo(AzureRegion.CANADA);

    assertThat(AzureRegion.fromString("europe"))
        .as("fromString should return EUROPE for 'europe'")
        .isEqualTo(AzureRegion.EUROPE);

    assertThat(AzureRegion.fromString("france"))
        .as("fromString should return FRANCE for 'france'")
        .isEqualTo(AzureRegion.FRANCE);

    assertThat(AzureRegion.fromString("germany"))
        .as("fromString should return GERMANY for 'germany'")
        .isEqualTo(AzureRegion.GERMANY);

    assertThat(AzureRegion.fromString("global"))
        .as("fromString should return GLOBAL for 'global'")
        .isEqualTo(AzureRegion.GLOBAL);

    assertThat(AzureRegion.fromString("india"))
        .as("fromString should return INDIA for 'india'")
        .isEqualTo(AzureRegion.INDIA);

    assertThat(AzureRegion.fromString("israel"))
        .as("fromString should return ISRAEL for 'israel'")
        .isEqualTo(AzureRegion.ISRAEL);

    assertThat(AzureRegion.fromString("italy"))
        .as("fromString should return ITALY for 'italy'")
        .isEqualTo(AzureRegion.ITALY);

    assertThat(AzureRegion.fromString("japan"))
        .as("fromString should return JAPAN for 'japan'")
        .isEqualTo(AzureRegion.JAPAN);

    assertThat(AzureRegion.fromString("korea"))
        .as("fromString should return KOREA for 'korea'")
        .isEqualTo(AzureRegion.KOREA);

    assertThat(AzureRegion.fromString("newzealand"))
        .as("fromString should return NEWZEALAND for 'newzealand'")
        .isEqualTo(AzureRegion.NEWZEALAND);

    assertThat(AzureRegion.fromString("norway"))
        .as("fromString should return NORWAY for 'norway'")
        .isEqualTo(AzureRegion.NORWAY);

    assertThat(AzureRegion.fromString("poland"))
        .as("fromString should return POLAND for 'poland'")
        .isEqualTo(AzureRegion.POLAND);

    assertThat(AzureRegion.fromString("qatar"))
        .as("fromString should return QATAR for 'qatar'")
        .isEqualTo(AzureRegion.QATAR);

    assertThat(AzureRegion.fromString("singapore"))
        .as("fromString should return SINGAPORE for 'singapore'")
        .isEqualTo(AzureRegion.SINGAPORE);

    assertThat(AzureRegion.fromString("southafrica"))
        .as("fromString should return SOUTHAFRICA for 'southafrica'")
        .isEqualTo(AzureRegion.SOUTHAFRICA);

    assertThat(AzureRegion.fromString("sweden"))
        .as("fromString should return SWEDEN for 'sweden'")
        .isEqualTo(AzureRegion.SWEDEN);

    assertThat(AzureRegion.fromString("switzerland"))
        .as("fromString should return SWITZERLAND for 'switzerland'")
        .isEqualTo(AzureRegion.SWITZERLAND);

    assertThat(AzureRegion.fromString("uae"))
        .as("fromString should return UAE for 'uae'")
        .isEqualTo(AzureRegion.UAE);

    assertThat(AzureRegion.fromString("uk"))
        .as("fromString should return UK for 'uk'")
        .isEqualTo(AzureRegion.UK);

    assertThat(AzureRegion.fromString("unitedstates"))
        .as("fromString should return UNITEDSTATES for 'unitedstates'")
        .isEqualTo(AzureRegion.UNITEDSTATES);

    assertThat(AzureRegion.fromString("unitedstateseuap"))
        .as("fromString should return UNITEDSTATES_EUAP for 'unitedstateseuap'")
        .isEqualTo(AzureRegion.UNITEDSTATES_EUAP);

    assertThat(AzureRegion.fromString("eastasiastage"))
        .as("fromString should return EAST_ASIA_STAGE for 'eastasiastage'")
        .isEqualTo(AzureRegion.EAST_ASIA_STAGE);

    assertThat(AzureRegion.fromString("southeastasiastage"))
        .as("fromString should return SOUTHEAST_ASIA_STAGE for 'southeastasiastage'")
        .isEqualTo(AzureRegion.SOUTHEAST_ASIA_STAGE);

    assertThat(AzureRegion.fromString("brazilus"))
        .as("fromString should return BRAZIL_US for 'brazilus'")
        .isEqualTo(AzureRegion.BRAZIL_US);

    assertThat(AzureRegion.fromString("eastusstg"))
        .as("fromString should return EAST_US_STG for 'eastusstg'")
        .isEqualTo(AzureRegion.EAST_US_STG);

    assertThat(AzureRegion.fromString("northcentralus"))
        .as("fromString should return NORTH_CENTRAL_US for 'northcentralus'")
        .isEqualTo(AzureRegion.NORTH_CENTRAL_US);

    assertThat(AzureRegion.fromString("westus"))
        .as("fromString should return WEST_US for 'westus'")
        .isEqualTo(AzureRegion.WEST_US);

    assertThat(AzureRegion.fromString("japanwest"))
        .as("fromString should return JAPAN_WEST for 'japanwest'")
        .isEqualTo(AzureRegion.JAPAN_WEST);

    assertThat(AzureRegion.fromString("jioindiawest"))
        .as("fromString should return JIO_INDIA_WEST for 'jioindiawest'")
        .isEqualTo(AzureRegion.JIO_INDIA_WEST);

    assertThat(AzureRegion.fromString("centraluseuap"))
        .as("fromString should return CENTRAL_US_EUAP for 'centraluseuap'")
        .isEqualTo(AzureRegion.CENTRAL_US_EUAP);

    assertThat(AzureRegion.fromString("eastus2euap"))
        .as("fromString should return EAST_US_EUAP for 'eastus2euap'")
        .isEqualTo(AzureRegion.EAST_US_EUAP);

    assertThat(AzureRegion.fromString("southcentralusstg"))
        .as("fromString should return SOUTH_CENTRAL_US_STG for 'southcentralusstg'")
        .isEqualTo(AzureRegion.SOUTH_CENTRAL_US_STG);

    assertThat(AzureRegion.fromString("westcentralus"))
        .as("fromString should return WEST_CENTRAL_US for 'westcentralus'")
        .isEqualTo(AzureRegion.WEST_CENTRAL_US);

    assertThat(AzureRegion.fromString("southafricawest"))
        .as("fromString should return SOUTH_AFRICA_WEST for 'southafricawest'")
        .isEqualTo(AzureRegion.SOUTH_AFRICA_WEST);

    assertThat(AzureRegion.fromString("australiacentral"))
        .as("fromString should return AUSTRALIA_CENTRAL for 'australiacentral'")
        .isEqualTo(AzureRegion.AUSTRALIA_CENTRAL);

    assertThat(AzureRegion.fromString("australiacentral2"))
        .as("fromString should return AUSTRALIA_CENTRAL2 for 'australiacentral2'")
        .isEqualTo(AzureRegion.AUSTRALIA_CENTRAL2);

    assertThat(AzureRegion.fromString("australiasoutheast"))
        .as("fromString should return AUSTRALIA_SOUTHEAST for 'australiasoutheast'")
        .isEqualTo(AzureRegion.AUSTRALIA_SOUTHEAST);

    assertThat(AzureRegion.fromString("jioindiacentral"))
        .as("fromString should return JIO_INDIA_CENTRAL for 'jioindiacentral'")
        .isEqualTo(AzureRegion.JIO_INDIA_CENTRAL);

    assertThat(AzureRegion.fromString("koreasouth"))
        .as("fromString should return KOREA_SOUTH for 'koreasouth'")
        .isEqualTo(AzureRegion.KOREA_SOUTH);

    assertThat(AzureRegion.fromString("southindia"))
        .as("fromString should return SOUTH_INDIA for 'southindia'")
        .isEqualTo(AzureRegion.SOUTH_INDIA);

    assertThat(AzureRegion.fromString("westindia"))
        .as("fromString should return WEST_INDIA for 'westindia'")
        .isEqualTo(AzureRegion.WEST_INDIA);

    assertThat(AzureRegion.fromString("canadaeast"))
        .as("fromString should return CANADA_EAST for 'canadaeast'")
        .isEqualTo(AzureRegion.CANADA_EAST);

    assertThat(AzureRegion.fromString("francesouth"))
        .as("fromString should return FRANCE_SOUTH for 'francesouth'")
        .isEqualTo(AzureRegion.FRANCE_SOUTH);

    assertThat(AzureRegion.fromString("germanynorth"))
        .as("fromString should return GERMANY_NORTH for 'germanynorth'")
        .isEqualTo(AzureRegion.GERMANY_NORTH);

    assertThat(AzureRegion.fromString("norwaywest"))
        .as("fromString should return NORWAY_WEST for 'norwaywest'")
        .isEqualTo(AzureRegion.NORWAY_WEST);

    assertThat(AzureRegion.fromString("switzerlandwest"))
        .as("fromString should return SWITZERLAND_WEST for 'switzerlandwest'")
        .isEqualTo(AzureRegion.SWITZERLAND_WEST);

    assertThat(AzureRegion.fromString("ukwest"))
        .as("fromString should return UK_WEST for 'ukwest'")
        .isEqualTo(AzureRegion.UK_WEST);

    assertThat(AzureRegion.fromString("uaecentral"))
        .as("fromString should return UAE_CENTRAL for 'uaecentral'")
        .isEqualTo(AzureRegion.UAE_CENTRAL);

    assertThat(AzureRegion.fromString("brazilsoutheast"))
        .as("fromString should return BRAZIL_SOUTHEAST for 'brazilsoutheast'")
        .isEqualTo(AzureRegion.BRAZIL_SOUTHEAST);

    assertThat(AzureRegion.fromString("global"))
        .as("fromString should return GLOBAL for 'global'")
        .isEqualTo(AzureRegion.GLOBAL);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureActionsWithCorrectSize() {
    Collection<AzureRegion> values = AzureRegion.values();
    
    assertThat(values)
        .as("Values should contain all specified AzureRegions")
        .contains(
            AzureRegion.EAST_US,
            AzureRegion.SOUTH_CENTRAL_US,
            AzureRegion.WEST_US2,
            AzureRegion.WEST_US3,
            AzureRegion.AUSTRALIA_EAST,
            AzureRegion.SOUTHEAST_ASIA,
            AzureRegion.NORTH_EUROPE,
            AzureRegion.SWEDEN_CENTRAL,
            AzureRegion.UK_SOUTH,
            AzureRegion.WEST_EUROPE,
            AzureRegion.CENTRAL_US,
            AzureRegion.SOUTH_AFRICA_NORTH,
            AzureRegion.CENTRAL_INDIA,
            AzureRegion.EAST_ASIA,
            AzureRegion.JAPAN_EAST,
            AzureRegion.KOREA_CENTRAL,
            AzureRegion.NEWZEALAND_NORTH,
            AzureRegion.CANADA_CENTRAL,
            AzureRegion.FRANCE_CENTRAL,
            AzureRegion.GERMANY_WEST_CENTRAL,
            AzureRegion.ITALY_NORTH,
            AzureRegion.NORWAY_EAST,
            AzureRegion.POLAND_CENTRAL,
            AzureRegion.SPAIN_CENTRAL,
            AzureRegion.SWITZERLAND_NORTH,
            AzureRegion.MEXICO_CENTRAL,
            AzureRegion.UAE_NORTH,
            AzureRegion.BRAZIL_SOUTH,
            AzureRegion.ISRAEL_CENTRAL,
            AzureRegion.QATAR_CENTRAL,
            AzureRegion.CENTRAL_US_STAGE,
            AzureRegion.EAST_US_STAGE,
            AzureRegion.EAST_US2_STAGE,
            AzureRegion.NORTH_CENTRAL_US_STAGE,
            AzureRegion.SOUTH_CENTRAL_US_STAGE,
            AzureRegion.WEST_US_STAGE,
            AzureRegion.WEST_US2_STAGE,
            AzureRegion.ASIA,
            AzureRegion.ASIA_PACIFIC,
            AzureRegion.AUSTRALIA,
            AzureRegion.BRAZIL,
            AzureRegion.CANADA,
            AzureRegion.EUROPE,
            AzureRegion.FRANCE,
            AzureRegion.GERMANY,
            AzureRegion.GLOBAL,
            AzureRegion.INDIA,
            AzureRegion.ISRAEL,
            AzureRegion.ITALY,
            AzureRegion.JAPAN,
            AzureRegion.KOREA,
            AzureRegion.NEWZEALAND,
            AzureRegion.NORWAY,
            AzureRegion.POLAND,
            AzureRegion.QATAR,
            AzureRegion.SINGAPORE,
            AzureRegion.SOUTHAFRICA,
            AzureRegion.SWEDEN,
            AzureRegion.SWITZERLAND,
            AzureRegion.UAE,
            AzureRegion.UK,
            AzureRegion.UNITEDSTATES,
            AzureRegion.UNITEDSTATES_EUAP,
            AzureRegion.EAST_ASIA_STAGE,
            AzureRegion.SOUTHEAST_ASIA_STAGE,
            AzureRegion.BRAZIL_US,
            AzureRegion.EAST_US2,
            AzureRegion.EAST_US_STG,
            AzureRegion.NORTH_CENTRAL_US,
            AzureRegion.WEST_US,
            AzureRegion.JAPAN_WEST,
            AzureRegion.JIO_INDIA_WEST,
            AzureRegion.CENTRAL_US_EUAP,
            AzureRegion.EAST_US_EUAP,
            AzureRegion.SOUTH_CENTRAL_US_STG,
            AzureRegion.WEST_CENTRAL_US,
            AzureRegion.SOUTH_AFRICA_WEST,
            AzureRegion.AUSTRALIA_CENTRAL,
            AzureRegion.AUSTRALIA_CENTRAL2,
            AzureRegion.AUSTRALIA_SOUTHEAST,
            AzureRegion.JIO_INDIA_CENTRAL,
            AzureRegion.KOREA_SOUTH,
            AzureRegion.SOUTH_INDIA,
            AzureRegion.WEST_INDIA,
            AzureRegion.CANADA_EAST,
            AzureRegion.FRANCE_SOUTH,
            AzureRegion.GERMANY_NORTH,
            AzureRegion.NORWAY_WEST,
            AzureRegion.SWITZERLAND_WEST,
            AzureRegion.UK_WEST,
            AzureRegion.UAE_CENTRAL,
            AzureRegion.BRAZIL_SOUTHEAST);
  }
}