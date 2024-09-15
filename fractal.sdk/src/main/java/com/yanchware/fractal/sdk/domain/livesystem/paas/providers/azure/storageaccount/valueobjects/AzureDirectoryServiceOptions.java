package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * Indicates the directory service used.
 */
public final class AzureDirectoryServiceOptions extends ExtendableEnum<AzureDirectoryServiceOptions> {
  public static final AzureDirectoryServiceOptions NONE = fromString("None");
  public static final AzureDirectoryServiceOptions AADDS = fromString("AADDS");
  public static final AzureDirectoryServiceOptions AD = fromString("AD");
  public static final AzureDirectoryServiceOptions AADKERB = fromString("AADKERB");

  @JsonCreator
  public static AzureDirectoryServiceOptions fromString(String name) {
    return fromString(name, AzureDirectoryServiceOptions.class);
  }

  public static Collection<AzureDirectoryServiceOptions> values() {
    return values(AzureDirectoryServiceOptions.class);
  }
}
