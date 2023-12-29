package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AzureFileShareRootSquashTypeTest {

  @Test
  public void testConstantsCreation() {
    assertNotNull(AzureFileShareRootSquashType.ALL_SQUASH, "ALL_SQUASH constant should not be null");
    assertNotNull(AzureFileShareRootSquashType.NO_ROOT_SQUASH, "NO_ROOT_SQUASH constant should not be null");
    assertNotNull(AzureFileShareRootSquashType.ROOT_SQUASH, "ROOT_SQUASH constant should not be null");
  }

  @Test
  public void testFromString() {
    assertEquals(AzureFileShareRootSquashType.ALL_SQUASH,
        AzureFileShareRootSquashType.fromString("AllSquash"),
        "fromString should return ALL_SQUASH for 'AllSquash'");

    assertEquals(AzureFileShareRootSquashType.NO_ROOT_SQUASH,
        AzureFileShareRootSquashType.fromString("NoRootSquash"),
        "fromString should return NO_ROOT_SQUASH for 'NoRootSquash'");

    assertEquals(AzureFileShareRootSquashType.ROOT_SQUASH,
        AzureFileShareRootSquashType.fromString("RootSquash"),
        "fromString should return ROOT_SQUASH for 'RootSquash'");
  }

  @Test
  public void testValuesMethod() {
    Collection<AzureFileShareRootSquashType> values = AzureFileShareRootSquashType.values();
    assertTrue(values.contains(AzureFileShareRootSquashType.ALL_SQUASH), "Values should contain ALL_SQUASH");
    assertTrue(values.contains(AzureFileShareRootSquashType.NO_ROOT_SQUASH), "Values should contain NO_ROOT_SQUASH");
    assertTrue(values.contains(AzureFileShareRootSquashType.ROOT_SQUASH), "Values should contain ROOT_SQUASH");
    assertEquals(3, values.size(), "There should be exactly 3 values");
  }
}