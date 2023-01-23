package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.*;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.AzureStorageAccount.builder;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
  public void resourceGroupNameValid_when_resourceGroupNameIsSet() {
    var storageAccount = generateBuilder()
      .withResourceGroup(new AzureResourceGroup(AzureRegion.CANADA_EAST, "rgName"))
      .build();

    assertEquals(storageAccount.getAzureResourceGroup().getName(), storageAccount.getAzureResourceGroup().getName());
    assertEquals(storageAccount.getAzureResourceGroup().getRegion(), storageAccount.getAzureResourceGroup().getRegion());
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
            .withDefaultToOAuthAuthentication(true)
            .withMinimumTlsVersion(AzureTlsVersion.TLS1_2)
            .withEnableHttpsTrafficOnly(true)
            .withEnableNfsV3(true)
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

      assertTrue(storageAccountConnectivity.isAllowBlobPublicAccess());
      assertEquals(AzureActiveDirectoryAccountType.USER, storageAccountConnectivity.getAzureIdentityBasedAuthAzureDirectoryAccountType());
      assertTrue(storageAccountConnectivity.isDefaultToOAuthAuthentication());
      assertEquals(AzureTlsVersion.TLS1_2, storageAccountConnectivity.getMinimumTlsVersion());
      assertTrue(storageAccountConnectivity.isAllowBlobPublicAccess());
      assertTrue(storageAccountConnectivity.isEnableHttpsTrafficOnly());
      assertTrue(storageAccountConnectivity.isEnableNfsV3());
      assertTrue(storageAccountConnectivity.isLocalUserEnabled());
      assertTrue(storageAccountConnectivity.isSftpEnabled());
      assertTrue(storageAccountConnectivity.isPublishInternetEndpoints());
      assertTrue(storageAccountConnectivity.isPublishMicrosoftEndpoints());
      assertTrue(storageAccountConnectivity.isLocalUserEnabled());
      assertEquals(10, storageAccountConnectivity.getKeyPolicyExpirationInDays());
      assertEquals(AzureStorageBypass.NONE, storageAccountConnectivity.getNetworkRuleSetBypass());
      assertEquals(AzureStorageDefaultAction.ALLOW, storageAccountConnectivity.getNetworkRuleSetDefaultAction());
      assertEquals(AzureStoragePublicNetworkAccess.ENABLED, storageAccountConnectivity.getPublicNetworkAccess());
      assertEquals("routingChoise", storageAccountConnectivity.getRoutingChoise());
      assertEquals("sasPolicyExpirationAction", storageAccountConnectivity.getSasPolicyExpirationAction());
      assertEquals("sasPolicyExpirationPeriod", storageAccountConnectivity.getSasPolicyExpirationPeriod());
      assertEquals(new ArrayList<>(), storageAccountConnectivity.getNetworkRuleSetIpRules());
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

      assertTrue(storageAccountSettings.isAccountImmutabilityAllowProtectedAppendWrites());
      assertTrue(storageAccountSettings.isAccountImmutabilityEnabled());
      assertTrue(storageAccountSettings.isAllowCrossTenantReplication());
      assertTrue(storageAccountSettings.isAllowSharedKeyAccess());
      assertTrue(storageAccountSettings.isBlobEncryptionEnabled());
      assertTrue(storageAccountSettings.isFileEncryptionEnabled());
      assertTrue(storageAccountSettings.isHnsEnabled());
      assertTrue(storageAccountSettings.isQueueEncryptionEnabled());
      assertTrue(storageAccountSettings.isTableEncryptionEnabled());
      assertTrue(storageAccountSettings.isRequireInfrastructureEncryption());
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

    assertTrue(storageAccountSettings.isCustomDomainUseSubName());
    assertEquals(new HashMap<>(), storageAccountSettings.getUserAssignedIdentities());
    assertEquals(AzureStorageAccessTier.HOT, storageAccountSettings.getAccessTier());
    assertEquals(AzureStorageSkuName.PREMIUM_LRS, storageAccountSettings.getSku());
    assertEquals(AzureDnsEndpointType.AZURE_DNS_ZONE, storageAccountSettings.getDnsEndpointType());
    assertEquals(AzureIdentityType.NONE, storageAccountSettings.getIdentityType());
    assertEquals("customDomainName", storageAccountSettings.getCustomDomainName());
    assertEquals("extendedLocationName", storageAccountSettings.getExtendedLocationName());
    assertEquals("extendedLocationType", storageAccountSettings.getExtendedLocationType());
  }    

  
  private AzureStorageAccount.AzureStorageAccountBuilder generateBuilder() {
    return builder().withId("storage-account");
  }
  
}
