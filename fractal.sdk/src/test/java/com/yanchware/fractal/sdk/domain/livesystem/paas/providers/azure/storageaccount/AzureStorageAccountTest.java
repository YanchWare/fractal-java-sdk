package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AzureStorageAccountTest {

  @Test
  public void kindIsValid_when_ValidationPasses() throws JsonProcessingException {
    var storage = AzureStorageAccount.builder()
        .withId("storage-account")
        .withName("a23456789012345678901234")
        .withRegion(AzureRegion.WEST_EUROPE)
        .withResourceGroup(AzureResourceGroup.builder()
            .withName("validName123")
            .withRegion(AzureRegion.WEST_EUROPE)
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

    assertThat(propertyNode.asText()).isEqualTo("StorageV2");
  }
}