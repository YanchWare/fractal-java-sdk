package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureMachineTypeTest {
  @Test
  @Order(1)
  public void valuesContainsNewValue_when_CustomValueAdded() {

    var newValue = AzureMachineType.fromString("This_Not_Exist_For_Sure");
    assertThat(AzureMachineType.values()).contains(newValue);
  }

  @Test
  @Order(2)
  public void correctValues_when_customEnum() {
    var valueToExtend = "This_Not_Exist_For_Sure";
    var enumValue = AzureMachineType.fromString(valueToExtend);

    assertThat(valueToExtend).isEqualTo(enumValue.toString());
  }

  @Test
  @Order(3)
  public void correctValues_when_Enum() {
    var existingEnum = "Basic_A0";
    var enumValue = AzureMachineType.fromString(existingEnum);

    assertThat(existingEnum).isEqualTo(enumValue.toString());
  }
}
