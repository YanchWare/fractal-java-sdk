package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureAccountImmutabilityPolicyState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountImmutabilityPolicyProperties {

  private Boolean allowProtectedAppendWrites;
  private Integer immutabilityPeriodSinceCreationInDays;
  private AzureAccountImmutabilityPolicyState state;

  public static AzureStorageAccountImmutabilityPolicyPropertiesBuilder builder() {
    return new AzureStorageAccountImmutabilityPolicyPropertiesBuilder();
  }

  public static class AzureStorageAccountImmutabilityPolicyPropertiesBuilder {
    private final AzureStorageAccountImmutabilityPolicyProperties instance;
    private final AzureStorageAccountImmutabilityPolicyPropertiesBuilder builder;

    public AzureStorageAccountImmutabilityPolicyPropertiesBuilder() {
      this.instance = new AzureStorageAccountImmutabilityPolicyProperties();
      this.builder = this;
    }

    /**
     * <pre>
     * This property can only be changed for disabled and unlocked time-based retention policies. 
     * When enabled, new blocks can be written to an append blob while maintaining immutability protection and compliance. 
     * Only new blocks can be added and any existing blocks cannot be modified or deleted.
     * </pre>
     */
    public AzureStorageAccountImmutabilityPolicyPropertiesBuilder withAllowProtectedAppendWrites(Boolean allowProtectedAppendWrites) {
      instance.setAllowProtectedAppendWrites(allowProtectedAppendWrites);
      return builder;
    }
    
    /**
     * <pre>
     * The immutability period for the blobs in the container since the policy creation, in days.
     * </pre>
     */
    public AzureStorageAccountImmutabilityPolicyPropertiesBuilder withImmutabilityPeriodSinceCreationInDays(Integer immutabilityPeriodSinceCreationInDays) {
      instance.setImmutabilityPeriodSinceCreationInDays(immutabilityPeriodSinceCreationInDays);
      return builder;
    }
    
    /**
     * <pre>
     * The ImmutabilityPolicy state defines the mode of the policy. 
     * Disabled state disables the policy. 
     * Unlocked state allows increase and decrease of immutability retention time and also allows toggling allowProtectedAppendWrites property. 
     * Locked state only allows the increase of the immutability retention time. 
     * A policy can only be created in a Disabled or Unlocked state and can be toggled between the two states. 
     * Only a policy in an Unlocked state can transition to a Locked state which cannot be reverted.
     * </pre>
     */
    public AzureStorageAccountImmutabilityPolicyPropertiesBuilder withState(AzureAccountImmutabilityPolicyState state) {
      instance.setState(state);
      return builder;
    }

    public AzureStorageAccountImmutabilityPolicyProperties build() {
      return instance;
    }
  }
}
