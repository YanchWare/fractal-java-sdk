package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountEncryptionIdentity {
  private String federatedIdentityClientId;
  private String userAssignedIdentity;

  public static AzureStorageAccountEncryptionIdentityBuilder builder() {

    return new AzureStorageAccountEncryptionIdentityBuilder();
  }


  public static class AzureStorageAccountEncryptionIdentityBuilder {
    private final AzureStorageAccountEncryptionIdentity identity;
    private final AzureStorageAccountEncryptionIdentityBuilder builder;

    public AzureStorageAccountEncryptionIdentityBuilder() {
      this.identity = new AzureStorageAccountEncryptionIdentity();
      this.builder = this;
    }

    /**
     * <pre>
     * ClientId of the multi-tenant application to be used in conjunction with the user-assigned identity 
     * for cross-tenant customer-managed-keys server-side encryption on the storage account.
     * </pre>
     */
    public AzureStorageAccountEncryptionIdentityBuilder withFederatedIdentityClientId(String federatedIdentityClientId) {
      identity.setFederatedIdentityClientId(federatedIdentityClientId);
      return builder;
    }

    /**
     * <pre>
     * Resource identifier of the UserAssigned identity to be associated with server-side encryption on the storage account.
     * </pre>
     */
    public AzureStorageAccountEncryptionIdentityBuilder withUserAssignedIdentity(String userAssignedIdentity) {
      identity.setUserAssignedIdentity(userAssignedIdentity);
      return builder;
    }


    public AzureStorageAccountEncryptionIdentity build() {
      return identity;
    }
  }
}
