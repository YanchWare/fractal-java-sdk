package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cdn;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

public class AzureCdnSku extends ExtendableEnum<AzureCdnSku> {
  public static final AzureCdnSku STANDARD_EDGIO = fromString("Standard_Verizon");
  public static final AzureCdnSku PREMIUM_EDGIO = fromString("Premium_Verizon");
  public static final AzureCdnSku STANDARD_MICROSOFT = fromString("Standard_Microsoft");
  public static final AzureCdnSku STANDARD_AZURE_FRONT_DOOR = fromString("Standard_AzureFrontDoor");
  public static final AzureCdnSku PREMIUM_AZURE_FRONT_DOOR = fromString("Premium_AzureFrontDoor");

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