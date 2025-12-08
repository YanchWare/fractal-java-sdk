package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub;

import com.yanchware.fractal.sdk.domain.livesystem.paas.SupportedTlsVersions;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureIdentityType;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub.valueobjects.CleanupPolicies;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub.valueobjects.EventhubSku;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub.valueobjects.EventhubSkuTier;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub.valueobjects.RetentionDescription;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.servicebus.valueobjects.Encryption;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.servicebus.valueobjects.KeySource;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects.AzurePublicNetworkAccess;
import com.yanchware.fractal.sdk.domain.values.ComponentId;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion.WEST_EUROPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureEventhubTest {

  @Test
  public void exceptionThrown_when_idIsLessThan6Characters() {
    assertThatThrownBy(() -> AzureEventhubNamespace.builder().withId("comp-id").withName("test").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("valid Eventhub namespace must be between 6 and 50 characters of length");
  }

  @Test
  public void exceptionThrown_when_idIsMoreThan50Characters() {
    assertThatThrownBy(() -> AzureEventhubNamespace.builder().withId("comp-id").withName("ttttttttttttttttttttttttttttttttttttttttttttttttttt").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("valid Eventhub namespace must be between 6 and 50 characters of length");
  }

  @Test
  public void noValidationErrors_when_eventhubHasRequiredFields() {
    var azureResourceGroup = AzureResourceGroup.builder().withName("az-group").withRegion(WEST_EUROPE).build();
    
    var idOrName = "eh-test-x";
    
    var tags = Map.of("tagKey1", "tagValue1");
    
    var sku = EventhubSku.builder()
        .withTier(EventhubSkuTier.BASIC)
        .build();
    
    var encryption = Encryption.builder()
        .withKeySource(KeySource.MICROSOFT_KEYVAULT)
        .build();
    
    var instance = AzureEventhubInstance.builder()
        .withId("instance-id")
        .withDisplayName("instance1")
        .withRetention(new RetentionDescription(1L,1L, CleanupPolicies.COMPACT))
        .withMessageRetentionInDays(2L)
        .withPartitionCount(2L)
        .withUserMetadata("user-metadata")
        .build();

    var eventhub = AzureEventhubNamespace.builder()
        .withId(idOrName)
        .withName(idOrName)
        .withRegion(AzureRegion.WEST_EUROPE)
        .withAzureResourceGroup(azureResourceGroup)
        .withTags(tags)
        .withSku(sku)
        .withIdentity(AzureIdentityType.USER_ASSIGNED)
        .withEncryption(encryption)
        .withDisableLocalAuth(Boolean.FALSE)
        .withZoneRedundant(Boolean.FALSE)
        .withAutoInflateEnabled(Boolean.TRUE)
        .withMaximumThroughputUnits(0)
        .withMinimumTlsVersion(SupportedTlsVersions.ONE_TWO)
        .withPublicNetworkAccess(AzurePublicNetworkAccess.DISABLED)
        .withInstance(instance)
    .build();
    
    assertThat(eventhub.validate()).isEmpty();
    
    assertThat(eventhub)
        .asInstanceOf(InstanceOfAssertFactories.type(AzureEventhubNamespace.class))
        .extracting(
            AzureEventhubNamespace::getId,
            AzureEventhubNamespace::getName,
            AzureEventhubNamespace::getAzureRegion,
            AzureEventhubNamespace::getAzureResourceGroup,
            AzureEventhubNamespace::getTags,
            AzureEventhubNamespace::getSku,
            AzureEventhubNamespace::getIdentity,
            AzureEventhubNamespace::getEncryption,
            AzureEventhubNamespace::getDisableLocalAuth,
            AzureEventhubNamespace::getZoneRedundant,
            AzureEventhubNamespace::getAutoInflateEnabled,
            AzureEventhubNamespace::getMaximumThroughputUnits,
            AzureEventhubNamespace::getMinimumTlsVersion,
            AzureEventhubNamespace::getPublicNetworkAccess,
            AzureEventhubNamespace::getInstances)
        
        .containsExactly(ComponentId.from(idOrName),
            idOrName,
            AzureRegion.WEST_EUROPE,
            azureResourceGroup,
            tags,
            sku,
            AzureIdentityType.USER_ASSIGNED,
            encryption,
            Boolean.FALSE,
            Boolean.FALSE,
            Boolean.TRUE,
            0,
            SupportedTlsVersions.ONE_TWO,
            AzurePublicNetworkAccess.DISABLED,
            List.of(instance));
  }
}