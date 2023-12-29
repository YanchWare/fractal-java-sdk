package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureResourceGroupTest {
  @Test
  public void correctValues_when_AzureResourceGroupWithEnumBuilt() {
    var resourceGroupName = "resourceGroupName";
    var region = AzureRegion.EUROPE_WEST;

    var azureResourceGroup = AzureResourceGroup.builder()
        .withName(resourceGroupName)
        .withRegion(region)
        .build();

    assertThat(azureResourceGroup)
        .extracting("name", "region")
        .contains(resourceGroupName, region);
  }

  @Test
  public void correctValues_when_AzureResourceGroupWithEnumAsStringBuilt() {
    var resourceGroupName = "resourceGroupName";

    var azureResourceGroup = AzureResourceGroup.builder()
        .withName(resourceGroupName)
        .withRegion(AzureRegion.fromString("eastus2"))
        .build();

    assertThat(azureResourceGroup)
        .extracting("name", "region")
        .contains(resourceGroupName, AzureRegion.US_EAST2);
  }

  @Test
  public void validationError_when_AzureResourceGroupWithWrongEnumAsStringBuilt() {
    assertThatThrownBy(() -> AzureResourceGroup.builder()
        .withName("resourceGroupName")
        .withRegion(AzureRegion.fromString("wrong_enum"))
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("[AzureResourceGroup Validation] Region has not been defined and it is required");
  }

  @Test
  public void validationError_when_AzureResourceGroupWithWrongNameAsStringBuilt() {
    assertThatThrownBy(() -> AzureResourceGroup.builder()
        .withName("")
        .withRegion(AzureRegion.fromString("wrong_enum"))
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("[AzureResourceGroup Validation] Name cannot be null or empty");
  }
}
