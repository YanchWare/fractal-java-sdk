package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureTlsVersionTest {
  @Test
  public void tlsVersionsConstants_shouldNotBeNull() {
    assertThat(AzureTlsVersion.TLS1_0).as("TLS1_0 constant should not be null").isNotNull();
    assertThat(AzureTlsVersion.TLS1_1).as("TLS1_1 constant should not be null").isNotNull();
    assertThat(AzureTlsVersion.TLS1_2).as("TLS1_2 constant should not be null").isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingTlsVersion() {
    assertThat(AzureTlsVersion.fromString("TLS1_0"))
      .as("fromString should return TLS1_0 for 'TLS1_0'")
      .isEqualTo(AzureTlsVersion.TLS1_0);

    assertThat(AzureTlsVersion.fromString("TLS1_1"))
      .as("fromString should return TLS1_1 for 'TLS1_1'")
      .isEqualTo(AzureTlsVersion.TLS1_1);

    assertThat(AzureTlsVersion.fromString("TLS1_2"))
      .as("fromString should return TLS1_2 for 'TLS1_2'")
      .isEqualTo(AzureTlsVersion.TLS1_2);
  }

  @Test
  public void valuesMethod_shouldContainAllTlsVersionsWithCorrectSize() {
    Collection<AzureTlsVersion> values = AzureTlsVersion.values();

    assertThat(values)
      .as("Values should contain TLS1_0, TLS1_1, and TLS1_2 and have exactly 3 values")
      .containsExactlyInAnyOrder(AzureTlsVersion.TLS1_0, AzureTlsVersion.TLS1_1, AzureTlsVersion.TLS1_2);
  }
}