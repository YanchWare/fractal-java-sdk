package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureIdentityType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * Identity for the resource.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountIdentity {
  private AzureIdentityType identityType;
  private Map<String, AzureUserAssignedIdentity> userAssignedIdentities;

  public static AzureStorageAccountIdentityBuilder builder() {
    return new AzureStorageAccountIdentityBuilder();
  }


  public static class AzureStorageAccountIdentityBuilder {
    private final AzureStorageAccountIdentity identity;
    private final AzureStorageAccountIdentityBuilder builder;

    public AzureStorageAccountIdentityBuilder() {
      this.identity = new AzureStorageAccountIdentity();
      this.builder = this;
    }

    /**
     * <pre>
     * The identity type.
     * </pre>
     */
    public AzureStorageAccountIdentityBuilder withIdentityType(AzureIdentityType identityType) {
      identity.setIdentityType(identityType);
      return builder;
    }

    /**
     * <pre>
     * Sets a list of key value pairs that describe the set of User Assigned identities that will be used with this storage account. 
     * The key is the ARM resource identifier of the identity. 
     * Only 1 User Assigned identity is permitted here.
     * </pre>
     */
    public AzureStorageAccountIdentityBuilder withUserAssignedIdentities(Map<String, AzureUserAssignedIdentity> userAssignedIdentities) {
      identity.setUserAssignedIdentities(userAssignedIdentities);
      return builder;
    }

    /**
     * <pre>
     * Key value pair that describe User Assigned identity that will be used with this storage account. 
     * The key is the ARM resource identifier of the identity. 
     * </pre>
     */
    public AzureStorageAccountIdentityBuilder withUserAssignedIdentity(String key, AzureUserAssignedIdentity userAssignedIdentity) {
      if (identity.getUserAssignedIdentities() == null) {
        withUserAssignedIdentities(new HashMap<>());
      }

      identity.getUserAssignedIdentities().put(key, userAssignedIdentity);

      return builder;
    }

    public AzureStorageAccountIdentity build() {
      return identity;
    }
  }
  
}
