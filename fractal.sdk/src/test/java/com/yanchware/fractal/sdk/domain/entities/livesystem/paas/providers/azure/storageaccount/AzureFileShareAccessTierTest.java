package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureFileShareAccessTierTest {
  @Test
  public void azureFileShareAccessTierConstants_shouldNotBeNull() {
    assertThat(AzureFileShareAccessTier.COOL).as("COOL constant should not be null").isNotNull();
    assertThat(AzureFileShareAccessTier.HOT).as("HOT constant should not be null").isNotNull();
    assertThat(AzureFileShareAccessTier.PREMIUM).as("PREMIUM constant should not be null").isNotNull();
    assertThat(AzureFileShareAccessTier.TRANSACTION_OPTIMIZED).as("TRANSACTION_OPTIMIZED constant should not be null").isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureFileShareAccessTier() {
    assertThat(AzureFileShareAccessTier.fromString("Cool"))
        .as("fromString should return COOL for 'Cool'")
        .isEqualTo(AzureFileShareAccessTier.COOL);
    
    assertThat(AzureFileShareAccessTier.fromString("Hot"))
        .as("fromString should return HOT for 'Hot'")
        .isEqualTo(AzureFileShareAccessTier.HOT);
    
    assertThat(AzureFileShareAccessTier.fromString("Premium"))
        .as("fromString should return PREMIUM for 'Premium'")
        .isEqualTo(AzureFileShareAccessTier.PREMIUM);
    
    assertThat(AzureFileShareAccessTier.fromString("TransactionOptimized"))
        .as("fromString should return TRANSACTION_OPTIMIZED for 'TransactionOptimized'")
        .isEqualTo(AzureFileShareAccessTier.TRANSACTION_OPTIMIZED);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureFileShareAccessTiersWithCorrectSize() {
    Collection<AzureFileShareAccessTier> values = AzureFileShareAccessTier.values();

    assertThat(values)
        .as("Values should contain COOL, HOT, PREMIUM, and TRANSACTION_OPTIMIZED and have exactly 4 values")
        .containsExactlyInAnyOrder(AzureFileShareAccessTier.COOL,
            AzureFileShareAccessTier.HOT,
            AzureFileShareAccessTier.PREMIUM,
            AzureFileShareAccessTier.TRANSACTION_OPTIMIZED);
  }
}