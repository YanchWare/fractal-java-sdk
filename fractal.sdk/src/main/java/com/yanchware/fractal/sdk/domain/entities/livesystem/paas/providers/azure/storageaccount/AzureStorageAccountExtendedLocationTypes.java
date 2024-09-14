package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * The type of extendedLocation.
 */
public final class AzureStorageAccountExtendedLocationTypes extends ExtendableEnum<AzureStorageAccountExtendedLocationTypes> {
  public static final AzureStorageAccountExtendedLocationTypes EDGE_ZONE = fromString("EdgeZone");

  public AzureStorageAccountExtendedLocationTypes() {
  }

  @JsonCreator
  public static AzureStorageAccountExtendedLocationTypes fromString(String name) {
    return fromString(name, AzureStorageAccountExtendedLocationTypes.class);
  }

  public static Collection<AzureStorageAccountExtendedLocationTypes> values() {
    return values(AzureStorageAccountExtendedLocationTypes.class);
  }
}
