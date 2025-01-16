package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsCaaRecordData;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsMxRecordData;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DnsMxRecordDataTest {
  @Test
  public void validationError_when_PriorityToSmall() {
    assertThatThrownBy(() -> DnsMxRecordData.builder().withPriority(-1).build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("The Priority value must be between 0 and 65535");
  }

  @Test
  public void validationError_when_PriorityToBig() {
    assertThatThrownBy(() -> DnsMxRecordData.builder().withPriority(65536).build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("The Priority value must be between 0 and 65535");
  }

  @Test
  public void validationError_whenMailExchangeNull() {
    assertThatThrownBy(() -> DnsMxRecordData.builder()
      .withPriority(1)
      .withMailExchange(null)
      .build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("The mailExchange has not been defined and it is required");
  }

  @Test
  public void validationError_when_ValueIsNull() {
    assertThatThrownBy(() -> DnsCaaRecordData.builder()
      .withFlags(255)
      .withTag("tag")
      .withValue(null)
      .build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("The value has not been defined and it is required");
  }
}