package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AzureKeyVaultCertificateTest {
  @Test
  public void exceptionThrown_when_certificateWithEmptyKeyVaultId() {
    assertThatThrownBy(() -> AzureKeyVaultCertificate.builder()
        .withKeyVaultId("")
        .withName("certificate-name")
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("KeyVaultId has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_certificateWithNullAsKeyVaultId() {
    assertThatThrownBy(() -> AzureKeyVaultCertificate.builder()
        .withKeyVaultId(null)
        .withName("certificate-name")
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("KeyVaultId has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_certificateWithEmptyName() {
    assertThatThrownBy(() -> AzureKeyVaultCertificate.builder()
        .withKeyVaultId("key-vault-id")
        .withName("")
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Name has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_certificateWithNullAsName() {
    assertThatThrownBy(() -> AzureKeyVaultCertificate.builder()
        .withKeyVaultId("key-vault-id")
        .withName(null)
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Name has not been defined and it is required");
  }

  @Test
  public void noValidationErrors_when_certificateHasRequiredFields() {
    var keyVaultId = "key-vault-id";
    var certificateName = "certificate-name";
    var certificateFriendlyName = "certificate-friendly-name";
    var certificatePassword = "password";
    var tags = Map.of("key3", "value3", "key4", "value4");
    
    var certificate = AzureKeyVaultCertificate.builder()
        .withKeyVaultId(keyVaultId)
        .withName(certificateName)
        .withFriendlyName(certificateFriendlyName)
        .withPassword(certificatePassword)
        .withRegion(AzureRegion.EUROPE_WEST)
        .withTags(tags)
        .withTag("key1", "value1")
        .withTag("key2", "value2")
        .build();

    assertThat(certificate.validate()).isEmpty();

    assertThat(certificate)
        .asInstanceOf(InstanceOfAssertFactories.type(AzureKeyVaultCertificate.class))
        .extracting(AzureKeyVaultCertificate::getKeyVaultId,
            AzureKeyVaultCertificate::getName,
            AzureKeyVaultCertificate::getFriendlyName,
            AzureKeyVaultCertificate::getPassword,
            AzureKeyVaultCertificate::getRegion,
            AzureKeyVaultCertificate::getTags)
        .containsExactly(keyVaultId, 
            certificateName, 
            certificateFriendlyName,
            certificatePassword, 
            AzureRegion.EUROPE_WEST,
            Map.of("key1", "value1", "key2", "value2", "key3", "value3", "key4", "value4"));
  }
}