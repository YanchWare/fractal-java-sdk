package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.TestWithFixture;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureBlockBlobStorageAccountTest extends TestWithFixture  {
  @Test
  public void kindIsValid_when_ValidationPasses() throws JsonProcessingException {
    var storage = AzureBlockBlobStorageAccount.builder()
        .withId(aComponentId())
        .withName(aLowerCaseAlphanumericString(10))
        .withDisplayName(aAlphanumericString(50))
        .withRegion(a(AzureRegion.class))
        .withResourceGroup(a(AzureResourceGroup.class))
        .withTag(a(String.class), a(String.class))
        .build();
    
    assertThat(storage.validate().isEmpty()).isTrue();

    var json = TestUtils.getJsonRepresentation(storage);
    assertThat(json).isNotBlank();

    var mapper = new ObjectMapper();
    var rootNode = mapper.readTree(json);
    var propertyNode = rootNode.path("kind");

    assertThat(propertyNode).isNotNull();

    assertThat(propertyNode.asText()).isEqualTo("BlockBlobStorage");
  }
}