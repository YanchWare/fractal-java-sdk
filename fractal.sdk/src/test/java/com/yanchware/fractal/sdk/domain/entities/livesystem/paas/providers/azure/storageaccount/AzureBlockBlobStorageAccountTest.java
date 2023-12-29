package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class AzureBlockBlobStorageAccountTest {
  @Test
  public void kindIsValid_when_ValidationPasses() throws JsonProcessingException {
    var storage = AzureBlockBlobStorageAccount.builder()
        .withId("block-blob-storage-account")
        .withName("a23456789012345678901234")
        .withDisplayName("Block Blob Storage Account")
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

    assertThat(propertyNode.asText()).isEqualTo("BlockBlobStorage");
  }
}