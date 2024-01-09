package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

public final class AzureDnsEndpointType extends ExtendableEnum<AzureDnsEndpointType> {
  public static final AzureDnsEndpointType STANDARD = fromString("Standard");
  public static final AzureDnsEndpointType AZURE_DNS_ZONE = fromString("AzureDnsZone");

  @JsonCreator
  public static AzureDnsEndpointType fromString(String name) {
    return fromString(name, AzureDnsEndpointType.class);
  }

  public static Collection<AzureDnsEndpointType> values() {
    return values(AzureDnsEndpointType.class);
  }
}
