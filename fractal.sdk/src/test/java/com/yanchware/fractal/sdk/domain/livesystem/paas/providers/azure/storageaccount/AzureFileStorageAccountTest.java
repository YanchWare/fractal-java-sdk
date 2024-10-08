package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.TestWithFixture;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AzureFileStorageAccountTest extends TestWithFixture {
  @Test
  public void kindIsValid_when_ValidationPasses() throws JsonProcessingException {
    var storage = AzureFileStorageAccount.builder()
        .withId(aComponentId())
        .withName(aLowerCaseAlphanumericString(10))
        .withDisplayName(aAlphanumericString(50))
        .withRegion(a(AzureRegion.class))
        .withResourceGroup(a(AzureResourceGroup.class))
        .withTag(a(String.class), a(String.class))
        .build();
    assertTrue(storage.validate().isEmpty());

    var json = TestUtils.getJsonRepresentation(storage);
    assertThat(json).isNotBlank();

    var mapper = new ObjectMapper();
    var rootNode = mapper.readTree(json);
    var propertyNode = rootNode.path("kind");

    assertThat(propertyNode).isNotNull();

    assertThat(propertyNode.asText()).isEqualTo("FileStorage");
  }

  @Test
  public void fileShareIsValid_when_ValidationPasses() throws JsonProcessingException {
    var storageId = aComponentId();
    var storageName = aLowerCaseAlphanumericString(10);
    var storageDisplayName = aAlphanumericString(50, false, false, " ");
    var storageRegion = AzureRegion.WEST_EUROPE;
    
    var storage = AzureFileStorageAccount.builder()
        .withId(storageId)
        .withName(storageName)
        .withDisplayName(storageDisplayName)
        .withRegion(storageRegion)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName(aLowerCaseAlphanumericString(10))
            .withRegion(storageRegion)
            .build())
        .withFileShare(AzureFileShare.builder()
            .withId(aLowerCaseAlphanumericString(10, true, "-"))
            .withName(aLowerCaseAlphanumericString(10, true, "-"))
            .withDisplayName(aAlphanumericString(50))
            .withEnabledProtocols(AzureFileShareEnabledProtocols.SMB)
            .withShareQuota(100)
            .withAccessTier(a(AzureFileShareAccessTier.class))
            .build())
        .withFileShare(AzureFileShare.builder()
            .withId(aLowerCaseAlphanumericString(10, true, "-"))
            .withName(aLowerCaseAlphanumericString(10, true, "-"))
            .withDisplayName(aAlphanumericString(50))
            .withEnabledProtocols(AzureFileShareEnabledProtocols.NFS)
            .withRootSquash(a(AzureFileShareRootSquashType.class))
            .withShareQuota(100)
            .withAccessTier(a(AzureFileShareAccessTier.class))
            .build())
        .withTag(a(String.class), a(String.class))
        .build();
    
    assertThat(storage.validate().isEmpty()).isTrue();
    assertThat(storage.getId().getValue()).isEqualTo(storageId);
    assertThat(storage.getName()).isEqualTo(storageName);
    assertThat(storage.getDisplayName()).isEqualTo(storageDisplayName);
    assertThat(storage.getAzureRegion()).isEqualTo(storageRegion);
    assertThat(storage.getAzureResourceGroup().getRegion()).isEqualTo(storageRegion);
    
    var json = TestUtils.getJsonRepresentation(storage);
    assertThat(json).isNotBlank();

    var mapper = new ObjectMapper();
    var rootNode = mapper.readTree(json);
    var kindNode = rootNode.path("kind");
    var fileSharesNode = rootNode.path("fileShares");

    assertThat(kindNode).isNotNull();
    assertThat(fileSharesNode).isNotNull();

    assertThat(kindNode.asText()).isEqualTo("FileStorage");
    assertThat(fileSharesNode.size()).isEqualTo(2);
  }
}