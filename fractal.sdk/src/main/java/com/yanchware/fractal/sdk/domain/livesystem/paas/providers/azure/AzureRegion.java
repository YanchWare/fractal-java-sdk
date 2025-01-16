package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * Choose the Azure region that's right for you and your customers
 */
public final class AzureRegion extends ExtendableEnum<AzureRegion> {

  public static final AzureRegion EAST_US = fromString("eastus");
  public static final AzureRegion SOUTH_CENTRAL_US = fromString("southcentralus");
  public static final AzureRegion WEST_US2 = fromString("westus2");
  public static final AzureRegion WEST_US3 = fromString("westus3");
  public static final AzureRegion AUSTRALIA_EAST = fromString("australiaeast");
  public static final AzureRegion SOUTHEAST_ASIA = fromString("southeastasia");
  public static final AzureRegion NORTH_EUROPE = fromString("northeurope");
  public static final AzureRegion SWEDEN_CENTRAL = fromString("swedencentral");
  public static final AzureRegion UK_SOUTH = fromString("uksouth");
  public static final AzureRegion WEST_EUROPE = fromString("westeurope");
  public static final AzureRegion CENTRAL_US = fromString("centralus");
  public static final AzureRegion SOUTH_AFRICA_NORTH = fromString("southafricanorth");
  public static final AzureRegion CENTRAL_INDIA = fromString("centralindia");
  public static final AzureRegion EAST_ASIA = fromString("eastasia");
  public static final AzureRegion JAPAN_EAST = fromString("japaneast");
  public static final AzureRegion KOREA_CENTRAL = fromString("koreacentral");
  public static final AzureRegion NEWZEALAND_NORTH = fromString("newzealandnorth");
  public static final AzureRegion CANADA_CENTRAL = fromString("canadacentral");
  public static final AzureRegion FRANCE_CENTRAL = fromString("francecentral");
  public static final AzureRegion GERMANY_WEST_CENTRAL = fromString("germanywestcentral");
  public static final AzureRegion ITALY_NORTH = fromString("italynorth");
  public static final AzureRegion NORWAY_EAST = fromString("norwayeast");
  public static final AzureRegion POLAND_CENTRAL = fromString("polandcentral");
  public static final AzureRegion SPAIN_CENTRAL = fromString("spaincentral");
  public static final AzureRegion SWITZERLAND_NORTH = fromString("switzerlandnorth");
  public static final AzureRegion MEXICO_CENTRAL = fromString("mexicocentral");
  public static final AzureRegion UAE_NORTH = fromString("uaenorth");
  public static final AzureRegion BRAZIL_SOUTH = fromString("brazilsouth");
  public static final AzureRegion ISRAEL_CENTRAL = fromString("israelcentral");
  public static final AzureRegion QATAR_CENTRAL = fromString("qatarcentral");
  public static final AzureRegion CENTRAL_US_STAGE = fromString("centralusstage");
  public static final AzureRegion EAST_US_STAGE = fromString("eastusstage");
  public static final AzureRegion EAST_US2_STAGE = fromString("eastus2stage");
  public static final AzureRegion NORTH_CENTRAL_US_STAGE = fromString("northcentralusstage");
  public static final AzureRegion SOUTH_CENTRAL_US_STAGE = fromString("southcentralusstage");
  public static final AzureRegion WEST_US_STAGE = fromString("westusstage");
  public static final AzureRegion WEST_US2_STAGE = fromString("westus2stage");
  public static final AzureRegion ASIA = fromString("asia");
  public static final AzureRegion ASIA_PACIFIC = fromString("asiapacific");
  public static final AzureRegion AUSTRALIA = fromString("australia");
  public static final AzureRegion BRAZIL = fromString("brazil");
  public static final AzureRegion CANADA = fromString("canada");
  public static final AzureRegion EUROPE = fromString("europe");
  public static final AzureRegion FRANCE = fromString("france");
  public static final AzureRegion GERMANY = fromString("germany");
  public static final AzureRegion GLOBAL = fromString("global");
  public static final AzureRegion INDIA = fromString("india");
  public static final AzureRegion ISRAEL = fromString("israel");
  public static final AzureRegion ITALY = fromString("italy");
  public static final AzureRegion JAPAN = fromString("japan");
  public static final AzureRegion KOREA = fromString("korea");
  public static final AzureRegion NEWZEALAND = fromString("newzealand");
  public static final AzureRegion NORWAY = fromString("norway");
  public static final AzureRegion POLAND = fromString("poland");
  public static final AzureRegion QATAR = fromString("qatar");
  public static final AzureRegion SINGAPORE = fromString("singapore");
  public static final AzureRegion SOUTHAFRICA = fromString("southafrica");
  public static final AzureRegion SWEDEN = fromString("sweden");
  public static final AzureRegion SWITZERLAND = fromString("switzerland");
  public static final AzureRegion UAE = fromString("uae");
  public static final AzureRegion UK = fromString("uk");
  public static final AzureRegion UNITEDSTATES = fromString("unitedstates");
  public static final AzureRegion UNITEDSTATES_EUAP = fromString("unitedstateseuap");
  public static final AzureRegion EAST_ASIA_STAGE = fromString("eastasiastage");
  public static final AzureRegion SOUTHEAST_ASIA_STAGE = fromString("southeastasiastage");
  public static final AzureRegion BRAZIL_US = fromString("brazilus");
  public static final AzureRegion EAST_US2 = fromString("eastus2");
  public static final AzureRegion EAST_US_STG = fromString("eastusstg");
  public static final AzureRegion NORTH_CENTRAL_US = fromString("northcentralus");
  public static final AzureRegion WEST_US = fromString("westus");
  public static final AzureRegion JAPAN_WEST = fromString("japanwest");
  public static final AzureRegion JIO_INDIA_WEST = fromString("jioindiawest");
  public static final AzureRegion CENTRAL_US_EUAP = fromString("centraluseuap");
  public static final AzureRegion EAST_US_EUAP = fromString("eastus2euap");
  public static final AzureRegion SOUTH_CENTRAL_US_STG = fromString("southcentralusstg");
  public static final AzureRegion WEST_CENTRAL_US = fromString("westcentralus");
  public static final AzureRegion SOUTH_AFRICA_WEST = fromString("southafricawest");
  public static final AzureRegion AUSTRALIA_CENTRAL = fromString("australiacentral");
  public static final AzureRegion AUSTRALIA_CENTRAL2 = fromString("australiacentral2");
  public static final AzureRegion AUSTRALIA_SOUTHEAST = fromString("australiasoutheast");
  public static final AzureRegion JIO_INDIA_CENTRAL = fromString("jioindiacentral");
  public static final AzureRegion KOREA_SOUTH = fromString("koreasouth");
  public static final AzureRegion SOUTH_INDIA = fromString("southindia");
  public static final AzureRegion WEST_INDIA = fromString("westindia");
  public static final AzureRegion CANADA_EAST = fromString("canadaeast");
  public static final AzureRegion FRANCE_SOUTH = fromString("francesouth");
  public static final AzureRegion GERMANY_NORTH = fromString("germanynorth");
  public static final AzureRegion NORWAY_WEST = fromString("norwaywest");
  public static final AzureRegion SWITZERLAND_WEST = fromString("switzerlandwest");
  public static final AzureRegion UK_WEST = fromString("ukwest");
  public static final AzureRegion UAE_CENTRAL = fromString("uaecentral");
  public static final AzureRegion BRAZIL_SOUTHEAST = fromString("brazilsoutheast");


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
