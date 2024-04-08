package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cdn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

public class AzureCdnSku extends ExtendableEnum<AzureCdnSku> {
  public static final AzureCdnSku STANDARD_VERIZON = fromString("Standard_Verizon");
  public static final AzureCdnSku PREMIUM_VERIZON = fromString("Premium_Verizon");
  public static final AzureCdnSku CUSTOM_VERIZON = fromString("Custom_Verizon");
  public static final AzureCdnSku STANDARD_AKAMAI = fromString("Standard_Akamai");
  public static final AzureCdnSku STANDARD_CHINA_CDN = fromString("Standard_ChinaCdn");
  public static final AzureCdnSku STANDARD_MICROSOFT = fromString("Standard_Microsoft");
  public static final AzureCdnSku STANDARD_AZURE_FRONT_DOOR = fromString("Standard_AzureFrontDoor");
  public static final AzureCdnSku PREMIUM_AZURE_FRONT_DOOR = fromString("Premium_AzureFrontDoor");
  public static final AzureCdnSku STANDARD_955BAND_WIDTH_CHINA_CDN = fromString("Standard_955BandWidth_ChinaCdn");
  public static final AzureCdnSku STANDARD_AVG_BAND_WIDTH_CHINA_CDN = fromString("Standard_AvgBandWidth_ChinaCdn");
  public static final AzureCdnSku STANDARD_PLUS_CHINA_CDN = fromString("StandardPlus_ChinaCdn");
  public static final AzureCdnSku STANDARD_PLUS_955BAND_WIDTH_CHINA_CDN = fromString("StandardPlus_955BandWidth_ChinaCdn");
  public static final AzureCdnSku STANDARD_PLUS_AVG_BAND_WIDTH_CHINA_CDN = fromString("StandardPlus_AvgBandWidth_ChinaCdn");

  /**
   * Creates or finds a AzureCdnSku from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding AzureCdnSku.
   */
  @JsonCreator
  public static AzureCdnSku fromString(String name) {
    return fromString(name, AzureCdnSku.class);
  }

  /**
   * Gets known AzureCdnSku values.
   *
   * @return known AzureCdnSku values.
   */
  public static Collection<AzureCdnSku> values() {
    return values(AzureCdnSku.class);
  }

}