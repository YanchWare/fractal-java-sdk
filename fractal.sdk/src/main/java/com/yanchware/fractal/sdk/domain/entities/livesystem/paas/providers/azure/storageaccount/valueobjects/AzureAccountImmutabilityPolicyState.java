package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

/**
 * <pre>
 * The ImmutabilityPolicy state defines the mode of the policy. 
 * 
 * Disabled state disables the policy. 
 * 
 * Unlocked state allows increase and decrease of immutability retention time 
 * and also allows toggling allowProtectedAppendWrites property. 
 * 
 * Locked state only allows the increase of the immutability retention time.
 * </pre>
 */
public final class AzureAccountImmutabilityPolicyState extends ExtendableEnum<AzureAccountImmutabilityPolicyState> {
  public static final AzureAccountImmutabilityPolicyState DISABLED = fromString("Disabled");
  public static final AzureAccountImmutabilityPolicyState LOCKED = fromString("Locked");
  public static final AzureAccountImmutabilityPolicyState UNLOCKED = fromString("Unlocked");

  @JsonCreator
  public static AzureAccountImmutabilityPolicyState fromString(String name) {
    return fromString(name, AzureAccountImmutabilityPolicyState.class);
  }

  public static Collection<AzureAccountImmutabilityPolicyState> values() {
    return values(AzureAccountImmutabilityPolicyState.class);
  }
}
