package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureAction;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureTlsVersion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.*;
import com.yanchware.fractal.sdk.utils.SerializationUtils;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.AzureStorageAccount.builder;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.BaseAzureStorageAccount.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AzureLegacyStorageAccountTest {

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithEmptyId() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder().withId("").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Component Id is illegal");
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithEmptyName() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("")
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(BaseAzureStorageAccount.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullName() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName(null)
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(BaseAzureStorageAccount.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithToShortName() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("na")
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(BaseAzureStorageAccount.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithToLongName() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("a234567890123456789012345")
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(BaseAzureStorageAccount.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullAzureRegion() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("abc")
        .withRegion(null)
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(AZURE_REGION_IS_BLANK);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullAzureResourceGroup() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("abc")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(null)
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(AZURE_RESOURCE_GROUP_IS_BLANK);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithEmptyNameInAzureResourceGroup() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("abc")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("")
            .build())
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(AzureResourceGroup.NAME_IS_NULL_OR_EMPTY);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullNameInAzureResourceGroup() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("abc")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName(null)
            .build())
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(AzureResourceGroup.NAME_IS_NULL_OR_EMPTY);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullRegionInAzureResourceGroup() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("abc")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("resource-group")
            .withRegion(null)
            .build())
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(AzureResourceGroup.REGION_IS_BLANK);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithInvalidNameWithSpacesInAzureResourceGroup() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("abc")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("invalid name with spaces")
            .withRegion(AzureRegion.EUROPE_WEST)
            .build())
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(AzureResourceGroup.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithInvalidNameEndsWithDotInAzureResourceGroup() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("abc")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("invalid.name.")
            .withRegion(AzureRegion.EUROPE_WEST)
            .build())
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(AzureResourceGroup.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithInvalidCharacterAsPartOfNameInAzureResourceGroup() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("abc")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("invalid?name")
            .withRegion(AzureRegion.EUROPE_WEST)
            .build())
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(AzureResourceGroup.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithInvalidCharactersAsPartOfNameInAzureResourceGroup() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("abc")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("!invalid@name#")
            .withRegion(AzureRegion.EUROPE_WEST)
            .build())
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(AzureResourceGroup.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithEmptyKeyAndProperValueInTags() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("a23456789012345678901234")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("validName123")
            .withRegion(AzureRegion.EUROPE_WEST)
            .build())
        .withTag("", "value1")
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(TAG_KEY_IS_BLANK);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithEmptyValueInTags() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("a23456789012345678901234")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("validName123")
            .withRegion(AzureRegion.EUROPE_WEST)
            .build())
        .withTag("key1", "")
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Tag value for key 'key1' cannot be null or empty");
  }

  @Test
  public void nameIsValid_when_MinimumValidLengthNameSet() {
    var storage = AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("abc")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("validName123")
            .withRegion(AzureRegion.EUROPE_WEST)
            .build())
        .build();

    assertTrue(storage.validate().isEmpty());
  }

  @Test
  public void nameIsValid_when_MaximumValidLengthNameSet() {
    var storage = AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("a23456789012345678901234")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("validName123")
            .withRegion(AzureRegion.EUROPE_WEST)
            .build())
        .build();

    assertTrue(storage.validate().isEmpty());
  }

  @Test
  public void tagsAreValid_when_KeyAndValueProvided() {
    var storage = AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("a23456789012345678901234")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("validName123")
            .withRegion(AzureRegion.EUROPE_WEST)
            .build())
        .withTag("key1", "value1")
        .build();
    
    assertTrue(storage.validate().isEmpty());
  }

  @Test
  public void kindIsValid_when_ValidationPasses() throws JsonProcessingException {
    var storage = AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("a23456789012345678901234")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("validName123")
            .withRegion(AzureRegion.EUROPE_WEST)
            .build())
        .withTag("key1", "value1")
        .build();
    assertTrue(storage.validate().isEmpty());

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
    assertThatThrownBy(() -> builder().withId("").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Component Id is illegal");
  }

  @Test
  public void regionValid_when_regionIsSet() {
    var region = AzureRegion.CANADA_EAST;
    var storageAccount = generateBuilder()
        .withRegion(region)
        .build();

    assertEquals(storageAccount.getAzureRegion(), region);
  }

  @Test
  public void backupIsValid_when_backupIsSet() {
    var storageAccountBackup = generateBuilder()
        .withBackup(
            AzureStorageAccountBackup.builder()
                .withEtag("etag")
                .withPolicy("policy")
                .withPolicyLocation("policyLocation")
                .withPolicyName("policyName")
                .withSku(AzureRecoveryServicesSkuName.STANDARD)
                .withPolicyResourceGroupName("policyResourceGroupName")
                .withPolicyType(AzureRecoveryServicesBackupPolicyWorkloadType.AzureStorage)
                .withVaultLocation("vaultLocation")
                .withVaultName("vaultName")
                .withVaultResourceGroupName("vaultResourceGroupName")
                .build()
        )
        .build()
        .getBackup();

    assertEquals("etag", storageAccountBackup.getEtag());
    assertEquals("policy", storageAccountBackup.getPolicy());
    assertEquals("policyLocation", storageAccountBackup.getPolicyLocation());
    assertEquals("policyName", storageAccountBackup.getPolicyName());
    assertEquals(AzureRecoveryServicesSkuName.STANDARD, storageAccountBackup.getSku());
    assertEquals("policyResourceGroupName", storageAccountBackup.getPolicyResourceGroupName());
    assertEquals(AzureRecoveryServicesBackupPolicyWorkloadType.AzureStorage, storageAccountBackup.getPolicyType());
    assertEquals("vaultLocation", storageAccountBackup.getVaultLocation());
    assertEquals("vaultName", storageAccountBackup.getVaultName());
    assertEquals("vaultResourceGroupName", storageAccountBackup.getVaultResourceGroupName());
  }

  @Test
  public void fileServiceIsValid_when_fileServiceIsSet() {
    var storageAccountFileService = generateBuilder()
        .withFileService(
            AzureStorageAccountFileService.builder()
                .withDeleteRetentionDays(10)
                .withDeleteRetentionEnabled(true)
                .build()
        )
        .build()
        .getFileService();

    assertTrue(storageAccountFileService.getDeleteRetentionEnabled());
    assertEquals(10, storageAccountFileService.getDeleteRetentionDays());
  }

  @Test
  public void connectivityIsValid_when_connectivityIsSet() {

    var azureVirtualNetworkRule = AzureVirtualNetworkRule.builder()
        .withVirtualNetworkResourceId("virtualNetworkResourceId")
        .withAction(AzureNetworkAction.ALLOW)
        .withState(AzureState.PROVISIONING)
        .build();

    var azureStorageIpRule = AzureIpRule.builder()
        .withIpAddressOrRange("ipAddressOrRange")
        .withAction(AzureNetworkAction.ALLOW)
        .build();

    var azureResourceAccessRule = AzureResourceAccessRule.builder()
        .withResourceId("resourceId")
        .withTenantId("tenantId")
        .build();

    var storageAccount = generateBuilder()
        .withAllowBlobPublicAccess(true)
        .withAzureFilesIdentityBasedAuthentication(AzureFilesIdentityBasedAuthentication.builder()
            .withActiveDirectoryProperties(AzureActiveDirectoryProperties.builder()
                .withAccountType(AzureActiveDirectoryAccountType.USER)
                .withAzureStorageSid("storageSid")
                .withDomainGuid("domainGuid")
                .withDomainName("domainName")
                .withDomainSid("domainSid")
                .withForestName("forestName")
                .withNetBiosDomainName("netBiosDomainName")
                .withSamAccountName("samAccount")
                .build())
            .withDefaultSharePermission(AzureDefaultSharePermission.NONE)
            .withDirectoryServiceOptions(AzureDirectoryServiceOptions.NONE)
            .build())
        .withDefaultToOAuthAuthentication(true)
        .withMinimumTlsVersion(AzureTlsVersion.TLS1_2)
        .withSupportsHttpsTrafficOnly(true)
        .withIsLocalUserEnabled(true)
        .withIsSftpEnabled(true)
        .withPublicNetworkAccess(AzurePublicNetworkAccess.ENABLED)
        .withIsNfsV3Enabled(true)
        .withKeyPolicy(AzureStorageAccountKeyPolicy.builder()
            .withKeyExpirationPeriodInDays(10)
            .build())
        .withNetworkAcls(AzureNetworkRuleSet.builder()
            .withBypass(AzureBypass.NONE)
            .withDefaultAction(AzureAction.ALLOW)
            .withVirtualNetworkRule(azureVirtualNetworkRule)
            .withResourceAccessRule(azureResourceAccessRule)
            .withIpRule(azureStorageIpRule)
            .build())
        .withRoutingPreference(AzureStorageAccountRoutingPreference.builder()
            .withRoutingChoice(AzureRoutingChoice.INTERNET_ROUTING)
            .withPublishInternetEndpoints(true)
            .withPublishMicrosoftEndpoints(true)
            .build())
        .withSasPolicy(AzureStorageAccountSasPolicy.builder()
            .withExpirationAction(AzureExpirationAction.LOG)
            .withSasExpirationPeriod("10.12:24:30")
            .build())
        .build();
    
    var azureFilesIdentityBasedAuthentication = storageAccount.getAzureFilesIdentityBasedAuthentication();
    var activeDirectoryProperties = storageAccount.getAzureFilesIdentityBasedAuthentication().getActiveDirectoryProperties();

    assertTrue(storageAccount.getIsNfsV3Enabled());
    assertTrue(storageAccount.getAllowBlobPublicAccess());
    assertEquals(AzureActiveDirectoryAccountType.USER, activeDirectoryProperties.getAccountType());
    assertTrue(storageAccount.getDefaultToOAuthAuthentication());
    assertEquals(AzureTlsVersion.TLS1_2, storageAccount.getMinimumTlsVersion());
    assertTrue(storageAccount.getSupportsHttpsTrafficOnly());
    assertTrue(storageAccount.getIsLocalUserEnabled());
    assertTrue(storageAccount.getIsSftpEnabled());
    assertTrue(storageAccount.getRoutingPreference().getPublishInternetEndpoints());
    assertTrue(storageAccount.getRoutingPreference().getPublishMicrosoftEndpoints());
    assertEquals(10, storageAccount.getKeyPolicy().getKeyExpirationPeriodInDays());
    assertEquals(AzureBypass.NONE, storageAccount.getNetworkAcls().getBypass());
    assertEquals(AzureAction.ALLOW, storageAccount.getNetworkAcls().getDefaultAction());
    assertEquals(AzurePublicNetworkAccess.ENABLED, storageAccount.getPublicNetworkAccess());
    assertEquals(AzureRoutingChoice.INTERNET_ROUTING, storageAccount.getRoutingPreference().getRoutingChoice());
    assertEquals(AzureExpirationAction.LOG, storageAccount.getSasPolicy().getExpirationAction());
    assertEquals("10.12:24:30", storageAccount.getSasPolicy().getSasExpirationPeriod());
    assertEquals(List.of(azureStorageIpRule), storageAccount.getNetworkAcls().getIpRules());
    assertEquals("domainGuid", activeDirectoryProperties.getDomainGuid());
    assertEquals("domainName", activeDirectoryProperties.getDomainName());
    assertEquals("domainSid", activeDirectoryProperties.getDomainSid());
    assertEquals("forestName", activeDirectoryProperties.getForestName());
    assertEquals("samAccount", activeDirectoryProperties.getSamAccountName());
    assertEquals("netBiosDomainName", activeDirectoryProperties.getNetBiosDomainName());
    assertEquals("storageSid", activeDirectoryProperties.getAzureStorageSid());
    assertEquals(AzureDefaultSharePermission.NONE, azureFilesIdentityBasedAuthentication.getDefaultSharePermission());
    assertEquals(AzureDirectoryServiceOptions.NONE, azureFilesIdentityBasedAuthentication.getDirectoryServiceOptions());
    assertEquals(List.of(azureVirtualNetworkRule), storageAccount.getNetworkAcls().getVirtualNetworkRules());
    assertEquals(List.of(azureResourceAccessRule), storageAccount.getNetworkAcls().getResourceAccessRules());
  }

  @Test
  public void settingsIsValid_when_settingsIsSet() {
    var storageAccount = generateBuilder()
        .withAllowCrossTenantReplication(true)
        .withAllowSharedKeyAccess(true)
        .withAllowedCopyScope(AzureStorageAccountAllowedCopyScope.AAD)
        .withIsHnsEnabled(true)
        .withLargeFileSharesState(AzureLargeFileSharesState.DISABLED)
        .withImmutableStorageWithVersioning(AzureImmutableStorageAccount.builder()
            .withEnabled(true)
            .withImmutabilityPolicy(AzureStorageAccountImmutabilityPolicyProperties.builder()
                .withAllowProtectedAppendWrites(true)
                .withImmutabilityPeriodSinceCreationInDays(10)
                .withState(AzureAccountImmutabilityPolicyState.LOCKED)
                .build())
            .build())
        .withEncryption(AzureStorageAccountEncryption.builder()
            .withRequireInfrastructureEncryption(true)
            .withKeySource(AzureStorageAccountKeySource.MICROSOFT_STORAGE)
            .withServices(AzureStorageAccountEncryptionServices.builder()
                .withBlob(AzureStorageAccountEncryptionService.builder()
                    .withEnabled(true)
                    .withKeyType(AzureStorageKeyType.ACCOUNT)
                    .build())
                .withFile(AzureStorageAccountEncryptionService.builder()
                    .withEnabled(true)
                    .withKeyType(AzureStorageKeyType.SERVICE)
                    .build())
                .withQueue(AzureStorageAccountEncryptionService.builder()
                    .withEnabled(true)
                    .withKeyType(AzureStorageKeyType.ACCOUNT)
                    .build())
                .withTable(AzureStorageAccountEncryptionService.builder()
                    .withEnabled(true)
                    .withKeyType(AzureStorageKeyType.ACCOUNT)
                    .build())
                .build())
            .withIdentity(AzureStorageAccountEncryptionIdentity.builder()
                .withFederatedIdentityClientId("encryptionFederatedIdentityClientId")
                .withUserAssignedIdentity("encryptionUserAssignedIdentity")
                .build())
            .withKeyVaultProperties(AzureStorageAccountKeyVaultProperties.builder()
                .withKeyName("encryptionKeyName")
                .withKeyVaultUri("encryptionKeyVaultUri")
                .withKeyVersion("encryptionKeyVersion")
                .build())
            .build())
        .build();
    

    assertTrue(storageAccount.getImmutableStorageWithVersioning().getImmutabilityPolicy().getAllowProtectedAppendWrites());
    assertTrue(storageAccount.getImmutableStorageWithVersioning().getEnabled());
    assertTrue(storageAccount.getAllowCrossTenantReplication());
    assertTrue(storageAccount.getAllowSharedKeyAccess());
    assertTrue(storageAccount.getIsHnsEnabled());
    assertTrue(storageAccount.getEncryption().getServices().getBlob().getEnabled());
    assertEquals(AzureStorageKeyType.ACCOUNT, storageAccount.getEncryption().getServices().getBlob().getKeyType());
    assertTrue(storageAccount.getEncryption().getServices().getFile().getEnabled());
    assertEquals(AzureStorageKeyType.SERVICE, storageAccount.getEncryption().getServices().getFile().getKeyType());
    assertTrue(storageAccount.getEncryption().getServices().getQueue().getEnabled());
    assertEquals(AzureStorageKeyType.ACCOUNT, storageAccount.getEncryption().getServices().getQueue().getKeyType());
    assertTrue(storageAccount.getEncryption().getServices().getTable().getEnabled());
    assertEquals(AzureStorageKeyType.ACCOUNT, storageAccount.getEncryption().getServices().getTable().getKeyType());
    assertTrue(storageAccount.getEncryption().getRequireInfrastructureEncryption());
    assertEquals(10, storageAccount.getImmutableStorageWithVersioning().getImmutabilityPolicy()
        .getImmutabilityPeriodSinceCreationInDays());
    assertEquals(AzureStorageAccountAllowedCopyScope.AAD, storageAccount.getAllowedCopyScope());
   
    assertEquals(AzureStorageAccountKeySource.MICROSOFT_STORAGE, storageAccount.getEncryption().getKeySource());
    assertEquals(AzureLargeFileSharesState.DISABLED, storageAccount.getLargeFileSharesState());
    assertEquals("encryptionFederatedIdentityClientId", storageAccount.getEncryption()
        .getIdentity().getFederatedIdentityClientId());
    assertEquals("encryptionKeyName", storageAccount.getEncryption().getKeyVaultProperties().getKeyName());
    assertEquals("encryptionKeyVaultUri", storageAccount.getEncryption().getKeyVaultProperties().getKeyVaultUri());
    assertEquals("encryptionUserAssignedIdentity", storageAccount.getEncryption().getIdentity().getUserAssignedIdentity());
    assertEquals("encryptionKeyVersion", storageAccount.getEncryption().getKeyVaultProperties().getKeyVersion());
  }

  @Test
  public void infrastructureIsValid_when_infrastructureIsSet() {
    var storageAccountSettings = generateBuilder()
        .withSku(AzureStorageAccountSkuName.PREMIUM_LRS)
        .withAccessTier(AzureStorageAccountAccessTier.HOT)
        .withExtendedLocation(AzureStorageAccountExtendedLocation.builder()
            .withName("extendedLocationName")
            .withType(AzureStorageAccountExtendedLocationTypes.EDGE_ZONE)
            .build())
        .withIdentity(AzureStorageAccountIdentity.builder()
            .withIdentityType(AzureIdentityType.NONE)
            .withUserAssignedIdentities(new HashMap<>())
            .build())
        .withCustomDomain(AzureStorageAccountCustomDomain.builder()
            .withName("customDomainName")
            .withUseSubDomainName(true)
            .build())
        .withDnsEndpointType(AzureDnsEndpointType.AZURE_DNS_ZONE)
        .build();
    

    assertTrue(storageAccountSettings.getCustomDomain().getUseSubDomainName());
    assertEquals(new HashMap<>(), storageAccountSettings.getIdentity().getUserAssignedIdentities());
    assertEquals(AzureIdentityType.NONE, storageAccountSettings.getIdentity().getIdentityType());
    assertEquals(AzureStorageAccountAccessTier.HOT, storageAccountSettings.getAccessTier());
    assertEquals(AzureStorageAccountSkuName.PREMIUM_LRS, storageAccountSettings.getSku());
    assertEquals(AzureDnsEndpointType.AZURE_DNS_ZONE, storageAccountSettings.getDnsEndpointType());
    assertEquals("customDomainName", storageAccountSettings.getCustomDomain().getName());
    assertEquals("extendedLocationName", storageAccountSettings.getExtendedLocation().getName());
    assertEquals(AzureStorageAccountExtendedLocationTypes.EDGE_ZONE, storageAccountSettings.getExtendedLocation().getType());
  }

  @Test
  public void booleanWithValueArePresent_when_serialisingTheComponent() throws JsonProcessingException {
    var storageAccount = generateBuilder()
        .withEncryption(AzureStorageAccountEncryption.builder()
            .withServices(
                AzureStorageAccountEncryptionServices.builder()
                    .withBlob(AzureStorageAccountEncryptionService.builder()
                        .withEnabled(true)
                        .build())
                    .build()
            ).build()
        ).build();

    String result = SerializationUtils.serialize(storageAccount.getEncryption()
        .getServices()
        .getBlob());
    assertEquals("{\"enabled\":true}", result);
  }

  @Test
  public void storageAccountBuildShouldFail_when_tooShortName() {
    assertThatThrownBy(() -> builder().withId("a").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Component Id is illegal");
    assertThatThrownBy(() -> builder().withId("ac").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Component Id is illegal");
  }

  @Test
  public void storageAccountBuildShouldFail_when_tooLongName() {
    assertThatThrownBy(() -> builder().withId("aaaaaaaaaaaaaaaaaaaaaaaaaa").build()).
        isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void storageAccountBuildShouldFail_when_HyphensOrUnderscoreInName() {
    assertThatThrownBy(() -> builder().withId("this-is-a-test").build()).
        isInstanceOf(IllegalArgumentException.class);

    assertThatThrownBy(() -> builder().withId("this_is_a_test").build()).
        isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void storageAccountBuildShouldFail_when_UppercaseCharactersInName() {
    assertThatThrownBy(() -> builder().withId("thisIsATest").build()).
        isInstanceOf(IllegalArgumentException.class);

    assertThatThrownBy(() -> builder().withId("Thisisatest").build()).
        isInstanceOf(IllegalArgumentException.class);
  }

  private AzureStorageAccount.AzureStorageAccountBuilder generateBuilder() {
    return builder().withId("storageaccount")
        .withName("storageaccount")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("rg-test")
            .withRegion(AzureRegion.EUROPE_WEST)
            .build());
  }
}