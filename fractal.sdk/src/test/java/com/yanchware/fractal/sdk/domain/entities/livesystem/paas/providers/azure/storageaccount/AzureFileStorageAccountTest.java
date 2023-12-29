package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AzureFileStorageAccountTest {
  @Test
  public void kindIsValid_when_ValidationPasses() throws JsonProcessingException {
    var storage = AzureFileStorageAccount.builder()
        .withId("file-storage-account")
        .withName("a23456789012345678901234")
        .withDisplayName("File Storage Account")
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

    assertThat(propertyNode.asText()).isEqualTo("FileStorage");
  }

  @Test
  public void fileShareIsValid_when_ValidationPasses() throws JsonProcessingException {
    var storage = AzureFileStorageAccount.builder()
        .withId("file-storage-account")
        .withName("a23456789012345678901234")
        .withDisplayName("File Storage Account")
        .withRegion(AzureRegion.EUROPE_WEST)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("validName123")
            .withRegion(AzureRegion.EUROPE_WEST)
            .build())
        .withFileShare(AzureFileShare.builder()
            .withId("templates")
            .withName("templates")
            .withDisplayName("Templates")
            .withEnabledProtocols(AzureFileShareEnabledProtocols.SMB)
            .withShareQuota(100)
            .withAccessTier(AzureFileShareAccessTier.PREMIUM)
            .build())
        .withFileShare(AzureFileShare.builder()
            .withId("nfs-templates")
            .withName("nfs")
            .withDisplayName("NFS Templates")
            .withEnabledProtocols(AzureFileShareEnabledProtocols.NFS)
            .withRootSquash(AzureFileShareRootSquashType.ROOT_SQUASH)
            .withShareQuota(100)
            .withAccessTier(AzureFileShareAccessTier.PREMIUM)
            .build())
        .withTag("key1", "value1")
        .build();
    assertTrue(storage.validate().isEmpty());
    
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