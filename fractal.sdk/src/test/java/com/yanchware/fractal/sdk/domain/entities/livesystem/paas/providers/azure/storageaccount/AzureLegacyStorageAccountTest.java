package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.TestWithFixture;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureAction;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureTlsVersion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.*;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.BaseAzureStorageAccount.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureLegacyStorageAccountTest extends TestWithFixture {

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithEmptyId() {
    assertThatThrownBy(() -> generateBuilder().withId("").build())
        .isInstanceOf(IllegalArgumentException.class
        ).hasMessageContaining("Component Id is illegal");
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithEmptyName() {
    assertThatThrownBy(() -> generateBuilder()
        .withName("")
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(BaseAzureStorageAccount.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullName() {
    assertThatThrownBy(() -> generateBuilder().withName(null).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(BaseAzureStorageAccount.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithToShortName() {
    assertThatThrownBy(() -> generateBuilder().withName(aLowerCaseAlphanumericString(2)).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(BaseAzureStorageAccount.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithToLongName() {
    assertThatThrownBy(() -> generateBuilder().withName(aLowerCaseAlphanumericString(25)).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(BaseAzureStorageAccount.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullAzureRegion() {
    assertThatThrownBy(() -> generateBuilder().withRegion(null).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(AZURE_REGION_IS_BLANK);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullAzureResourceGroup() {
    assertThatThrownBy(() -> generateBuilder().withResourceGroup(null).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(AZURE_RESOURCE_GROUP_IS_BLANK);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithEmptyNameInAzureResourceGroup() {
    assertThatThrownBy(() -> generateBuilder()
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("")
            .build())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(AzureResourceGroup.NAME_IS_NULL_OR_EMPTY);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullNameInAzureResourceGroup() {
    assertThatThrownBy(() -> generateBuilder()
        .withResourceGroup(AzureResourceGroup.builder()
            .withName(null)
            .build())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(AzureResourceGroup.NAME_IS_NULL_OR_EMPTY);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullRegionInAzureResourceGroup() {
    assertThatThrownBy(() -> generateBuilder()
        .withResourceGroup(AzureResourceGroup.builder()
            .withName(aLowerCaseAlphanumericString(10, true, "-"))
            .withRegion(null)
            .build())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(AzureResourceGroup.REGION_IS_BLANK);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithInvalidNameWithSpacesInAzureResourceGroup() {
    assertThatThrownBy(() -> generateBuilder().withResourceGroup(AzureResourceGroup.builder()
            .withName("invalid name with spaces")
            .withRegion(a(AzureRegion.class))
            .build())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(AzureResourceGroup.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithInvalidNameEndsWithDotInAzureResourceGroup() {
    assertThatThrownBy(() -> generateBuilder()
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("invalid.name.")
            .withRegion(a(AzureRegion.class))
            .build())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(AzureResourceGroup.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithInvalidCharacterAsPartOfNameInAzureResourceGroup() {
    assertThatThrownBy(() -> generateBuilder()
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("invalid?name")
            .withRegion(a(AzureRegion.class))
            .build())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(AzureResourceGroup.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithInvalidCharactersAsPartOfNameInAzureResourceGroup() {
    assertThatThrownBy(() -> generateBuilder()
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("!invalid@name#")
            .withRegion(a(AzureRegion.class))
            .build())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(AzureResourceGroup.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithEmptyKeyAndProperValueInTags() {
    assertThatThrownBy(() -> generateBuilder()
        .withTag("", aLowerCaseAlphanumericString(5))
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(TAG_KEY_IS_BLANK);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithEmptyValueInTags() {
    var tagKey = aLowerCaseAlphanumericString(10);
    assertThatThrownBy(() -> generateBuilder().withTag(tagKey, "").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Tag value for key '" + tagKey + "' cannot be null or empty");
  }

  @Test
  public void nameIsValid_when_MinimumValidLengthNameSet() {
    var storage = generateBuilder()
        .withName(aLowerCaseAlphanumericString(3))
        .build();

    assertThat(storage.validate().isEmpty()).isTrue();
  }

  @Test
  public void nameIsValid_when_MaximumValidLengthNameSet() {
    var storage = generateBuilder()
        .withName(aLowerCaseAlphanumericString(24))
        .build();

    assertThat(storage.validate().isEmpty()).isTrue();
  }

  @Test
  public void tagsAreValid_when_KeyAndValueProvided() {
    var storage = generateBuilder()
        .withTag(aLowerCaseAlphanumericString(10), aLowerCaseAlphanumericString(10))
        .build();

    assertThat(storage.validate().isEmpty()).isTrue();
  }

  @Test
  public void kindIsValid_when_ValidationPasses() throws JsonProcessingException {
    var storage = generateBuilder()
        .withTag(aLowerCaseAlphanumericString(10), aLowerCaseAlphanumericString(10))
        .build();

    assertThat(storage.validate().isEmpty()).isTrue();

    var json = TestUtils.getJsonRepresentation(storage);
    assertThat(json).isNotBlank();

    var mapper = new ObjectMapper();
    var rootNode = mapper.readTree(json);
    var propertyNode = rootNode.path("kind");

    assertThat(propertyNode).isNotNull();

    assertThat(propertyNode.asText()).isEqualTo("Storage");
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullId() {
    assertThatThrownBy(() -> generateBuilder().withId("").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Component Id is illegal");
  }

  @Test
  public void regionValid_when_regionIsSet() {
    var region = AzureRegion.CANADA_EAST;
    var storageAccount = generateBuilder().withRegion(region).build();

    assertThat(storageAccount.getAzureRegion()).isEqualTo(region);
  }

  @Test
  public void backupIsValid_when_backupIsSet() {
    var etag = aLowerCaseAlphanumericString(10);
    var policy = aLowerCaseAlphanumericString(10);
    var policyLocation = aLowerCaseAlphanumericString(10);
    var policyName = aLowerCaseAlphanumericString(10);
    var policyResourceGroupName = aLowerCaseAlphanumericString(10);
    var vaultLocation = aLowerCaseAlphanumericString(10);
    var vaultName = aLowerCaseAlphanumericString(10);
    var vaultResourceGroupName = aLowerCaseAlphanumericString(10);

    var storageAccountBackup = generateBuilder()
        .withBackup(AzureStorageAccountBackup.builder()
            .withEtag(etag)
            .withPolicy(policy)
            .withPolicyLocation(policyLocation)
            .withPolicyName(policyName)
            .withSku(AzureRecoveryServicesSkuName.STANDARD)
            .withPolicyResourceGroupName(policyResourceGroupName)
            .withPolicyType(AzureRecoveryServicesBackupPolicyWorkloadType.AzureStorage)
            .withVaultLocation(vaultLocation).withVaultName(vaultName)
            .withVaultResourceGroupName(vaultResourceGroupName)
            .build())
        .build()
        .getBackup();

    assertThat(storageAccountBackup.getEtag())
        .isEqualTo(etag);

    assertThat(storageAccountBackup.getPolicy())
        .isEqualTo(policy);

    assertThat(storageAccountBackup.getPolicyLocation())
        .isEqualTo(policyLocation);

    assertThat(storageAccountBackup.getPolicyName())
        .isEqualTo(policyName);

    assertThat(storageAccountBackup.getPolicyResourceGroupName())
        .isEqualTo(policyResourceGroupName);

    assertThat(storageAccountBackup.getVaultLocation())
        .isEqualTo(vaultLocation);

    assertThat(storageAccountBackup.getVaultName())
        .isEqualTo(vaultName);

    assertThat(storageAccountBackup.getVaultResourceGroupName())
        .isEqualTo(vaultResourceGroupName);

    assertThat(storageAccountBackup.getSku())
        .isEqualTo(AzureRecoveryServicesSkuName.STANDARD);

    assertThat(storageAccountBackup.getPolicyType())
        .isEqualTo(AzureRecoveryServicesBackupPolicyWorkloadType.AzureStorage);
  }

  @Test
  public void fileServiceIsValid_when_fileServiceIsSet() {
    var deleteRetentionEnabled = a(Boolean.class);
    var deleteRetentionDays = aPositiveInteger();

    var storageAccountFileService = generateBuilder()
        .withFileService(AzureStorageAccountFileService.builder()
            .withDeleteRetentionEnabled(deleteRetentionEnabled)
            .withDeleteRetentionDays(deleteRetentionDays)
            .build())
        .build()
        .getFileService();

    assertThat(storageAccountFileService.getDeleteRetentionEnabled())
        .isEqualTo(deleteRetentionEnabled);

    assertThat(storageAccountFileService.getDeleteRetentionDays())
        .isEqualTo(deleteRetentionDays);
  }

  @Test
  public void storageAccountIsValid_when_EverythingIsSet() {

    var storageAccountSku = a(AzureStorageAccountSkuName.class);
    var extendedLocationName = aLowerCaseAlphanumericString(15);
    var extendedLocationType = a(AzureStorageAccountExtendedLocationTypes.class);
    var identityType = a(AzureIdentityType.class);
    var userAssignedIdentityKey = a(String.class);
    var userAssignedIdentityClientId = a(String.class);
    var userAssignedIdentityPrincipalId = a(String.class);
    var accessTier = a(AzureStorageAccountAccessTier.class);
    var allowBlobPublicAccess = a(Boolean.class);
    var allowCrossTenantReplication = a(Boolean.class);
    var allowSharedKeyAccess = a(Boolean.class);
    var allowedCopyScope = a(AzureStorageAccountAllowedCopyScope.class);
    var activeDirectoryAccountType = a(AzureActiveDirectoryAccountType.class);
    var activeDirectoryAzureStorageSid = a(String.class);
    var activeDirectoryDomainGuid = a(String.class);
    var activeDirectoryDomainName = a(String.class);
    var activeDirectoryDomainSid = a(String.class);
    var activeDirectoryForestName = a(String.class);
    var activeDirectoryNetBiosDomainName = a(String.class);
    var activeDirectorySamAccountName = a(String.class);
    var activeDirectoryDefaultSharePermission = a(AzureDefaultSharePermission.class);
    var activeDirectoryDirectoryServiceOptions = a(AzureDirectoryServiceOptions.class);
    var customDomainName = a(String.class);
    var customDomainUseSubDomainName = a(Boolean.class);
    var defaultToOAuthAuthentication = a(Boolean.class);
    var dnsEndpointType = a(AzureDnsEndpointType.class);
    var encryptionIdentityFederatedIdentityClientId = a(String.class);
    var encryptionIdentityUserAssignedIdentity = a(String.class);
    var encryptionKeySource = a(AzureStorageAccountKeySource.class);
    var encryptionKeyVaultPropertiesKeyName = a(String.class);
    var encryptionKeyVaultPropertiesKeyVaultUri = a(String.class);
    var encryptionKeyVaultPropertiesKeyVersion = a(String.class);
    var encryptionRequireInfrastructureEncryption = a(Boolean.class);
    var blobEncryptionServiceEnabled = a(Boolean.class);
    var blobEncryptionServiceKeyType = a(AzureStorageKeyType.class);
    var fileEncryptionServiceEnabled = a(Boolean.class);
    var fileEncryptionServiceKeyType = a(AzureStorageKeyType.class);
    var queueEncryptionServiceEnabled = a(Boolean.class);
    var queueEncryptionServiceKeyType = a(AzureStorageKeyType.class);
    var tableEncryptionServiceEnabled = a(Boolean.class);
    var tableEncryptionServiceKeyType = a(AzureStorageKeyType.class);
    var immutableStorageEnabled = a(Boolean.class);
    var immutabilityPolicyAllowProtectedAppendWrites = a(Boolean.class);
    var immutabilityPolicyImmutabilityPeriodSinceCreationInDays = aPositiveInteger();
    var immutabilityPolicyState = a(AzureAccountImmutabilityPolicyState.class);
    var isHnsEnabled = a(Boolean.class);
    var isLocalUserEnabled = a(Boolean.class);
    var isNfsV3Enabled = a(Boolean.class);
    var isSftpEnabled = a(Boolean.class);
    var keyPolicyExpirationPeriodInDays = aPositiveInteger();
    var largeFileSharesState = a(AzureLargeFileSharesState.class);
    var minimumTlsVersion = a(AzureTlsVersion.class);
    var networkRuleSetBypass = a(AzureBypass.class);
    var networkRuleSetDefaultAction = a(AzureAction.class);
    var networkRuleSetIpRuleAction = a(AzureNetworkAction.class);
    var networkRuleSetIpRuleIpAddressOrRange = a(String.class);
    var networkRuleSetResourceAccessRuleResourceId = a(String.class);
    var networkRuleSetResourceAccessRuleTenantId = a(String.class);
    var networkRuleSetVirtualNetworkRuleAction = a(AzureNetworkAction.class);
    var networkRuleSetVirtualNetworkRuleResourceId = a(String.class);
    var networkRuleSetVirtualNetworkRuleState = a(AzureState.class);
    var publicNetworkAccess = a(AzurePublicNetworkAccess.class);
    var routingPreferencePublishInternetEndpoints = a(Boolean.class);
    var routingPreferencePublishMicrosoftEndpoints = a(Boolean.class);
    var routingPreferenceRoutingChoice = a(AzureRoutingChoice.class);
    var sasPolicyExpirationAction = a(AzureExpirationAction.class);
    var sasPolicyExpirationPeriod = "30.15:50:21";
    var supportsHttpsTrafficOnly = a(Boolean.class);


    var extendedLocation = AzureStorageAccountExtendedLocation.builder()
        .withName(extendedLocationName)
        .withType(extendedLocationType)
        .build();

    var identity = AzureStorageAccountIdentity.builder()
        .withIdentityType(identityType)
        .withUserAssignedIdentity(userAssignedIdentityKey, AzureUserAssignedIdentity.builder()
            .withClientId(userAssignedIdentityClientId)
            .withPrincipalId(userAssignedIdentityPrincipalId)
            .build())
        .build();

    var activeDirectoryProperties = AzureActiveDirectoryProperties.builder()
        .withAccountType(activeDirectoryAccountType)
        .withAzureStorageSid(activeDirectoryAzureStorageSid)
        .withDomainGuid(activeDirectoryDomainGuid)
        .withDomainName(activeDirectoryDomainName)
        .withDomainSid(activeDirectoryDomainSid)
        .withForestName(activeDirectoryForestName)
        .withNetBiosDomainName(activeDirectoryNetBiosDomainName)
        .withSamAccountName(activeDirectorySamAccountName)
        .build();

    var azureFilesIdentityBasedAuthentication = AzureFilesIdentityBasedAuthentication.builder()
        .withActiveDirectoryProperties(activeDirectoryProperties)
        .withDefaultSharePermission(activeDirectoryDefaultSharePermission)
        .withDirectoryServiceOptions(activeDirectoryDirectoryServiceOptions)
        .build();

    var customDomain = AzureStorageAccountCustomDomain.builder()
        .withName(customDomainName)
        .withUseSubDomainName(customDomainUseSubDomainName)
        .build();

    var encryptionIdentity = AzureStorageAccountEncryptionIdentity.builder()
        .withFederatedIdentityClientId(encryptionIdentityFederatedIdentityClientId)
        .withUserAssignedIdentity(encryptionIdentityUserAssignedIdentity)
        .build();

    var encryptionKeyVaultProperties = AzureStorageAccountKeyVaultProperties.builder()
        .withKeyName(encryptionKeyVaultPropertiesKeyName)
        .withKeyVaultUri(encryptionKeyVaultPropertiesKeyVaultUri)
        .withKeyVersion(encryptionKeyVaultPropertiesKeyVersion)
        .build();

    var blobEncryptionService = AzureStorageAccountEncryptionService.builder()
        .withEnabled(blobEncryptionServiceEnabled)
        .withKeyType(blobEncryptionServiceKeyType)
        .build();

    var fileEncryptionService = AzureStorageAccountEncryptionService.builder()
        .withEnabled(fileEncryptionServiceEnabled)
        .withKeyType(fileEncryptionServiceKeyType)
        .build();

    var queueEncryptionService = AzureStorageAccountEncryptionService.builder()
        .withEnabled(queueEncryptionServiceEnabled)
        .withKeyType(queueEncryptionServiceKeyType)
        .build();

    var tableEncryptionService = AzureStorageAccountEncryptionService.builder()
        .withEnabled(tableEncryptionServiceEnabled)
        .withKeyType(tableEncryptionServiceKeyType)
        .build();

    var encryptionServices = AzureStorageAccountEncryptionServices.builder()
        .withBlob(blobEncryptionService)
        .withFile(fileEncryptionService)
        .withQueue(queueEncryptionService)
        .withTable(tableEncryptionService)
        .build();

    var encryption = AzureStorageAccountEncryption.builder()
        .withIdentity(encryptionIdentity)
        .withKeySource(encryptionKeySource)
        .withKeyVaultProperties(encryptionKeyVaultProperties)
        .withRequireInfrastructureEncryption(encryptionRequireInfrastructureEncryption)
        .withServices(encryptionServices)
        .build();

    var immutabilityPolicy = AzureStorageAccountImmutabilityPolicyProperties.builder()
        .withAllowProtectedAppendWrites(immutabilityPolicyAllowProtectedAppendWrites)
        .withImmutabilityPeriodSinceCreationInDays(immutabilityPolicyImmutabilityPeriodSinceCreationInDays)
        .withState(immutabilityPolicyState)
        .build();

    var immutableStorage = AzureImmutableStorageAccount.builder()
        .withEnabled(immutableStorageEnabled)
        .withImmutabilityPolicy(immutabilityPolicy)
        .build();

    var keyPolicy = AzureStorageAccountKeyPolicy.builder()
        .withKeyExpirationPeriodInDays(keyPolicyExpirationPeriodInDays)
        .build();

    var networkRuleSetIpRule = AzureIpRule.builder()
        .withAction(networkRuleSetIpRuleAction)
        .withIpAddressOrRange(networkRuleSetIpRuleIpAddressOrRange)
        .build();

    var networkRuleSetResourceAccessRule = AzureResourceAccessRule.builder()
        .withResourceId(networkRuleSetResourceAccessRuleResourceId)
        .withTenantId(networkRuleSetResourceAccessRuleTenantId)
        .build();

    var networkRuleSetVirtualNetworkRule = AzureVirtualNetworkRule.builder()
        .withAction(networkRuleSetVirtualNetworkRuleAction)
        .withVirtualNetworkResourceId(networkRuleSetVirtualNetworkRuleResourceId)
        .withState(networkRuleSetVirtualNetworkRuleState)
        .build();

    var networkRuleSet = AzureNetworkRuleSet.builder()
        .withBypass(networkRuleSetBypass)
        .withDefaultAction(networkRuleSetDefaultAction)
        .withIpRule(networkRuleSetIpRule)
        .withResourceAccessRule(networkRuleSetResourceAccessRule)
        .withVirtualNetworkRule(networkRuleSetVirtualNetworkRule)
        .build();

    var routingPreference = AzureStorageAccountRoutingPreference.builder()
        .withPublishInternetEndpoints(routingPreferencePublishInternetEndpoints)
        .withPublishMicrosoftEndpoints(routingPreferencePublishMicrosoftEndpoints)
        .withRoutingChoice(routingPreferenceRoutingChoice)
        .build();

    var sasPolicy = AzureStorageAccountSasPolicy.builder()
        .withExpirationAction(sasPolicyExpirationAction)
        .withSasExpirationPeriod(sasPolicyExpirationPeriod)
        .build();

    var storageAccount = generateBuilder()
        .withSku(storageAccountSku)
        .withExtendedLocation(extendedLocation)
        .withIdentity(identity)
        .withAccessTier(accessTier)
        .withAllowBlobPublicAccess(allowBlobPublicAccess)
        .withAllowCrossTenantReplication(allowCrossTenantReplication)
        .withAllowSharedKeyAccess(allowSharedKeyAccess)
        .withAllowedCopyScope(allowedCopyScope)
        .withAzureFilesIdentityBasedAuthentication(azureFilesIdentityBasedAuthentication)
        .withCustomDomain(customDomain)
        .withDefaultToOAuthAuthentication(defaultToOAuthAuthentication)
        .withDnsEndpointType(dnsEndpointType)
        .withEncryption(encryption)
        .withImmutableStorageWithVersioning(immutableStorage)
        .withIsHnsEnabled(isHnsEnabled)
        .withIsLocalUserEnabled(isLocalUserEnabled)
        .withIsNfsV3Enabled(isNfsV3Enabled)
        .withIsSftpEnabled(isSftpEnabled)
        .withKeyPolicy(keyPolicy)
        .withLargeFileSharesState(largeFileSharesState)
        .withMinimumTlsVersion(minimumTlsVersion)
        .withNetworkRuleSet(networkRuleSet)
        .withPublicNetworkAccess(publicNetworkAccess)
        .withRoutingPreference(routingPreference)
        .withSasPolicy(sasPolicy)
        .withSupportsHttpsTrafficOnly(supportsHttpsTrafficOnly)
        .build();

    var storageAccountJson = TestUtils.getJsonRepresentation(storageAccount);
    assertThat(storageAccountJson).isNotBlank();

    var storageAccountExtendedLocation = storageAccount.getExtendedLocation();
    var storageAccountIdentity = storageAccount.getIdentity();
    var storageAccountUserAssignedIdentity = storageAccountIdentity.getUserAssignedIdentities().get(userAssignedIdentityKey);
    var storageAccountBasedAuthentication = storageAccount.getAzureFilesIdentityBasedAuthentication();
    var storageAccountActiveDirectoryProperties = storageAccountBasedAuthentication.getActiveDirectoryProperties();
    var storageAccountCustomDomain = storageAccount.getCustomDomain();
    var storageAccountEncryption = storageAccount.getEncryption();
    var storageAccountEncryptionIdentity = storageAccountEncryption.getIdentity();
    var storageAccountEncryptionKeyVaultProperties = storageAccountEncryption.getKeyVaultProperties();
    var storageAccountEncryptionServices = storageAccountEncryption.getServices();
    var storageAccountBlobEncryptionService = storageAccountEncryptionServices.getBlob();
    var storageAccountFileEncryptionService = storageAccountEncryptionServices.getFile();
    var storageAccountQueueEncryptionService = storageAccountEncryptionServices.getQueue();
    var storageAccountTableEncryptionService = storageAccountEncryptionServices.getTable();
    var storageAccountImmutableStorage = storageAccount.getImmutableStorageWithVersioning();
    var storageAccountImmutableStoragePolicy = storageAccountImmutableStorage.getImmutabilityPolicy();
    var storageAccountKeyPolicy = storageAccount.getKeyPolicy();
    var storageAccountNetworkRuleSet = storageAccount.getNetworkRuleSet();
    var storageAccountNetworkRuleSetIpRule = storageAccountNetworkRuleSet.getIpRules().getFirst();
    var storageAccountNetworkRuleSetResourceAccessRule = storageAccountNetworkRuleSet.getResourceAccessRules().getFirst();
    var storageAccountNetworkRuleSetVirtualNetworkRule = storageAccountNetworkRuleSet.getVirtualNetworkRules().getFirst();
    var storageAccountRoutingPreference = storageAccount.getRoutingPreference();
    var storageAccountSasPolicy = storageAccount.getSasPolicy();

    assertThat(storageAccount.getSku())
        .isEqualTo(storageAccountSku);

    assertThat(storageAccount.getExtendedLocation())
        .isEqualTo(storageAccountExtendedLocation);

    assertThat(storageAccountExtendedLocation.getName())
        .isEqualTo(extendedLocationName);

    assertThat(storageAccountExtendedLocation.getType())
        .isEqualTo(extendedLocationType);

    assertThat(storageAccount.getIdentity())
        .isEqualTo(storageAccountIdentity);

    assertThat(storageAccountIdentity.getIdentityType())
        .isEqualTo(identityType);

    assertThat(storageAccountIdentity.getUserAssignedIdentities())
        .hasSize(1);

    assertThat(storageAccountIdentity.getUserAssignedIdentities())
        .containsKey(userAssignedIdentityKey);

    assertThat(storageAccountUserAssignedIdentity.getClientId())
        .isEqualTo(userAssignedIdentityClientId);

    assertThat(storageAccountUserAssignedIdentity.getPrincipalId())
        .isEqualTo(userAssignedIdentityPrincipalId);

    assertThat(storageAccount.getAccessTier())
        .isEqualTo(accessTier);

    assertThat(storageAccount.getAllowBlobPublicAccess())
        .isEqualTo(allowBlobPublicAccess);

    assertThat(storageAccount.getAllowCrossTenantReplication())
        .isEqualTo(allowCrossTenantReplication);

    assertThat(storageAccount.getAllowSharedKeyAccess())
        .isEqualTo(allowSharedKeyAccess);

    assertThat(storageAccount.getAllowedCopyScope())
        .isEqualTo(allowedCopyScope);

    assertThat(storageAccount.getAzureFilesIdentityBasedAuthentication())
        .isEqualTo(azureFilesIdentityBasedAuthentication);

    assertThat(storageAccountBasedAuthentication.getActiveDirectoryProperties())
        .isEqualTo(storageAccountActiveDirectoryProperties);

    assertThat(storageAccountActiveDirectoryProperties.getAccountType())
        .isEqualTo(activeDirectoryAccountType);

    assertThat(storageAccountActiveDirectoryProperties.getAzureStorageSid())
        .isEqualTo(activeDirectoryAzureStorageSid);

    assertThat(storageAccountActiveDirectoryProperties.getDomainGuid())
        .isEqualTo(activeDirectoryDomainGuid);

    assertThat(storageAccountActiveDirectoryProperties.getDomainName())
        .isEqualTo(activeDirectoryDomainName);

    assertThat(storageAccountActiveDirectoryProperties.getDomainSid())
        .isEqualTo(activeDirectoryDomainSid);

    assertThat(storageAccountActiveDirectoryProperties.getForestName())
        .isEqualTo(activeDirectoryForestName);

    assertThat(storageAccountActiveDirectoryProperties.getNetBiosDomainName())
        .isEqualTo(activeDirectoryNetBiosDomainName);

    assertThat(storageAccountActiveDirectoryProperties.getSamAccountName())
        .isEqualTo(activeDirectorySamAccountName);

    assertThat(storageAccountBasedAuthentication.getDefaultSharePermission())
        .isEqualTo(activeDirectoryDefaultSharePermission);

    assertThat(storageAccountBasedAuthentication.getDirectoryServiceOptions())
        .isEqualTo(activeDirectoryDirectoryServiceOptions);

    assertThat(storageAccount.getCustomDomain())
        .isEqualTo(storageAccountCustomDomain);

    assertThat(storageAccountCustomDomain.getName())
        .isEqualTo(customDomainName);

    assertThat(storageAccountCustomDomain.getUseSubDomainName())
        .isEqualTo(customDomainUseSubDomainName);

    assertThat(storageAccount.getDefaultToOAuthAuthentication())
        .isEqualTo(defaultToOAuthAuthentication);

    assertThat(storageAccount.getDnsEndpointType())
        .isEqualTo(dnsEndpointType);

    assertThat(storageAccount.getEncryption())
        .isEqualTo(storageAccountEncryption);

    assertThat(storageAccountEncryption.getIdentity())
        .isEqualTo(encryptionIdentity);

    assertThat(storageAccountEncryption.getKeySource())
        .isEqualTo(encryptionKeySource);

    assertThat(storageAccountEncryption.getKeyVaultProperties())
        .isEqualTo(encryptionKeyVaultProperties);

    assertThat(storageAccountEncryption.getRequireInfrastructureEncryption())
        .isEqualTo(encryptionRequireInfrastructureEncryption);

    assertThat(storageAccountEncryption.getServices())
        .isEqualTo(encryptionServices);

    assertThat(storageAccountEncryptionIdentity.getFederatedIdentityClientId())
        .isEqualTo(encryptionIdentityFederatedIdentityClientId);

    assertThat(storageAccountEncryptionIdentity.getUserAssignedIdentity())
        .isEqualTo(encryptionIdentityUserAssignedIdentity);

    assertThat(storageAccountEncryptionKeyVaultProperties.getKeyName())
        .isEqualTo(encryptionKeyVaultPropertiesKeyName);

    assertThat(storageAccountEncryptionKeyVaultProperties.getKeyVaultUri())
        .isEqualTo(encryptionKeyVaultPropertiesKeyVaultUri);

    assertThat(storageAccountEncryptionKeyVaultProperties.getKeyVersion())
        .isEqualTo(encryptionKeyVaultPropertiesKeyVersion);

    assertThat(storageAccountEncryptionServices.getBlob())
        .isEqualTo(blobEncryptionService);

    assertThat(storageAccountEncryptionServices.getFile())
        .isEqualTo(fileEncryptionService);

    assertThat(storageAccountEncryptionServices.getQueue())
        .isEqualTo(queueEncryptionService);

    assertThat(storageAccountEncryptionServices.getTable())
        .isEqualTo(tableEncryptionService);

    assertThat(storageAccountBlobEncryptionService.getEnabled())
        .isEqualTo(blobEncryptionServiceEnabled);

    assertThat(storageAccountBlobEncryptionService.getKeyType())
        .isEqualTo(blobEncryptionServiceKeyType);

    assertThat(storageAccountFileEncryptionService.getEnabled())
        .isEqualTo(fileEncryptionServiceEnabled);

    assertThat(storageAccountFileEncryptionService.getKeyType())
        .isEqualTo(fileEncryptionServiceKeyType);

    assertThat(storageAccountQueueEncryptionService.getEnabled())
        .isEqualTo(queueEncryptionServiceEnabled);

    assertThat(storageAccountQueueEncryptionService.getKeyType())
        .isEqualTo(queueEncryptionServiceKeyType);

    assertThat(storageAccountTableEncryptionService.getEnabled())
        .isEqualTo(tableEncryptionServiceEnabled);

    assertThat(storageAccountTableEncryptionService.getKeyType())
        .isEqualTo(tableEncryptionServiceKeyType);

    assertThat(storageAccount.getImmutableStorageWithVersioning())
        .isEqualTo(storageAccountImmutableStorage);

    assertThat(storageAccountImmutableStorage.getEnabled())
        .isEqualTo(immutableStorageEnabled);

    assertThat(storageAccountImmutableStorage.getImmutabilityPolicy())
        .isEqualTo(storageAccountImmutableStoragePolicy);

    assertThat(storageAccountImmutableStoragePolicy.getAllowProtectedAppendWrites())
        .isEqualTo(immutabilityPolicyAllowProtectedAppendWrites);

    assertThat(storageAccountImmutableStoragePolicy.getImmutabilityPeriodSinceCreationInDays())
        .isEqualTo(immutabilityPolicyImmutabilityPeriodSinceCreationInDays);

    assertThat(storageAccount.getIsHnsEnabled())
        .isEqualTo(isHnsEnabled);

    assertThat(storageAccount.getIsLocalUserEnabled())
        .isEqualTo(isLocalUserEnabled);

    assertThat(storageAccount.getIsNfsV3Enabled())
        .isEqualTo(isNfsV3Enabled);

    assertThat(storageAccount.getIsSftpEnabled())
        .isEqualTo(isSftpEnabled);

    assertThat(storageAccount.getKeyPolicy())
        .isEqualTo(keyPolicy);

    assertThat(storageAccountKeyPolicy.getKeyExpirationPeriodInDays())
        .isEqualTo(keyPolicyExpirationPeriodInDays);

    assertThat(storageAccount.getLargeFileSharesState())
        .isEqualTo(largeFileSharesState);

    assertThat(storageAccount.getMinimumTlsVersion())
        .isEqualTo(minimumTlsVersion);

    assertThat(storageAccount.getNetworkRuleSet())
        .isEqualTo(networkRuleSet);

    assertThat(storageAccountNetworkRuleSet.getBypass())
        .isEqualTo(networkRuleSetBypass);

    assertThat(storageAccountNetworkRuleSet.getDefaultAction())
        .isEqualTo(networkRuleSetDefaultAction);

    assertThat(storageAccountNetworkRuleSet.getIpRules())
        .hasSize(1);

    assertThat(storageAccountNetworkRuleSetIpRule.getAction())
        .isEqualTo(networkRuleSetIpRuleAction);

    assertThat(storageAccountNetworkRuleSetIpRule.getIpAddressOrRange())
        .isEqualTo(networkRuleSetIpRuleIpAddressOrRange);

    assertThat(storageAccountNetworkRuleSet.getResourceAccessRules())
        .hasSize(1);

    assertThat(storageAccountNetworkRuleSetResourceAccessRule.getResourceId())
        .isEqualTo(networkRuleSetResourceAccessRuleResourceId);

    assertThat(storageAccountNetworkRuleSetResourceAccessRule.getTenantId())
        .isEqualTo(networkRuleSetResourceAccessRuleTenantId);

    assertThat(storageAccountNetworkRuleSet.getVirtualNetworkRules())
        .hasSize(1);

    assertThat(storageAccountNetworkRuleSetVirtualNetworkRule.getAction())
        .isEqualTo(networkRuleSetVirtualNetworkRuleAction);

    assertThat(storageAccountNetworkRuleSetVirtualNetworkRule.getVirtualNetworkResourceId())
        .isEqualTo(networkRuleSetVirtualNetworkRuleResourceId);

    assertThat(storageAccountNetworkRuleSetVirtualNetworkRule.getState())
        .isEqualTo(networkRuleSetVirtualNetworkRuleState);

    assertThat(storageAccount.getPublicNetworkAccess())
        .isEqualTo(publicNetworkAccess);

    assertThat(storageAccount.getRoutingPreference())
        .isEqualTo(routingPreference);

    assertThat(storageAccountRoutingPreference.getPublishInternetEndpoints())
        .isEqualTo(routingPreferencePublishInternetEndpoints);

    assertThat(storageAccountRoutingPreference.getPublishMicrosoftEndpoints())
        .isEqualTo(routingPreferencePublishMicrosoftEndpoints);

    assertThat(storageAccountRoutingPreference.getRoutingChoice())
        .isEqualTo(routingPreferenceRoutingChoice);

    assertThat(storageAccount.getSasPolicy())
        .isEqualTo(sasPolicy);

    assertThat(storageAccountSasPolicy.getExpirationAction())
        .isEqualTo(sasPolicyExpirationAction);

    assertThat(storageAccountSasPolicy.getSasExpirationPeriod())
        .isEqualTo(sasPolicyExpirationPeriod);

    assertThat(storageAccount.getSupportsHttpsTrafficOnly())
        .isEqualTo(supportsHttpsTrafficOnly);

  }


  @Test
  public void booleanWithValueArePresent_when_serialisingTheComponent() {
    var storageAccount = generateBuilder()
        .withEncryption(AzureStorageAccountEncryption.builder()
            .withServices(AzureStorageAccountEncryptionServices.builder()
                .withBlob(AzureStorageAccountEncryptionService.builder()
                    .withEnabled(true)
                    .withKeyType(AzureStorageKeyType.SERVICE)
                    .build())
                .build())
            .build())
        .build();

    String result = TestUtils.getJsonRepresentation(storageAccount.getEncryption()
        .getServices()
        .getBlob());
    
    var expectedResult = """
        {
          "enabled" : true,
          "keyType" : "Service"
        }""";
    
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  public void storageAccountBuildShouldFail_when_tooShortName() {
    assertThatThrownBy(() -> generateBuilder()
        .withName(aLowerCaseAlphanumericString(2, true, "-"))
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(NAME_IS_NOT_VALID);
  }

  @Test
  public void storageAccountBuildShouldFail_when_tooLongName() {
    assertThatThrownBy(() -> generateBuilder()
        .withName(aLowerCaseAlphanumericString(25, true, "-"))
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(NAME_IS_NOT_VALID);
  }

  @Test
  public void storageAccountBuildShouldFail_when_HyphensOrUnderscoreInName() {
    assertThatThrownBy(() -> generateBuilder().withName("this-is-a-test").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(NAME_IS_NOT_VALID);

    assertThatThrownBy(() -> generateBuilder().withName("this_is_a_test").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(NAME_IS_NOT_VALID);
  }

  @Test
  public void storageAccountBuildShouldFail_when_UppercaseCharactersInName() {
    assertThatThrownBy(() -> generateBuilder().withName("thisIsATest").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(NAME_IS_NOT_VALID);

    assertThatThrownBy(() -> generateBuilder().withName("Thisisatest").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(NAME_IS_NOT_VALID);
  }

  private AzureLegacyStorageAccount.AzureLegacyStorageAccountBuilder generateBuilder() {
    return new AzureLegacyStorageAccount.AzureLegacyStorageAccountBuilder()
        .withId(aComponentId())
        .withName(aLowerCaseAlphanumericString(24))
        .withDisplayName(aAlphanumericString(50))
        .withRegion(a(AzureRegion.class))
        .withResourceGroup(AzureResourceGroup.builder()
            .withName(aLowerCaseAlphanumericString(24))
            .withRegion(a(AzureRegion.class))
            .build());
  }
}