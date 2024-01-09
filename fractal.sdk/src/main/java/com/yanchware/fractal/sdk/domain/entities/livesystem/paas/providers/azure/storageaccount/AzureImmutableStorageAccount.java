package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureImmutableStorageAccount {

  private Boolean enabled;
  private AzureStorageAccountImmutabilityPolicyProperties immutabilityPolicy;

  public static AzureImmutableStorageAccountBuilder builder() {
    return new AzureImmutableStorageAccountBuilder();
  }

  public static class AzureImmutableStorageAccountBuilder {
    private final AzureImmutableStorageAccount instance;
    private final AzureImmutableStorageAccountBuilder builder;

    public AzureImmutableStorageAccountBuilder() {
      this.instance = new AzureImmutableStorageAccount();
      this.builder = this;
    }

    /**
     * A boolean flag which enables account-level immutability. 
     * All the containers under such an account have object-level immutability enabled by default.
     */
    public AzureImmutableStorageAccountBuilder withEnabled(Boolean enabled) {
      instance.setEnabled(enabled);
      return builder;
    }
    
    /**
     * Specifies the default account-level immutability policy which is inherited and applied to objects 
     * that do not possess an explicit immutability policy at the object level. 
     * The object-level immutability policy has higher precedence than the container-level immutability policy, 
     * which has a higher precedence than the account-level immutability policy.
     */
    public AzureImmutableStorageAccountBuilder withImmutabilityPolicy(AzureStorageAccountImmutabilityPolicyProperties immutabilityPolicy) {
      instance.setImmutabilityPolicy(immutabilityPolicy);
      return builder;
    }

    public AzureImmutableStorageAccount build() {
      return instance;
    }
  }
}
