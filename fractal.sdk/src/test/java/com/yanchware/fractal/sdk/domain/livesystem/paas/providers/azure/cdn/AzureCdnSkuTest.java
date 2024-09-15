package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cdn;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class AzureCdnSkuTest {
  @Test
  public void azureCdnSkuTestConstants_shouldNotBeNull() {
    assertThat(AzureCdnSku.STANDARD_EDGIO)
        .as("STANDARD_EDGIO constant should not be null")
        .isNotNull();

    assertThat(AzureCdnSku.PREMIUM_EDGIO)
        .as("PREMIUM_EDGIO constant should not be null")
        .isNotNull();
    

    assertThat(AzureCdnSku.STANDARD_MICROSOFT)
        .as("STANDARD_MICROSOFT constant should not be null")
        .isNotNull();

    assertThat(AzureCdnSku.STANDARD_AZURE_FRONT_DOOR)
        .as("STANDARD_AZURE_FRONT_DOOR constant should not be null")
        .isNotNull();

    assertThat(AzureCdnSku.STANDARD_AZURE_FRONT_DOOR)
        .as("STANDARD_AZURE_FRONT_DOOR constant should not be null")
        .isNotNull();

    assertThat(AzureCdnSku.PREMIUM_AZURE_FRONT_DOOR)
        .as("PREMIUM_AZURE_FRONT_DOOR constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureCdnSku() {
    assertThat(AzureCdnSku.fromString("Standard_Verizon"))
        .as("fromString should return STANDARD_EDGIO for 'Standard_Verizon'")
        .isEqualTo(AzureCdnSku.STANDARD_EDGIO);

    assertThat(AzureCdnSku.fromString("Premium_Verizon"))
        .as("fromString should return PREMIUM_EDGIO for 'Premium_Verizon'")
        .isEqualTo(AzureCdnSku.PREMIUM_EDGIO);

    assertThat(AzureCdnSku.fromString("Standard_Microsoft"))
        .as("fromString should return STANDARD_MICROSOFT for 'Standard_Microsoft'")
        .isEqualTo(AzureCdnSku.STANDARD_MICROSOFT);

    assertThat(AzureCdnSku.fromString("Standard_AzureFrontDoor"))
        .as("fromString should return STANDARD_AZURE_FRONT_DOOR for 'Standard_AzureFrontDoor'")
        .isEqualTo(AzureCdnSku.STANDARD_AZURE_FRONT_DOOR);
    
    assertThat(AzureCdnSku.fromString("Premium_AzureFrontDoor"))
        .as("fromString should return PREMIUM_AZURE_FRONT_DOOR for 'Premium_AzureFrontDoor'")
        .isEqualTo(AzureCdnSku.PREMIUM_AZURE_FRONT_DOOR);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureCdnSkusWithCorrectSize() {
    Collection<AzureCdnSku> values = AzureCdnSku.values();

    assertThat(values)
        .as("Values should contain all specified AzureCdnSku's")
        .containsExactlyInAnyOrder(AzureCdnSku.STANDARD_EDGIO,
            AzureCdnSku.PREMIUM_EDGIO,
            AzureCdnSku.STANDARD_MICROSOFT,
            AzureCdnSku.STANDARD_AZURE_FRONT_DOOR,
            AzureCdnSku.PREMIUM_AZURE_FRONT_DOOR);
  }
}