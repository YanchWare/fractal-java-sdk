package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

public final class AzureActiveDirectoryAccountType extends ExtendableEnum<AzureActiveDirectoryAccountType> {
  public static final AzureActiveDirectoryAccountType USER = fromString("User");
  public static final AzureActiveDirectoryAccountType COMPUTER = fromString("Computer");

  public AzureActiveDirectoryAccountType() {
  }

  @JsonCreator
  public static AzureActiveDirectoryAccountType fromString(String name) {
    return fromString(name, AzureActiveDirectoryAccountType.class);
  }

  public static Collection<AzureActiveDirectoryAccountType> values() {
    return values(AzureActiveDirectoryAccountType.class);
  }
}
