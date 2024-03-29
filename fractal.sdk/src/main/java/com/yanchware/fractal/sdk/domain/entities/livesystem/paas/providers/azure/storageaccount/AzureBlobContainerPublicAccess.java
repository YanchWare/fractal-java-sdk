package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents the level of public access granted to an Azure Blob Container.
 * <p>
 * This enum specifies the accessibility of the data within the container from the internet.
 * <ul>
 * <li>{@code CONTAINER}: Indicates full public read access for container and blob data.</li>
 * <li>{@code BLOB}: Indicates public read access for blobs only. Container data is not available publicly.</li>
 * <li>{@code NONE}: Indicates no public read access. Data within the container can only be accessed with an appropriate authorization.</li>
 * </ul>
 * </p>
 */

public enum AzureBlobContainerPublicAccess {
  /**
   * Full public read access for container and blob data. 
   */
  CONTAINER("Container"),

  /**
   * Public read access for blobs only. 
   * Container data is not available publicly.
   */
  BLOB("Blob"),

  /**
   * No public read access. 
   * Data within the container can only be accessed with appropriate authorization.
   */
  NONE("None");


  private final String value;

  AzureBlobContainerPublicAccess(String value) {
    this.value = value;
  }

  /**
   * Parses a serialized value to a AzureBlobContainerPublicAccess instance.
   *
   * @param value the serialized value to parse.
   * @return the parsed AzureBlobContainerPublicAccess object, or {@code null} if unable to parse.
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
