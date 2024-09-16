package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * The complex type of the extended location.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountExtendedLocation {
  private String name;
  private AzureStorageAccountExtendedLocationTypes type;

  public static AzureStorageAccountExtendedLocationBuilder builder() {
    return new AzureStorageAccountExtendedLocationBuilder();
  }


  public static class AzureStorageAccountExtendedLocationBuilder {
    private final AzureStorageAccountExtendedLocation extendedLocation;
    private final AzureStorageAccountExtendedLocationBuilder builder;

    public AzureStorageAccountExtendedLocationBuilder() {
      this.extendedLocation = new AzureStorageAccountExtendedLocation();
      this.builder = this;
    }

    /**
     * The name of the extended location.
     */
    public AzureStorageAccountExtendedLocationBuilder withName(String name) {
      extendedLocation.setName(name);
      return builder;
    }

    /**
     * The type of the extended location.
     */
    public AzureStorageAccountExtendedLocationBuilder withType(AzureStorageAccountExtendedLocationTypes type) {
      extendedLocation.setType(type);
      return builder;
    }

    public AzureStorageAccountExtendedLocation build() {
      return extendedLocation;
    }
  }
}
