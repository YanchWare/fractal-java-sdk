package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.*;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.*;
import com.yanchware.fractal.sdk.utils.SerializationUtils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.AzureStorageAccount.builder;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class AzureStorageAccountTest {

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullId() {
    assertThatThrownBy(() -> builder().withId("").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Component Id is illegal");
  }

  @Test
  public void regionValid_when_regionIsSet() {
    var storageAccount = generateBuilder()
        .withRegion(AzureRegion.CANADA_EAST)
        .build();

    assertEquals(storageAccount.getAzureRegion(), storageAccount.getAzureRegion());
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
  public void connectivityIsValid_when_connectivityIsSet() {
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
          .withNetworkRuleSetDefaultAction(AzureStorageDefaultAction.ALLOW)
          .withNetworkRuleSetIpRules(new ArrayList<>())
          .withPublicNetworkAccess(AzureStoragePublicNetworkAccess.ENABLED)
          .withPublishInternetEndpoints(true)
          .withPublishMicrosoftEndpoints(true)
          .withRoutingChoise("routingChoise")
          .withSasPolicyExpirationAction("sasPolicyExpirationAction")
          .withSasPolicyExpirationPeriod("sasPolicyExpirationPeriod")
          .withNetworkRuleSetBypass(AzureStorageBypass.NONE)
          .withIsLocalUserEnabled(true)
          .build()
        )
        .build()
        .getConnectivity();

      assertNull(storageAccountConnectivity.getEnableNfsV3());
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
      assertTrue(storageAccountConnectivity.getIsLocalUserEnabled());
      assertEquals(10, storageAccountConnectivity.getKeyPolicyExpirationInDays());
      assertEquals(AzureStorageBypass.NONE, storageAccountConnectivity.getNetworkRuleSetBypass());
      assertEquals(AzureStorageDefaultAction.ALLOW, storageAccountConnectivity.getNetworkRuleSetDefaultAction());
      assertEquals(AzureStoragePublicNetworkAccess.ENABLED, storageAccountConnectivity.getPublicNetworkAccess());
      assertEquals("routingChoise", storageAccountConnectivity.getRoutingChoise());
      assertEquals("sasPolicyExpirationAction", storageAccountConnectivity.getSasPolicyExpirationAction());
      assertEquals("sasPolicyExpirationPeriod", storageAccountConnectivity.getSasPolicyExpirationPeriod());
      assertEquals(new ArrayList<>(), storageAccountConnectivity.getNetworkRuleSetIpRules());
      assertEquals("domainGuid", storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectoryDomainGuid());
      assertEquals("domainName", storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectoryDomainName());
      assertEquals("domainSid", storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectoryDomainSid());
      assertEquals("forestName", storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectoryForestName());
      assertEquals("samAccount", storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectorySamAccount());
      assertEquals("netBiosDomainName", storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectoryNetBiosDomainName());
      assertEquals("storageSid", storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectoryStorageSid());
      assertEquals(AzureDefaultSharePermission.NONE, storageAccountConnectivity.getAzureIdentityBasedDefaultSharePermission());
      assertEquals(AzureDirectoryServiceOptions.NONE, storageAccountConnectivity.getAzureIdentityBasedDirectoryServiceOptions());
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
  
  private AzureStorageAccount.AzureStorageAccountBuilder generateBuilder() {
    return builder().withId("storage-account");
  }
  
}
