package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AzureIpFilterTagTest {
  @Test
  public void shouldReturnProperValue() {
    assertAll(
        () -> assertEquals(AzureIpFilterTag.fromString("Default"), AzureIpFilterTag.DEFAULT),
        () -> assertEquals(AzureIpFilterTag.fromString("ServiceTag"), AzureIpFilterTag.SERVICE_TAG),
        () -> assertEquals(AzureIpFilterTag.fromString("XffProxy"), AzureIpFilterTag.XFF_PROXY)
    );
  }
}