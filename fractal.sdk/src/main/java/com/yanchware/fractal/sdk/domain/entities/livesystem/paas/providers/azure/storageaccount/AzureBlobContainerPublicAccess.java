package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Specifies whether data in the container may be accessed publicly and the level of access. */

public enum AzureBlobContainerPublicAccess {
  /** Enum value Container. */
  CONTAINER("Container"),

  /** Enum value Blob. */
  BLOB("Blob"),

  /** Enum value None. */
  NONE("None");

  /** The actual serialized value for a PublicAccess instance. */
  private final String value;

  AzureBlobContainerPublicAccess(String value) {
    this.value = value;
  }

  /**
   * Parses a serialized value to a AzureBlobContainerPublicAccess instance.
   *
   * @param value the serialized value to parse.
   * @return the parsed AzureBlobContainerPublicAccess object, or null if unable to parse.
   */
  @JsonCreator
  public static AzureBlobContainerPublicAccess fromString(String value) {
    if (value == null) {
      return null;
    }
    AzureBlobContainerPublicAccess[] items = AzureBlobContainerPublicAccess.values();
    for (AzureBlobContainerPublicAccess item : items) {
      if (item.toString().equalsIgnoreCase(value)) {
        return item;
      }
    }
    return null;
  }

  /** {@inheritDoc} */
  @JsonValue
  @Override
  public String toString() {
    return this.value;
  }
}
