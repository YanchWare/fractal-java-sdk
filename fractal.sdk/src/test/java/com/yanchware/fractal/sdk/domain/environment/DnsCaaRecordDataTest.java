package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsCaaRecordData;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DnsCaaRecordDataTest {
  @Test
  public void validationError_when_FlagsToSmall() {
    assertThatThrownBy(() -> DnsCaaRecordData.builder().withFlags(-1).build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("The Flags value must be between 0 and 255");
  }

  @Test
  public void validationError_when_FlagsToBig() {
    assertThatThrownBy(() -> DnsCaaRecordData.builder().withFlags(256).build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("The Flags value must be between 0 and 255");
  }

  @Test
  public void validationError_when_TagIsNull() {
    assertThatThrownBy(() -> DnsCaaRecordData.builder()
      .withFlags(255)
      .withTag(null)
      .build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("The tag has not been defined and it is required");
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