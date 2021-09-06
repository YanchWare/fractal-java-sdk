package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

public enum AzureRegion {
    US_EAST("eastus"),
    US_EAST2("eastus2"),
    US_SOUTH_CENTRAL("southcentralus"),
    US_WEST2("westus2"),
    US_CENTRAL("centralus"),
    US_NORTH_CENTRAL("northcentralus"),
    US_WEST("westus"),
    US_WEST_CENTRAL("westcentralus"),
    CANADA_CENTRAL("canadacentral"),
    CANADA_EAST("canadaeast"),
    BRAZIL_SOUTH("brazilsouth"),
    BRAZIL_SOUTHEAST("brazilsoutheast"),
    EUROPE_NORTH("northeurope"),
    UK_SOUTH("uksouth"),
    EUROPE_WEST("westeurope"),
    FRANCE_CENTRAL("francecentral"),
    GERMANY_WEST_CENTRAL("germanywestcentral"),
    NORWAY_EAST("norwayeast"),
    SWITZERLAND_NORTH("switzerlandnorth"),
    FRANCE_SOUTH("francesouth"),
    GERMANY_NORTH("germanynorth"),
    NORWAY_WEST("norwaywest"),
    SWITZERLAND_WEST("switzerlandwest"),
    UK_WEST("ukwest"),
    AUSTRALIA_EAST("australiaeast"),
    ASIA_SOUTHEAST("southeastasia"),
    INDIA_CENTRAL("centralindia"),
    ASIA_EAST("eastasia"),
    JAPAN_EAST("japaneast"),
    KOREA_CENTRAL("koreacentral"),
    AUSTRALIA_CENTRAL("australiacentral"),
    AUSTRALIA_CENTRAL2("australiacentral2"),
    AUSTRALIA_SOUTHEAST("australiasoutheast"),
    JAPAN_WEST("japanwest"),
    KOREA_SOUTH("koreasouth"),
    INDIA_SOUTH("southindia"),
    INDIA_WEST("westindia"),
    UAE_NORTH("uaenorth"),
    UAE_CENTRAL("uaecentral"),
    SOUTHAFRICA_NORTH("southafricanorth"),
    SOUTHAFRICA_WEST("southafricawest"),
    CHINA_NORTH("chinanorth"),
    CHINA_EAST("chinaeast"),
    CHINA_NORTH2("chinanorth2"),
    CHINA_EAST2("chinaeast2"),
    GERMANY_CENTRAL("germanycentral"),
    GERMANY_NORTHEAST("germanynortheast"),
    GOV_US_VIRGINIA("usgovvirginia"),
    GOV_US_IOWA("usgoviowa"),
    GOV_US_ARIZONA("usgovarizona"),
    GOV_US_TEXAS("usgovtexas"),
    GOV_US_DOD_EAST("usdodeast"),
    GOV_US_DOD_CENTRAL("usdodcentral");

    private final String id;

    AzureRegion(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
