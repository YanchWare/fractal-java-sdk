package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

public final class AzureAccountImmutabilityPolicyState extends ExtendableEnum<AzureAccountImmutabilityPolicyState> {
  public static final AzureAccountImmutabilityPolicyState UNLOCKED = fromString("Unlocked");

  public static final AzureAccountImmutabilityPolicyState LOCKED = fromString("Locked");

  public static final AzureAccountImmutabilityPolicyState DISABLED = fromString("Disabled");

  @JsonCreator
  public static AzureAccountImmutabilityPolicyState fromString(String name) {
    return fromString(name, AzureAccountImmutabilityPolicyState.class);
  }

  public static Collection<AzureAccountImmutabilityPolicyState> values() {
    return values(AzureAccountImmutabilityPolicyState.class);
  }
}
