package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * Allows you to specify the type of endpoint.
 * Set this to AzureDNSZone to create a large number of accounts in a single subscription,
 * which creates accounts in an Azure DNS Zone and the endpoint URL will have an alphanumeric DNS Zone identifier.
 */
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
