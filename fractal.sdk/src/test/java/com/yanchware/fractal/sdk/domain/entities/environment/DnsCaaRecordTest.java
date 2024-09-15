package com.yanchware.fractal.sdk.domain.entities.environment;

import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsCaaRecord;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DnsCaaRecordTest {
  @Test
  public void validationError_when_NameIsNull() {
    assertThatThrownBy(() -> DnsCaaRecord.builder().withName(null).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("[DnsCaaRecord Validation] The Name must contain between 1 and 63 characters");
  }
}