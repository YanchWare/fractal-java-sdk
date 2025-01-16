package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AzureFtpsStateTest {
  @Test
  public void shouldReturnProperValue() {
    assertAll(
      () -> assertEquals(AzureFtpsState.fromString("AllAllowed"), AzureFtpsState.ALL_ALLOWED),
      () -> assertEquals(AzureFtpsState.fromString("Disabled"), AzureFtpsState.DISABLED),
      () -> assertEquals(AzureFtpsState.fromString("FtpsOnly"), AzureFtpsState.FTPS_ONLY)
    );
  }
}