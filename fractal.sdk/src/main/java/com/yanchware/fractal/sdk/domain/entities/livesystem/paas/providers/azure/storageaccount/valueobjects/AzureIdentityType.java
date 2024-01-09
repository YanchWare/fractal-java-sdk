package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

/**
 * The identity type.
 */
public final class AzureIdentityType extends ExtendableEnum<AzureIdentityType> {
  public static final AzureIdentityType NONE = fromString("None");
  public static final AzureIdentityType SYSTEM_ASSIGNED = fromString("SystemAssigned");
  public static final AzureIdentityType USER_ASSIGNED = fromString("UserAssigned");
  public static final AzureIdentityType SYSTEM_ASSIGNED_USER_ASSIGNED = fromString("SystemAssigned,UserAssigned");

  public AzureIdentityType() {
  }

  @JsonCreator
  public static AzureIdentityType fromString(String name) {
    return fromString(name, AzureIdentityType.class);
  }

  public static Collection<AzureIdentityType> values() {
    return values(AzureIdentityType.class);
  }
}
