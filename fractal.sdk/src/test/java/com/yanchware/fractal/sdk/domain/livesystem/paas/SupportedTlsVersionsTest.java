package com.yanchware.fractal.sdk.domain.livesystem.paas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SupportedTlsVersionsTest {
  @Test
  public void shouldReturnProperValue() {
    assertAll(
      () -> assertEquals(SupportedTlsVersions.fromString("1.0"), SupportedTlsVersions.ONE_ZERO),
      () -> assertEquals(SupportedTlsVersions.fromString("1.1"), SupportedTlsVersions.ONE_ONE),
      () -> assertEquals(SupportedTlsVersions.fromString("1.2"), SupportedTlsVersions.ONE_TWO)
    );
  }
}