package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AzureManagedPipelineModeTest {
  @Test
  public void shouldReturnProperValue() {
    assertAll(
        () -> assertEquals(AzureManagedPipelineMode.fromString("Classic"), AzureManagedPipelineMode.CLASSIC),
        () -> assertEquals(AzureManagedPipelineMode.fromString("Integrated"), AzureManagedPipelineMode.INTEGRATED)
    );
  }
}