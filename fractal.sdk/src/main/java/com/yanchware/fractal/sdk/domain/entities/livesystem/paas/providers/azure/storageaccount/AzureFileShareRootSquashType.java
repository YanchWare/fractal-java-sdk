package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public final class AzureFileShareRootSquashType extends ExtendableEnum<AzureFileShareRootSquashType> {
  public static final AzureFileShareRootSquashType ALL_SQUASH = fromString("AllSquash");
  public static final AzureFileShareRootSquashType NO_ROOT_SQUASH = fromString("NoRootSquash");
  public static final AzureFileShareRootSquashType ROOT_SQUASH = fromString("RootSquash");

  public AzureFileShareRootSquashType() {
  }

  @JsonCreator
  public static AzureFileShareRootSquashType fromString(String name) {
    return fromString(name, AzureFileShareRootSquashType.class);
  }

  public static Collection<AzureFileShareRootSquashType> values() {
    return values(AzureFileShareRootSquashType.class);
  }
}