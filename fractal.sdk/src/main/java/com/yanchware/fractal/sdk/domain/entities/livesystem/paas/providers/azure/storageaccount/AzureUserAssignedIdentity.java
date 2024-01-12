package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * UserAssignedIdentity for the resource.  
 * </pre>
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureUserAssignedIdentity {

  private String clientId;
  private String principalId;

  public static AzureUserAssignedIdentityBuilder builder() {
    return new AzureUserAssignedIdentityBuilder();
  }

  public static class AzureUserAssignedIdentityBuilder {
    private final AzureUserAssignedIdentity instance;
    private final AzureUserAssignedIdentityBuilder builder;

    public AzureUserAssignedIdentityBuilder() {
      this.instance = new AzureUserAssignedIdentity();
      this.builder = this;
    }
    
    /**
     * <pre>
     * The client ID of the identity.
     * </pre>
     */
    public AzureUserAssignedIdentityBuilder withClientId(String clientId) {
      instance.setClientId(clientId);
      return builder;
    }

    /**
     * <pre>
     * The principal ID of the identity.
     * </pre>
     */
    public AzureUserAssignedIdentityBuilder withPrincipalId(String principalId) {
      instance.setPrincipalId(principalId);
      return builder;
    }

    public AzureUserAssignedIdentity build() {
      return instance;
    }
  }
}
