package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureDefaultSharePermission;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureDirectoryServiceOptions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Settings for Azure Files identity based authentication.
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureFilesIdentityBasedAuthentication {
  private AzureActiveDirectoryProperties activeDirectoryProperties;
  private AzureDefaultSharePermission defaultSharePermission;
  private AzureDirectoryServiceOptions directoryServiceOptions;

  public static AzureFilesIdentityBasedAuthenticationBuilder builder() {
    return new AzureFilesIdentityBasedAuthenticationBuilder();
  }


  public static class AzureFilesIdentityBasedAuthenticationBuilder {
    private final AzureFilesIdentityBasedAuthentication azureFilesIdentityBasedAuthentication;
    private final AzureFilesIdentityBasedAuthenticationBuilder builder;

    public AzureFilesIdentityBasedAuthenticationBuilder() {
      this.azureFilesIdentityBasedAuthentication = new AzureFilesIdentityBasedAuthentication();
      this.builder = this;
    }

    /**
     * Required if directoryServiceOptions are AD, optional if they are AADKERB.
     */
    public AzureFilesIdentityBasedAuthenticationBuilder withActiveDirectoryProperties(
        AzureActiveDirectoryProperties activeDirectoryProperties) {
      azureFilesIdentityBasedAuthentication.setActiveDirectoryProperties(activeDirectoryProperties);
      return builder;
    }

    /**
     * Default share permission for users using Kerberos authentication if RBAC role is not assigned.
     */
    public AzureFilesIdentityBasedAuthenticationBuilder withDefaultSharePermission(
        AzureDefaultSharePermission defaultSharePermission) {
      azureFilesIdentityBasedAuthentication.setDefaultSharePermission(defaultSharePermission);
      return builder;
    }

    /**
     * Indicates the directory service used. Note that this enum may be extended in the future.
     */
    public AzureFilesIdentityBasedAuthenticationBuilder withDirectoryServiceOptions(
        AzureDirectoryServiceOptions directoryServiceOptions) {
      azureFilesIdentityBasedAuthentication.setDirectoryServiceOptions(directoryServiceOptions);
      return builder;
    }

    public AzureFilesIdentityBasedAuthentication build() {
      return azureFilesIdentityBasedAuthentication;
    }
  }
}
