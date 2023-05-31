package com.yanchware.fractal.sdk.domain.entities.environment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DnsARecordTest {

  @Test
  public void validationError_when_NameIsNull() {
    assertThatThrownBy(() -> DnsARecord.builder().withName(null).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("[DnsARecord Validation] The Name must contain between 1 and 63 characters");
  }
}