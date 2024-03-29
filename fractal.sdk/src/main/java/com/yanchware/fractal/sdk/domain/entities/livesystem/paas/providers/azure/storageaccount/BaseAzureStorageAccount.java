package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSDataStorage;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureTlsVersion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.*;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_STORAGE_ACCOUNT;

@Getter
@Setter
public abstract class BaseAzureStorageAccount extends PaaSDataStorage implements AzureResourceEntity, LiveSystemComponent {
  private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9]{3,24}$");

  @JsonIgnore
  public static final String NAME_IS_NOT_VALID = "Name must be between 3 and 24 characters in length and use numbers and lower-case letters only";

  @JsonIgnore
  public static final String AZURE_RESOURCE_GROUP_IS_BLANK = "Azure Resource group has not been defined and it is required";

  @JsonIgnore
  public static final String AZURE_REGION_IS_BLANK = "Region has not been defined and it is required";

  @JsonIgnore
  public static final String TAG_KEY_IS_BLANK = "Tag key cannot be null or empty";
  private static final String TAG_VALUE_INVALID_FORMAT = "Tag value for key '%s' cannot be null or empty";

  private String name;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;

  public abstract String getKind();

  private AzureStorageAccountSkuName sku;
  private AzureStorageAccountExtendedLocation extendedLocation;
  private AzureStorageAccountIdentity identity;
  private AzureStorageAccountAccessTier accessTier;
  private Boolean allowBlobPublicAccess;
  private Boolean allowCrossTenantReplication;
  private Boolean allowSharedKeyAccess;
  private AzureStorageAccountAllowedCopyScope allowedCopyScope;
  private AzureFilesIdentityBasedAuthentication azureFilesIdentityBasedAuthentication;
  private AzureStorageAccountCustomDomain customDomain;
  private Boolean defaultToOAuthAuthentication;
  private AzureDnsEndpointType dnsEndpointType;
  private AzureStorageAccountEncryption encryption;
  private AzureImmutableStorageAccount immutableStorageWithVersioning;
  private Boolean isLocalUserEnabled;
  private Boolean isNfsV3Enabled;
  private Boolean isSftpEnabled;
  private AzureStorageAccountKeyPolicy keyPolicy;
  private AzureLargeFileSharesState largeFileSharesState;
  private AzureTlsVersion minimumTlsVersion;
  private AzureNetworkRuleSet networkRuleSet;
  private AzurePublicNetworkAccess publicNetworkAccess;
  private AzureStorageAccountRoutingPreference routingPreference;
  private AzureStorageAccountSasPolicy sasPolicy;
  private Boolean supportsHttpsTrafficOnly;
  private AzureStorageAccountBackup backup;
  


  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static abstract class Builder<T extends BaseAzureStorageAccount, B extends Builder<T, B>> extends Component.Builder<T, B> {

    @Override
    public T build() {
      component.setType(PAAS_STORAGE_ACCOUNT);
      return super.build();
    }

    /**
     * <pre>
     * The name of the storage account within the specified resource group.
     * Storage account names must be between 3 and 24 characters in length and use numbers and lower-case letters only.
     * 
     * Regex pattern: ^[a-z0-9]+$
     * </pre>
     */
    public B withName(String name) {
      component.setName(name);

      return builder;
    }

    /**
     * <pre>
     * Choose the Azure region that's right for you and your customers
     * </pre>
     */
    public B withRegion(AzureRegion azureRegion) {
      component.setAzureRegion(azureRegion);

      return builder;
    }

    /**
     * <pre>
     * A resource group is a collection of resources that share the same lifecycle, permissions, and policies.
     * The resource group can include all the resources for the solution, or only those resources that you want to manage as a group.
     * </pre>
     */
    public B withResourceGroup(AzureResourceGroup resourceGroup) {
      component.setAzureResourceGroup(resourceGroup);

      return builder;
    }

    /**
     * <pre>
     * The SKU of the storage account.
     * </pre>
     */
    public B withSku(AzureStorageAccountSkuName sku) {
      component.setSku(sku);

      return builder;
    }

    /**
     * <pre>
     * Set the extended location of the resource. 
     * If not set, the storage account will be created in Azure main region.
     * </pre>
     */
    public B withExtendedLocation(AzureStorageAccountExtendedLocation extendedLocation) {
      component.setExtendedLocation(extendedLocation);

      return builder;
    }

    /**
     * <pre>
     * The identity of the resource.
     * </pre>
     */
    public B withIdentity(AzureStorageAccountIdentity identity) {
      component.setIdentity(identity);

      return builder;
    }

    /**
     * <pre>
     * Required for Blob Storage accounts. 
     * The access tier is used for billing.
     * </pre>
     */
    public B withAccessTier(AzureStorageAccountAccessTier accessTier) {
      component.setAccessTier(accessTier);

      return builder;
    }

    /**
     * <pre>
     * Allow or disallow public access to all blobs or containers in the storage account.
     * </pre>
     */
    public B withAllowBlobPublicAccess(Boolean allowBlobPublicAccess) {
      component.setAllowBlobPublicAccess(allowBlobPublicAccess);

      return builder;
    }

    /**
     * <pre>
     * Allow or disallow cross AAD tenant object replication. 
     * Set this property to true for new or existing accounts only if object replication policies will involve 
     * storage accounts in different AAD tenants.
     * </pre>
     */
    public B withAllowCrossTenantReplication(Boolean allowCrossTenantReplication) {
      component.setAllowCrossTenantReplication(allowCrossTenantReplication);

      return builder;
    }

    /**
     * <pre>
     * Indicates whether the storage account permits requests to be authorized with the account access key via Shared Key.
     * If false, then all requests, including shared access signatures, 
     * must be authorized with Azure Active Directory (Azure AD).
     * </pre>
     */
    public B withAllowSharedKeyAccess(Boolean allowSharedKeyAccess) {
      component.setAllowSharedKeyAccess(allowSharedKeyAccess);

      return builder;
    }

    /**
     * <pre>
     * Restrict copy to and from Storage Accounts within an AAD tenant or with Private Links to the same VNet.
     * </pre>
     */
    public B withAllowedCopyScope(AzureStorageAccountAllowedCopyScope allowedCopyScope) {
      component.setAllowedCopyScope(allowedCopyScope);

      return builder;
    }

    /**
     * <pre>
     * Provides the identity based authentication settings for Azure Files.
     * </pre>
     */
    public B withAzureFilesIdentityBasedAuthentication(AzureFilesIdentityBasedAuthentication azureFilesIdentityBasedAuthentication) {
      component.setAzureFilesIdentityBasedAuthentication(azureFilesIdentityBasedAuthentication);

      return builder;
    }

    /**
     * <pre>
     * User domain assigned to the storage account. 
     * Name is the CNAME source. 
     * Only one custom domain is supported per storage account at this time. 
     * To clear the existing custom domain, use an empty string for the custom domain name property.
     * </pre>
     */
    public B withCustomDomain(AzureStorageAccountCustomDomain customDomain) {
      component.setCustomDomain(customDomain);

      return builder;
    }

    /**
     * <pre>
     * Enables OAuth as the default authentication, if sets to true.
     * </pre>
     */
    public B withDefaultToOAuthAuthentication(Boolean defaultToOAuthAuthentication) {
      component.setDefaultToOAuthAuthentication(defaultToOAuthAuthentication);

      return builder;
    }

    /**
     * <pre>
     * Allows you to specify the type of endpoint. 
     * Set this to AzureDNSZone to create a large number of accounts in a single subscription, 
     * which creates accounts in an Azure DNS Zone and the endpoint URL will have an alphanumeric DNS Zone identifier.
     * </pre>
     */
    public B withDnsEndpointType(AzureDnsEndpointType dnsEndpointType) {
      component.setDnsEndpointType(dnsEndpointType);

      return builder;
    }

    /**
     * <pre>
     * Encryption settings to be used for server-side encryption for the storage account.
     * </pre>
     */
    public B withEncryption(AzureStorageAccountEncryption encryption) {
      component.setEncryption(encryption);

      return builder;
    }

    /**
     * <pre>
     * This property enables and defines account-level immutability. 
     * Enabling the feature auto-enables Blob Versioning.
     * </pre>
     */
    public B withImmutableStorageWithVersioning(AzureImmutableStorageAccount immutableStorageWithVersioning) {
      component.setImmutableStorageWithVersioning(immutableStorageWithVersioning);

      return builder;
    }

    /**
     * <pre>
     * Enables local users feature, if set to true
     * </pre>
     */
    public B withIsLocalUserEnabled(Boolean isLocalUserEnabled) {
      component.setIsLocalUserEnabled(isLocalUserEnabled);

      return builder;
    }


    /**
     * <pre>
     * Enables NFS 3.0 protocol support, if set to true.
     * </pre>
     */
    public B withIsNfsV3Enabled(Boolean isNfsV3Enabled) {
      component.setIsNfsV3Enabled(isNfsV3Enabled);

      return builder;
    }

    /**
     * <pre>
     * Enables Secure File Transfer Protocol, if set to true
     * </pre>
     */
    public B withIsSftpEnabled(Boolean isSftpEnabled) {
      component.setIsSftpEnabled(isSftpEnabled);

      return builder;
    }

    /**
     * <pre>
     * KeyPolicy assigned to the storage account.
     * </pre>
     */
    public B withKeyPolicy(AzureStorageAccountKeyPolicy keyPolicy) {
      component.setKeyPolicy(keyPolicy);

      return builder;
    }

    /**
     * <pre>
     * Allow large file shares if sets to Enabled.
     * It cannot be disabled once it is enabled.
     * </pre>
     */
    public B withLargeFileSharesState(AzureLargeFileSharesState largeFileSharesState) {
      component.setLargeFileSharesState(largeFileSharesState);

      return builder;
    }

    /**
     * <pre>
     * Set the minimum TLS version to be permitted on requests to storage.
     * </pre>
     */
    public B withMinimumTlsVersion(AzureTlsVersion minimumTlsVersion) {
      component.setMinimumTlsVersion(minimumTlsVersion);

      return builder;
    }

    /**
     * <pre>
     * Network rule set
     * </pre>
     */
    public B withNetworkRuleSet(AzureNetworkRuleSet networkRuleSet) {
      component.setNetworkRuleSet(networkRuleSet);

      return builder;
    }

    /**
     * <pre>
     * Allow or disallow public network access to Storage Account.
     * Value is optional but if passed in, must be 'Enabled' or 'Disabled'.
     * </pre>
     */
    public B withPublicNetworkAccess(AzurePublicNetworkAccess publicNetworkAccess) {
      component.setPublicNetworkAccess(publicNetworkAccess);

      return builder;
    }

    /**
     * <pre>
     * Maintains information about the network routing choice opted by the user for data transfer.
     * </pre>
     */
    public B withRoutingPreference(AzureStorageAccountRoutingPreference routingPreference) {
      component.setRoutingPreference(routingPreference);

      return builder;
    }

    /**
     * <pre>
     * SasPolicy assigned to the storage account.
     * </pre>
     */
    public B withSasPolicy(AzureStorageAccountSasPolicy sasPolicy) {
      component.setSasPolicy(sasPolicy);

      return builder;
    }

    /**
     * <pre>
     * Allows https traffic only to storage service if sets to true.
     * The default value is true.
     * </pre>
     */
    public B withSupportsHttpsTrafficOnly(Boolean supportsHttpsTrafficOnly) {
      component.setSupportsHttpsTrafficOnly(supportsHttpsTrafficOnly);

      return builder;
    }

    /**
     * Tags are name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public B withTags(Map<String, String> tags) {
      component.setTags(tags);

      return builder;
    }

    /**
     * Tag is name/value pair that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public B withTag(String key, String value) {
      if (component.getTags() == null) {
        withTags(new HashMap<>());
      }

      component.getTags().put(key, value);

      return builder;
    }

    public B withBackup(AzureStorageAccountBackup backup) {
      component.setBackup(backup);
      return builder;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    // Validate name
    if (StringUtils.isBlank(name) || !NAME_PATTERN.matcher(name).matches()) {
      errors.add(NAME_IS_NOT_VALID);
    }

    // Validate Azure Region
    if (azureRegion == null) {
      errors.add(AZURE_REGION_IS_BLANK);
    }

    // Validate Azure Resource Group
    if (azureResourceGroup == null) {
      errors.add(AZURE_RESOURCE_GROUP_IS_BLANK);
    }

    // Validate tags
    if (tags != null) {
      for (Map.Entry<String, String> tag : tags.entrySet()) {
        if (tag.getKey() == null || tag.getKey().isEmpty()) {
          errors.add(TAG_KEY_IS_BLANK);
        }
        if (tag.getValue() == null || tag.getValue().isEmpty()) {
          errors.add(String.format(TAG_VALUE_INVALID_FORMAT, tag.getKey()));
        }
      }
    }

    return errors;
  }
}