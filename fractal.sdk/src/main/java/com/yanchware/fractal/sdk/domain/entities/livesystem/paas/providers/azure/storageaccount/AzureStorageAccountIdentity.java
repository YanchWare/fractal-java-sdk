package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureIdentityType;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureUserAssignedIdentity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureStorageAccountIdentity {
  private String principalId;
  private String tenantId;
  private AzureIdentityType identityType;
  private HashMap<String, AzureUserAssignedIdentity> userAssignedIdentities;

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

    public AzureStorageAccountIdentityBuilder withPrincipalId(String principalId) {
      identity.setPrincipalId(principalId);
      return builder;
    }

    public AzureStorageAccountIdentityBuilder withTenantId(String tenantId) {
      identity.setTenantId(tenantId);
      return builder;
    }

    public AzureStorageAccountIdentityBuilder withIdentityType(AzureIdentityType identityType) {
      identity.setIdentityType(identityType);
      return builder;
    }

    public AzureStorageAccountIdentityBuilder withUserAssignedIdentities(HashMap<String, AzureUserAssignedIdentity> userAssignedIdentities) {
      identity.setUserAssignedIdentities(userAssignedIdentities);
      return builder;
    }

    public AzureStorageAccountIdentity build() {
      return identity;
    }
  }
  
}
