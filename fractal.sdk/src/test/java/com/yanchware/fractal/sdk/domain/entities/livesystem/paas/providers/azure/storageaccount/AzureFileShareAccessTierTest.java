package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureFileShareAccessTierTest {
  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureFileShareAccessTier.COOL, "COOL constant should not be null");
    assertNotNull(AzureFileShareAccessTier.HOT, "HOT constant should not be null");
    assertNotNull(AzureFileShareAccessTier.PREMIUM, "PREMIUM constant should not be null");
    assertNotNull(AzureFileShareAccessTier.TRANSACTION_OPTIMIZED, "TRANSACTION_OPTIMIZED constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureFileShareAccessTier.COOL, AzureFileShareAccessTier.fromString("Cool"), "fromString should return COOL for 'Cool'");
    assertEquals(AzureFileShareAccessTier.HOT, AzureFileShareAccessTier.fromString("Hot"), "fromString should return HOT for 'Hot'");
    assertEquals(AzureFileShareAccessTier.PREMIUM, AzureFileShareAccessTier.fromString("Premium"), "fromString should return PREMIUM for 'Premium'");
    assertEquals(AzureFileShareAccessTier.TRANSACTION_OPTIMIZED, AzureFileShareAccessTier.fromString("TransactionOptimized"), "fromString should return TRANSACTION_OPTIMIZED for 'TransactionOptimized'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureFileShareAccessTier> values = AzureFileShareAccessTier.values();
    assertTrue(values.contains(AzureFileShareAccessTier.COOL), "Values should contain COOL");
    assertTrue(values.contains(AzureFileShareAccessTier.HOT), "Values should contain HOT");
    assertTrue(values.contains(AzureFileShareAccessTier.PREMIUM), "Values should contain PREMIUM");
    assertTrue(values.contains(AzureFileShareAccessTier.TRANSACTION_OPTIMIZED), "Values should contain TRANSACTION_OPTIMIZED");
    assertEquals(4, values.size(), "There should be exactly 4 values");
  }
}