package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureActiveDirectoryAccountType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Settings properties for Active Directory (AD).
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureActiveDirectoryProperties {
  private AzureActiveDirectoryAccountType accountType;
  private String azureStorageSid;
  private String domainGuid;
  private String domainName;
  private String domainSid;
  private String forestName;
  private String netBiosDomainName;
  private String samAccountName;

  public static AzureActiveDirectoryPropertiesBuilder builder() {
    return new AzureActiveDirectoryPropertiesBuilder();
  }


  public static class AzureActiveDirectoryPropertiesBuilder {
    private final AzureActiveDirectoryProperties properties;
    private final AzureActiveDirectoryPropertiesBuilder builder;

    public AzureActiveDirectoryPropertiesBuilder() {
      this.properties = new AzureActiveDirectoryProperties();
      this.builder = this;
    }

    /**
     * <pre>Specifies the Active Directory account type for Azure Storage.</pre>
     */
    public AzureActiveDirectoryPropertiesBuilder withAccountType(AzureActiveDirectoryAccountType accountType) {
      properties.setAccountType(accountType);
      return builder;
    }

    /**
     * <pre>Specifies the security identifier (SID) for Azure Storage.</pre>
     */
    public AzureActiveDirectoryPropertiesBuilder withAzureStorageSid(String azureStorageSid) {
      properties.setAzureStorageSid(azureStorageSid);
      return builder;
    }

    /**
     * <pre>Specifies the domain GUID.</pre>
     */
    public AzureActiveDirectoryPropertiesBuilder withDomainGuid(String domainGuid) {
      properties.setDomainGuid(domainGuid);
      return builder;
    }

    /**
     * <pre>Specifies the primary domain that the AD DNS server is authoritative for.</pre>
     */
    public AzureActiveDirectoryPropertiesBuilder withDomainName(String domainName) {
      properties.setDomainName(domainName);
      return builder;
    }

    /**
     * <pre>Specifies the security identifier (SID).</pre>
     */
    public AzureActiveDirectoryPropertiesBuilder withDomainSid(String domainSid) {
      properties.setDomainSid(domainSid);
      return builder;
    }

    /**
     * <pre>Specifies the Active Directory forest to get.</pre>
     */
    public AzureActiveDirectoryPropertiesBuilder withForestName(String forestName) {
      properties.setForestName(forestName);
      return builder;
    }

    /**
     * <pre>Specifies the NetBIOS domain name.</pre>
     */
    public AzureActiveDirectoryPropertiesBuilder withNetBiosDomainName(String netBiosDomainName) {
      properties.setNetBiosDomainName(netBiosDomainName);
      return builder;
    }

    /**
     * <pre>Specifies the Active Directory SAMAccountName for Azure Storage.</pre>
     */
    public AzureActiveDirectoryPropertiesBuilder withSamAccountName(String samAccountName) {
      properties.setSamAccountName(samAccountName);
      return builder;
    }

    public AzureActiveDirectoryProperties build() {
      return properties;
    }
  }
}
