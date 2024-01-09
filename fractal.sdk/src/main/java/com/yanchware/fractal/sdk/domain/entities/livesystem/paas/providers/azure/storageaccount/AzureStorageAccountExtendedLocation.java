package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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

    public AzureStorageAccountExtendedLocationBuilder withName(String name) {
      extendedLocation.setName(name);
      return builder;
    }

    public AzureStorageAccountExtendedLocationBuilder withType(AzureStorageAccountExtendedLocationTypes type) {
      extendedLocation.setType(type);
      return builder;
    }

    public AzureStorageAccountExtendedLocation build() {
      return extendedLocation;
    }
  }
}
