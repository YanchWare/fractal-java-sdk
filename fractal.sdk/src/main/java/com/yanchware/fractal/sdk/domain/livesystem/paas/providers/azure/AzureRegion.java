package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * Choose the Azure region that's right for you and your customers
 */
public final class AzureRegion extends ExtendableEnum<AzureRegion> {
 
  /**
   * (US) East US
   */
  public static final AzureRegion EAST_US = fromString("eastus");

  /**
   * (US) East US 2
   */
  public static final AzureRegion EAST_US2 = fromString("eastus2");

  /**
   * (US) South Central US
   */
  public static final AzureRegion SOUTH_CENTRAL_US = fromString("southcentralus");

  /**
   * (US) West US 2
   */
  public static final AzureRegion WEST_US2 = fromString("westus2");

  /**
   * (US) West US 3
   */
  public static final AzureRegion WEST_US_3 = fromString("westus3");

  /**
   * (Asia Pacific) Australia East
   */
  public static final AzureRegion AUSTRALIA_EAST = fromString("australiaeast");

  /**
   * (Asia Pacific) Southeast Asia
   */
  public static final AzureRegion SOUTHEAST_ASIA = fromString("southeastasia");

  /**
   * (Europe) North Europe
   */
  public static final AzureRegion NORTH_EUROPE = fromString("northeurope");

  /**
   * (Europe) Sweden Central
   */
  public static final AzureRegion SWEDEN_CENTRAL = fromString("swedencentral");

  /**
   * (Europe) UK South
   */
  public static final AzureRegion UK_SOUTH = fromString("uksouth");

  /**
   * (Europe) West Europe
   */
  public static final AzureRegion WEST_EUROPE = fromString("westeurope");

  /**
   * (US) Central US
   */
  public static final AzureRegion CENTRAL_US = fromString("centralus");

  /**
   * (Africa) South Africa North
   */
  public static final AzureRegion SOUTH_AFRICA_NORTH = fromString("southafricanorth");

  /**
   * (Asia Pacific) Central India
   */
  public static final AzureRegion CENTRAL_INDIA = fromString("centralindia");

  /**
   * (Asia Pacific) East Asia
   */
  public static final AzureRegion EAST_ASIA = fromString("eastasia");

  /**
   * (Asia Pacific) Japan East
   */
  public static final AzureRegion JAPAN_EAST = fromString("japaneast");

  /**
   * (Asia Pacific) Korea Central
   */
  public static final AzureRegion KOREA_CENTRAL = fromString("koreacentral");

  /**
   * (Canada) Canada Central
   */
  public static final AzureRegion CANADA_CENTRAL = fromString("canadacentral");

  /**
   * (Europe) France Central
   */
  public static final AzureRegion FRANCE_CENTRAL = fromString("francecentral");

  /**
   * (Europe) Germany West Central
   */
  public static final AzureRegion GERMANY_WEST_CENTRAL = fromString("germanywestcentral");

  /**
   * (Europe) Italy North
   */
  public static final AzureRegion ITALY_NORTH = fromString("italynorth");

  /**
   * (Europe) Norway East
   */
  public static final AzureRegion NORWAY_EAST = fromString("norwayeast");

  /**
   * (Europe) Poland Central
   */
  public static final AzureRegion POLAND_CENTRAL = fromString("polandcentral");

  /**
   * (Europe) Switzerland North
   */
  public static final AzureRegion SWITZERLAND_NORTH = fromString("switzerlandnorth");

  /**
   * (Middle East) UAE North
   */
  public static final AzureRegion UAE_NORTH = fromString("uaenorth");

  /**
   * (South America) Brazil South
   */
  public static final AzureRegion BRAZIL_SOUTH = fromString("brazilsouth");
  

  /**
   * (Middle East) Israel Central
   */
  public static final AzureRegion ISRAEL_CENTRAL = fromString("israelcentral");

  /**
   * (Middle East) Qatar Central
   */
  public static final AzureRegion QATAR_CENTRAL = fromString("qatarcentral");
  

  /**
   * (US) North Central US
   */
  public static final AzureRegion NORTH_CENTRAL_US = fromString("northcentralus");

  /**
   * (US) West US
   */
  public static final AzureRegion WEST_US = fromString("westus");

  /**
   * (Asia Pacific) Japan West
   */
  public static final AzureRegion JAPAN_WEST = fromString("japanwest");

  /**
   * (Asia Pacific) Jio India West
   */
  public static final AzureRegion JIO_INDIA_WEST = fromString("jioindiawest");
  

  /**
   * (US) West Central US
   */
  public static final AzureRegion WEST_CENTRAL_US = fromString("westcentralus");

  /**
   * (Africa) South Africa West
   */
  public static final AzureRegion SOUTH_AFRICA_WEST = fromString("southafricawest");

  /**
   * (Asia Pacific) Australia Central
   */
  public static final AzureRegion AUSTRALIA_CENTRAL = fromString("australiacentral");

  /**
   * (Asia Pacific) Australia Central 2
   */
  public static final AzureRegion AUSTRALIA_CENTRAL2 = fromString("australiacentral2");

  /**
   * (Asia Pacific) Australia Southeast
   */
  public static final AzureRegion AUSTRALIA_SOUTHEAST = fromString("australiasoutheast");

  /**
   * (Asia Pacific) Jio India Central
   */
  public static final AzureRegion JIO_INDIA_CENTRAL = fromString("jioindiacentral");

  /**
   * (Asia Pacific) Korea South
   */
  public static final AzureRegion KOREA_SOUTH = fromString("koreasouth");

  /**
   * (Asia Pacific) South India
   */
  public static final AzureRegion SOUTH_INDIA = fromString("southindia");

  /**
   * (Asia Pacific) West India
   */
  public static final AzureRegion WEST_INDIA = fromString("westindia");

  /**
   * (Canada) Canada East
   */
  public static final AzureRegion CANADA_EAST = fromString("canadaeast");

  /**
   * (Europe) France South
   */
  public static final AzureRegion FRANCE_SOUTH = fromString("francesouth");

  /**
   * (Europe) Germany North
   */
  public static final AzureRegion GERMANY_NORTH = fromString("germanynorth");

  /**
   * (Europe) Norway West
   */
  public static final AzureRegion NORWAY_WEST = fromString("norwaywest");

  /**
   * (Europe) Switzerland West
   */
  public static final AzureRegion SWITZERLAND_WEST = fromString("switzerlandwest");

  /**
   * (Europe) UK West
   */
  public static final AzureRegion UK_WEST = fromString("ukwest");

  /**
   * (Middle East) UAE Central
   */
  public static final AzureRegion UAE_CENTRAL = fromString("uaecentral");

  /**
   * (South America) Brazil Southeast
   */
  public static final AzureRegion BRAZIL_SOUTHEAST = fromString("brazilsoutheast");
  public static final AzureRegion GLOBAL = fromString("Global");


  /**
   * Creates or finds a AzureRegion from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding AzureRegion.
   */
  @JsonCreator
  public static AzureRegion fromString(String name) {
    return fromString(name, AzureRegion.class);
  }

  /**
   * Gets known AzureRegion values.
   *
   * @return known AzureRegion values.
   */
  public static Collection<AzureRegion> values() {
    return values(AzureRegion.class);
  }
}
