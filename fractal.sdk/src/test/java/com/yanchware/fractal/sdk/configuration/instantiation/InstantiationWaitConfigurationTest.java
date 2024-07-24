package com.yanchware.fractal.sdk.configuration.instantiation;

import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_LIVE_SYSTEM_INSTANTIATION_WAIT_TIMEOUT_MINUTES;
import static org.assertj.core.api.Assertions.assertThat;

class InstantiationWaitConfigurationTest {
  @Test
  void testDefaultConfiguration() {
    var config = InstantiationWaitConfiguration.builder().build();

    // Assert default values
    assertThat(config.isWaitForInstantiation()).isFalse();
    assertThat(config.getTimeoutMinutes()).isNull();
  }

  @Test
  void testWaitForInstantiation() {
    InstantiationWaitConfiguration config = InstantiationWaitConfiguration.builder()
        .withWaitForInstantiation(true)
        .build();

    assertThat(config.isWaitForInstantiation()).isTrue();
    assertThat(config.getTimeoutMinutes()).isEqualTo(DEFAULT_LIVE_SYSTEM_INSTANTIATION_WAIT_TIMEOUT_MINUTES);
  }

  @Test
  void testCustomTimeout() {
    InstantiationWaitConfiguration config = InstantiationWaitConfiguration.builder()
        .withWaitForInstantiation(true)
        .withTimeoutMinutes(30)
        .build();

    assertThat(config.isWaitForInstantiation()).isTrue();
    assertThat(config.getTimeoutMinutes()).isEqualTo(30);
  }
}