package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cdn;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureIdentityType;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.AzureUserAssignedIdentity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Managed service identity (system assigned and/ or user assigned identities).
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureManagedServiceIdentity {
  private UUID principalId;
  private UUID tenantId;
  private AzureIdentityType type;
  private Map<String, AzureUserAssignedIdentity> userAssignedIdentities;

  public static AzureManagedServiceIdentityBuilder builder() {
    return new AzureManagedServiceIdentityBuilder();
  }

  public static class AzureManagedServiceIdentityBuilder {
    private final AzureManagedServiceIdentity instance;
    private final AzureManagedServiceIdentityBuilder builder;

    public AzureManagedServiceIdentityBuilder() {
      this.instance = new AzureManagedServiceIdentity();
      this.builder = this;
    }

    /**
     * <pre>
     * The service principal ID of the system assigned identity. This property will only be provided for a system 
     * assigned identity.
     * </pre>
     */
    public AzureManagedServiceIdentityBuilder withPrincipalId(UUID principalId) {
      instance.setPrincipalId(principalId);
      return builder;
    }

    /**
     * <pre>
     * The tenant ID of the system assigned identity. This property will only be provided for a system assigned 
     * identity.
     * </pre>
     */
    public AzureManagedServiceIdentityBuilder withTenantId(UUID tenantId) {
      instance.setTenantId(tenantId);
      return builder;
    }

    /**
     * <pre>
     * Type of managed service identity (where both SystemAssigned and UserAssigned types are allowed).
     * </pre>
     */
    public AzureManagedServiceIdentityBuilder withType(AzureIdentityType type) {
      instance.setType(type);
      return builder;
    }

    /**
     * The set of user assigned identities associated with the resource. The dictionary keys should be ARM 
     * resource ids in the form:
     * '/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.ManagedIdentity/userAssignedIdentities/{identityName}.'
     * The dictionary values can be empty objects ({}) in requests.
     */
    public AzureManagedServiceIdentityBuilder withUserAssignedIdentities(Map<String, AzureUserAssignedIdentity> userAssignedIdentities) {
      instance.setUserAssignedIdentities(userAssignedIdentities);
      return builder;
    }

    /**
     * User assigned identity associated with the resource. The dictionary key should be ARM resource id in the form:
     * '/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.ManagedIdentity/userAssignedIdentities/{identityName}.'
     */
    public AzureManagedServiceIdentityBuilder withUserAssignedIdentity(String key, AzureUserAssignedIdentity userAssignedIdentity) {
      if (instance.getUserAssignedIdentities() == null) {
        withUserAssignedIdentities(new HashMap<>());
      }

      instance.getUserAssignedIdentities().put(key, userAssignedIdentity);
      return builder;
    }


    public AzureManagedServiceIdentity build() {
      return instance;
    }
  }
}
