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

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.AzureLegacyStorageAccount.builder;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.BaseAzureFileStorage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

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
        hasMessageContaining(BaseAzureFileStorage.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullName() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName(null)
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(BaseAzureFileStorage.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithToShortName() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("na")
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(BaseAzureFileStorage.NAME_IS_NOT_VALID);
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithToLongName() {
    assertThatThrownBy(() -> AzureLegacyStorageAccount.builder()
        .withId("legacy-storage-account")
        .withName("a234567890123456789012345")
        .build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining(BaseAzureFileStorage.NAME_IS_NOT_VALID);
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
                .withSku(AzureStorageKind.AzureRecoveryServicesSkuName.STANDARD)
                .withPolicyResourceGroupName("policyResourceGroupName")
                .withPolicyType(AzureStorageKind.AzureRecoveryServicesBackupPolicyWorkloadType.AzureStorage)
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
    assertEquals(AzureStorageKind.AzureRecoveryServicesSkuName.STANDARD, storageAccountBackup.getSku());
    assertEquals("policyResourceGroupName", storageAccountBackup.getPolicyResourceGroupName());
    assertEquals(AzureStorageKind.AzureRecoveryServicesBackupPolicyWorkloadType.AzureStorage, storageAccountBackup.getPolicyType());
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

    var azureVirtualNetworkRule = new AzureVirtualNetworkRule(
        "virtualNetworkResourceId",
        AzureStorageAction.ALLOW,
        AzureStorageProvisionState.PROVISIONING
    );

    var azureStorageIpRule = new AzureStorageIpRule(
        "ipAddressOrRange", AzureStorageAction.ALLOW
    );

    var azureResourceAccessRule = new AzureResourceAccessRule(
        "tenantId", "resourceId"
    );

    var storageAccountConnectivity = generateBuilder()
        .withConnectivity(
            AzureStorageAccountConnectivity.builder()
                .withAllowBlobPublicAccess(true)
                .withAzureIdentityBasedAuthAzureDirectoryAccountType(AzureActiveDirectoryAccountType.USER)
                .withAzureIdentityBasedAuthAzureDirectoryDomainGuid("domainGuid")
                .withAzureIdentityBasedAuthAzureDirectoryDomainName("domainName")
                .withAzureIdentityBasedAuthAzureDirectoryDomainSid("domainSid")
                .withAzureIdentityBasedAuthAzureDirectoryForestName("forestName")
                .withAzureIdentityBasedAuthAzureDirectorySamAccount("samAccount")
                .withAzureIdentityBasedDefaultSharePermission(AzureDefaultSharePermission.NONE)
                .withAzureIdentityBasedAuthAzureDirectoryNetBiosDomainName("netBiosDomainName")
                .withAzureIdentityBasedAuthAzureDirectoryStorageSid("storageSid")
                .withAzureIdentityBasedDirectoryServiceOptions(AzureDirectoryServiceOptions.NONE)
                .withDefaultToOAuthAuthentication(true)
                .withMinimumTlsVersion(AzureTlsVersion.TLS1_2)
                .withEnableHttpsTrafficOnly(true)
                .withIsLocalUserEnabled(true)
                .withIsSftpEnabled(true)
                .withKeyPolicyExpirationInDays(10)
                .withNetworkRuleSetBypass(AzureStorageBypass.NONE)
                .withNetworkRuleSetDefaultAction(AzureAction.ALLOW)
                .withPublicNetworkAccess(AzureStoragePublicNetworkAccess.ENABLED)
                .withPublishInternetEndpoints(true)
                .withPublishMicrosoftEndpoints(true)
                .withRoutingChoice("routingChoice")
                .withSasPolicyExpirationAction("sasPolicyExpirationAction")
                .withSasPolicyExpirationPeriod("sasPolicyExpirationPeriod")
                .withEnableNfsV3(true)
                .withNetworkRuleSetBypass(AzureStorageBypass.NONE)
                .withIsLocalUserEnabled(true)
                .withNetworkRuleSetVirtualNetworkRule(azureVirtualNetworkRule)
                .withNetworkRuleSetResourceAccessRule(azureResourceAccessRule)
                .withNetworkRuleSetIpRule(azureStorageIpRule)
                .build()
        )
        .build()
        .getConnectivity();

    assertTrue(storageAccountConnectivity.getEnableNfsV3());
    assertTrue(storageAccountConnectivity.getAllowBlobPublicAccess());
    assertEquals(AzureActiveDirectoryAccountType.USER, storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectoryAccountType());
    assertTrue(storageAccountConnectivity.getDefaultToOAuthAuthentication());
    assertEquals(AzureTlsVersion.TLS1_2, storageAccountConnectivity.getMinimumTlsVersion());
    assertTrue(storageAccountConnectivity.getAllowBlobPublicAccess());
    assertTrue(storageAccountConnectivity.getEnableHttpsTrafficOnly());
    assertTrue(storageAccountConnectivity.getIsLocalUserEnabled());
    assertTrue(storageAccountConnectivity.getIsSftpEnabled());
    assertTrue(storageAccountConnectivity.getPublishInternetEndpoints());
    assertTrue(storageAccountConnectivity.getPublishMicrosoftEndpoints());
    assertTrue(storageAccountConnectivity.getEnableNfsV3());
    assertEquals(10, storageAccountConnectivity.getKeyPolicyExpirationInDays());
    assertEquals(AzureStorageBypass.NONE, storageAccountConnectivity.getNetworkRuleSetBypass());
    assertEquals(AzureAction.ALLOW, storageAccountConnectivity.getNetworkRuleSetDefaultAction());
    assertEquals(AzureStoragePublicNetworkAccess.ENABLED, storageAccountConnectivity.getPublicNetworkAccess());
    assertEquals("routingChoice", storageAccountConnectivity.getRoutingChoice());
    assertEquals("sasPolicyExpirationAction", storageAccountConnectivity.getSasPolicyExpirationAction());
    assertEquals("sasPolicyExpirationPeriod", storageAccountConnectivity.getSasPolicyExpirationPeriod());
    assertEquals(List.of(azureStorageIpRule), storageAccountConnectivity.getNetworkRuleSetIpRules());
    assertEquals("domainGuid", storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectoryDomainGuid());
    assertEquals("domainName", storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectoryDomainName());
    assertEquals("domainSid", storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectoryDomainSid());
    assertEquals("forestName", storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectoryForestName());
    assertEquals("samAccount", storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectorySamAccount());
    assertEquals("netBiosDomainName", storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectoryNetBiosDomainName());
    assertEquals("storageSid", storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectoryStorageSid());
    assertEquals(AzureDefaultSharePermission.NONE, storageAccountConnectivity.getAzureIdentityBasedDefaultSharePermission());
    assertEquals(AzureDirectoryServiceOptions.NONE, storageAccountConnectivity.getAzureIdentityBasedDirectoryServiceOptions());
    assertEquals(List.of(azureVirtualNetworkRule), storageAccountConnectivity.getNetworkRuleSetVirtualNetworkRules());
    assertEquals(List.of(azureResourceAccessRule), storageAccountConnectivity.getNetworkRuleSetResourceAccessRules());
  }

  @Test
  public void settingsIsValid_when_settingsIsSet() {
    var storageAccountSettings = generateBuilder()
        .withSettings(
            AzureStorageAccountSettings
                .builder()
                .withAccountImmutabilityAllowProtectedAppendWrites(true)
                .withAccountImmutabilityEnabled(true)
                .withAllowCrossTenantReplication(true)
                .withAllowSharedKeyAccess(true)
                .withBlobEncryptionEnabled(true)
                .withFileEncryptionEnabled(true)
                .withIsHnsEnabled(true)
                .withQueueEncryptionEnabled(true)
                .withTableEncryptionEnabled(true)
                .withRequireInfrastructureEncryption(true)
                .withAccountImmutabilityPeriodSinceCreationInDays(10)
                .withAllowedCopyScope(AzureStorageAllowedCopyScope.AAD)
                .withAccountImmutabilityPolicyState(AzureAccountImmutabilityPolicyState.LOCKED)
                .withBlobEncryptionType(AzureStorageKeyType.ACCOUNT)
                .withEncryptionKeySource(AzureStorageKeySource.MICROSOFT_STORAGE)
                .withLargeFileSharesState(AzureLargeFileSharesState.DISABLED)
                .withFileEncryptionType(AzureStorageKeySource.MICROSOFT_STORAGE)
                .withKind(AzureStorageKind.FILE_STORAGE)
                .withQueueEncryptionType(AzureStorageKeyType.ACCOUNT)
                .withTableEncryptionType(AzureStorageKeyType.ACCOUNT)
                .withEncryptionFederatedIdentityClientId("encryptionFederatedIdentityClientId")
                .withEncryptionKeyName("encryptionKeyName")
                .withEncryptionKeyVaultUri("encryptionKeyVaultUri")
                .withEncryptionUserAssignedIdentity("encryptionUserAssignedIdentity")
                .withEncryptionKeyVersion("encryptionKeyVersion")
                .build()
        )
        .build()
        .getSettings();

    assertTrue(storageAccountSettings.getAccountImmutabilityAllowProtectedAppendWrites());
    assertTrue(storageAccountSettings.getAccountImmutabilityEnabled());
    assertTrue(storageAccountSettings.getAllowCrossTenantReplication());
    assertTrue(storageAccountSettings.getAllowSharedKeyAccess());
    assertTrue(storageAccountSettings.getBlobEncryptionEnabled());
    assertTrue(storageAccountSettings.getFileEncryptionEnabled());
    assertTrue(storageAccountSettings.getIsHnsEnabled());
    assertTrue(storageAccountSettings.getQueueEncryptionEnabled());
    assertTrue(storageAccountSettings.getTableEncryptionEnabled());
    assertTrue(storageAccountSettings.getRequireInfrastructureEncryption());
    assertEquals(10, storageAccountSettings.getAccountImmutabilityPeriodSinceCreationInDays());
    assertEquals(AzureStorageAllowedCopyScope.AAD, storageAccountSettings.getAllowedCopyScope());
    assertEquals(AzureStorageKeyType.ACCOUNT, storageAccountSettings.getBlobEncryptionType());
    assertEquals(AzureStorageKeySource.MICROSOFT_STORAGE, storageAccountSettings.getEncryptionKeySource());
    assertEquals(AzureLargeFileSharesState.DISABLED, storageAccountSettings.getLargeFileSharesState());
    assertEquals(AzureStorageKeySource.MICROSOFT_STORAGE, storageAccountSettings.getFileEncryptionType());
    assertEquals(AzureStorageKind.FILE_STORAGE, storageAccountSettings.getKind());
    assertEquals(AzureStorageKeyType.ACCOUNT, storageAccountSettings.getQueueEncryptionType());
    assertEquals(AzureStorageKeyType.ACCOUNT, storageAccountSettings.getTableEncryptionType());
    assertEquals("encryptionFederatedIdentityClientId", storageAccountSettings.getEncryptionFederatedIdentityClientId());
    assertEquals("encryptionKeyName", storageAccountSettings.getEncryptionKeyName());
    assertEquals("encryptionKeyVaultUri", storageAccountSettings.getEncryptionKeyVaultUri());
    assertEquals("encryptionUserAssignedIdentity", storageAccountSettings.getEncryptionUserAssignedIdentity());
    assertEquals("encryptionKeyVersion", storageAccountSettings.getEncryptionKeyVersion());
  }

  @Test
  public void infrastructureIsValid_when_infrastructureIsSet() {
    var storageAccountSettings = generateBuilder()
        .withInfrastructure(
            AzureStorageAccountInfrastructure
                .builder()
                .withCustomDomainUseSubName(true)
                .withUserAssignedIdentities(new HashMap<>())
                .withAccessTier(AzureStorageAccessTier.HOT)
                .withSku(AzureStorageSkuName.PREMIUM_LRS)
                .withDnsEndpointType(AzureDnsEndpointType.AZURE_DNS_ZONE)
                .withIdentityType(AzureIdentityType.NONE)
                .withCustomDomainName("customDomainName")
                .withExtendedLocationName("extendedLocationName")
                .withExtendedLocationType("extendedLocationType")
                .build()
        ).build()
        .getInfrastructure();

    assertTrue(storageAccountSettings.getCustomDomainUseSubName());
    assertEquals(new HashMap<>(), storageAccountSettings.getUserAssignedIdentities());
    assertEquals(AzureStorageAccessTier.HOT, storageAccountSettings.getAccessTier());
    assertEquals(AzureStorageSkuName.PREMIUM_LRS, storageAccountSettings.getSku());
    assertEquals(AzureDnsEndpointType.AZURE_DNS_ZONE, storageAccountSettings.getDnsEndpointType());
    assertEquals(AzureIdentityType.NONE, storageAccountSettings.getIdentityType());
    assertEquals("customDomainName", storageAccountSettings.getCustomDomainName());
    assertEquals("extendedLocationName", storageAccountSettings.getExtendedLocationName());
    assertEquals("extendedLocationType", storageAccountSettings.getExtendedLocationType());
  }


  @Test
  public void booleanWithoutValueAreNotPresent_when_serialisingTheComponent() throws JsonProcessingException {
    var storageAccountSettings = generateBuilder()
        .withSettings(
            AzureStorageAccountSettings.builder()
                .withKind(AzureStorageKind.FILE_STORAGE)
                .build()
        ).build();

    String result = SerializationUtils.serialize(storageAccountSettings.getSettings());
    assertEquals("{\"kind\":\"FileStorage\"}", result);
  }

  @Test
  public void booleanWithValueArePresent_when_serialisingTheComponent() throws JsonProcessingException {
    var storageAccountSettings = generateBuilder()
        .withSettings(
            AzureStorageAccountSettings.builder()
                .withBlobEncryptionEnabled(true)
                .build()
        ).build();

    String result = SerializationUtils.serialize(storageAccountSettings.getSettings());
    assertEquals("{\"blobEncryptionEnabled\":true}", result);
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

  private AzureLegacyStorageAccount.AzureLegacyStorageAccountBuilder generateBuilder() {
    return builder().withId("storageaccount");
  }
}