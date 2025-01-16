package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cdn;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureIdentityType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Managed service identity (system assigned and/ or user assigned identities).
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureManagedServiceIdentity {
  private AzureIdentityType type;
  private Collection<String> userAssignedIdentities;

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
     * Type of managed service identity (where both SystemAssigned and UserAssigned types are allowed).
     * </pre>
     */
    public AzureManagedServiceIdentityBuilder withType(AzureIdentityType type) {
      instance.setType(type);
      return builder;
    }

    /**
     * The set of user assigned identity id's associated with the resource. It should be ARM
     * resource ids in the form:
     * '/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft
     * .ManagedIdentity/userAssignedIdentities/{identityName}.'
     */
    public AzureManagedServiceIdentityBuilder withUserAssignedIdentities(Collection<String> userAssignedIdentities) {
      instance.setUserAssignedIdentities(userAssignedIdentities);
      return builder;
    }

    /**
     * User assigned identity associated with the resource. It should be ARM resource id in the form:
     * '/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft
     * .ManagedIdentity/userAssignedIdentities/{identityName}.'
     */
    public AzureManagedServiceIdentityBuilder withUserAssignedIdentity(String userAssignedIdentityId) {
      if (instance.getUserAssignedIdentities() == null) {
        withUserAssignedIdentities(new ArrayList<>() {
        });
      }

      instance.getUserAssignedIdentities().add(userAssignedIdentityId);
      return builder;
    }


    public AzureManagedServiceIdentity build() {
      return instance;
    }
  }
}
