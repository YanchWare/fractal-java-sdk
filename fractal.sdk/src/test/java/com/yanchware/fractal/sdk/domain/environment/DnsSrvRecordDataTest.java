package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsSrvRecordData;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DnsSrvRecordDataTest {
  @Test
  public void validationError_when_PriorityToSmall() {
    assertThatThrownBy(() -> DnsSrvRecordData.builder().withPriority(-1).build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("The priority value must be less than or equal to 65535");
  }

  @Test
  public void validationError_when_PriorityToBig() {
    assertThatThrownBy(() -> DnsSrvRecordData.builder().withPriority(65536).build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("The priority value must be less than or equal to 65535");
  }

  @Test
  public void validationError_when_WeightToSmall() {
    assertThatThrownBy(() -> DnsSrvRecordData.builder()
      .withPriority(1)
      .withWeight(-1)
      .build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("The weight value must be less than or equal to 65535");
  }

  @Test
  public void validationError_when_PortToBig() {
    assertThatThrownBy(() -> DnsSrvRecordData.builder()
      .withPriority(65535)
      .withWeight(65535)
      .withPort(65536)
      .build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("The port value must be less than or equal to 65535");
  }

  @Test
  public void validationError_when_PortToSmall() {
    assertThatThrownBy(() -> DnsSrvRecordData.builder()
      .withPriority(1)
      .withWeight(1)
      .withPort(-1)
      .build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("The port value must be less than or equal to 65535");
  }

  @Test
  public void validationError_when_WeightToBig() {
    assertThatThrownBy(() -> DnsSrvRecordData.builder()
      .withPriority(65535)
      .withWeight(65536)
      .build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("The weight value must be less than or equal to 65535");
  }

  @Test
  public void validationError_when_TargetIsNull() {
    assertThatThrownBy(() -> DnsSrvRecordData.builder()
      .withPriority(1)
      .withWeight(1)
      .withPort(1)
      .withTarget(null)
      .build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Target has not been defined and it is required");
  }
}