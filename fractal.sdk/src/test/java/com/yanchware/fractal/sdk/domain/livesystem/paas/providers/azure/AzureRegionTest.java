package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureRegionTest {

  @Test
  public void azureActionConstants_shouldNotBeNull() {
    assertThat(AzureRegion.EAST_US)
        .as("EAST_US constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.EAST_US2)
        .as("EAST_US2 constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.SOUTH_CENTRAL_US)
        .as("SOUTH_CENTRAL_US constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.WEST_US2)
        .as("WEST_US2 constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.WEST_US_3)
        .as("WEST_US_3 constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.AUSTRALIA_EAST)
        .as("AUSTRALIA_EAST constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.SOUTHEAST_ASIA)
        .as("SOUTHEAST_ASIA constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.NORTH_EUROPE)
        .as("NORTH_EUROPE constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.SWEDEN_CENTRAL)
        .as("SWEDEN_CENTRAL constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.UK_SOUTH)
        .as("UK_SOUTH constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.WEST_EUROPE)
        .as("WEST_EUROPE constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.CENTRAL_US)
        .as("CENTRAL_US constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.SOUTH_AFRICA_NORTH)
        .as("SOUTH_AFRICA_NORTH constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.CENTRAL_INDIA)
        .as("CENTRAL_INDIA constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.EAST_ASIA)
        .as("EAST_ASIA constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.JAPAN_EAST)
        .as("JAPAN_EAST constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.KOREA_CENTRAL)
        .as("KOREA_CENTRAL constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.CANADA_CENTRAL)
        .as("CANADA_CENTRAL constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.FRANCE_CENTRAL)
        .as("FRANCE_CENTRAL constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.GERMANY_WEST_CENTRAL)
        .as("GERMANY_WEST_CENTRAL constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.ITALY_NORTH)
        .as("ITALY_NORTH constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.NORWAY_EAST)
        .as("NORWAY_EAST constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.POLAND_CENTRAL)
        .as("POLAND_CENTRAL constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.SWITZERLAND_NORTH)
        .as("SWITZERLAND_NORTH constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.UAE_NORTH)
        .as("UAE_NORTH constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.BRAZIL_SOUTH)
        .as("BRAZIL_SOUTH constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.ISRAEL_CENTRAL)
        .as("ISRAEL_CENTRAL constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.QATAR_CENTRAL)
        .as("QATAR_CENTRAL constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.NORTH_CENTRAL_US)
        .as("NORTH_CENTRAL_US constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.WEST_US)
        .as("WEST_US constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.JAPAN_WEST)
        .as("JAPAN_WEST constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.JIO_INDIA_WEST)
        .as("JIO_INDIA_WEST constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.WEST_CENTRAL_US)
        .as("WEST_CENTRAL_US constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.SOUTH_AFRICA_WEST)
        .as("SOUTH_AFRICA_WEST constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.AUSTRALIA_CENTRAL)
        .as("AUSTRALIA_CENTRAL constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.AUSTRALIA_CENTRAL2)
        .as("AUSTRALIA_CENTRAL2 constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.AUSTRALIA_SOUTHEAST)
        .as("AUSTRALIA_SOUTHEAST constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.JIO_INDIA_CENTRAL)
        .as("JIO_INDIA_CENTRAL constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.KOREA_SOUTH)
        .as("KOREA_SOUTH constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.SOUTH_INDIA)
        .as("SOUTH_INDIA constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.WEST_INDIA)
        .as("WEST_INDIA constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.CANADA_EAST)
        .as("CANADA_EAST constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.FRANCE_SOUTH)
        .as("FRANCE_SOUTH constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.GERMANY_NORTH)
        .as("GERMANY_NORTH constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.NORWAY_WEST)
        .as("NORWAY_WEST constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.SWITZERLAND_WEST)
        .as("SWITZERLAND_WEST constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.UK_WEST)
        .as("UK_WEST constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.UAE_CENTRAL)
        .as("UAE_CENTRAL constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.BRAZIL_SOUTHEAST)
        .as("BRAZIL_SOUTHEAST constant should not be null")
        .isNotNull();

    assertThat(AzureRegion.GLOBAL)
        .as("GLOBAL constant should not be null")
        .isNotNull();
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
        .isEqualTo(AzureRegion.WEST_US_3);

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

    assertThat(AzureRegion.fromString("switzerlandnorth"))
        .as("fromString should return SWITZERLAND_NORTH for 'switzerlandnorth'")
        .isEqualTo(AzureRegion.SWITZERLAND_NORTH);

    assertThat(AzureRegion.fromString("uaenorth"))
        .as("fromString should return UAE_NORTH for 'uaenorth'")
        .isEqualTo(AzureRegion.UAE_NORTH);

    assertThat(AzureRegion.fromString("brazilsouth"))
        .as("fromString should return BRAZIL_SOUTH for 'brazilsouth'")
        .isEqualTo(AzureRegion.BRAZIL_SOUTH);

    assertThat(AzureRegion.fromString("israelcentral"))
        .as("fromString should return ISRAEL_CENTRAL for 'israelcentral'")
        .isEqualTo(AzureRegion.ISRAEL_CENTRAL);

    assertThat(AzureRegion.fromString("qatarcentral"))
        .as("fromString should return QATAR_CENTRAL for 'qatarcentral'")
        .isEqualTo(AzureRegion.QATAR_CENTRAL);

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

    assertThat(AzureRegion.fromString("Global"))
        .as("fromString should return GLOBAL for 'Global'")
        .isEqualTo(AzureRegion.GLOBAL);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureActionsWithCorrectSize() {
    Collection<AzureRegion> values = AzureRegion.values();
    
    assertThat(values)
        .as("Values should contain all specified AzureRegions")
        .contains(AzureRegion.EAST_US,
            AzureRegion.EAST_US2,
            AzureRegion.SOUTH_CENTRAL_US,
            AzureRegion.WEST_US2,
            AzureRegion.WEST_US_3,
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
            AzureRegion.CANADA_CENTRAL,
            AzureRegion.FRANCE_CENTRAL,
            AzureRegion.GERMANY_WEST_CENTRAL,
            AzureRegion.ITALY_NORTH,
            AzureRegion.NORWAY_EAST,
            AzureRegion.POLAND_CENTRAL,
            AzureRegion.SWITZERLAND_NORTH,
            AzureRegion.UAE_NORTH,
            AzureRegion.BRAZIL_SOUTH,
            AzureRegion.ISRAEL_CENTRAL,
            AzureRegion.QATAR_CENTRAL,
            AzureRegion.NORTH_CENTRAL_US,
            AzureRegion.WEST_US,
            AzureRegion.JAPAN_WEST,
            AzureRegion.JIO_INDIA_WEST,
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
            AzureRegion.BRAZIL_SOUTHEAST,
            AzureRegion.GLOBAL);
  }
}