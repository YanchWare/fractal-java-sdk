package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureResourceGroupTest {
  @Test
  public void correctValues_when_AzureResourceGroupWithEnumBuilt() {
    var resourceGroupName = "resourceGroupName";
    var region = AzureRegion.WEST_EUROPE;

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
        .contains(resourceGroupName, AzureRegion.EAST_US2);
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
