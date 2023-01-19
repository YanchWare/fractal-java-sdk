package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureMachineTypeTest {
  @Test
  public void correctValues_when_customEnum() {
    var valueToExtend = "This_Not_Exist_For_Sure";
    var enumValue = AzureMachineType.fromString(valueToExtend);
    
    assertThat(valueToExtend).isEqualTo(enumValue.toString());
  }

  @Test
  public void correctValues_when_Enum() {
    var existingEnum = "Basic_A0";
    var enumValue = AzureMachineType.fromString(existingEnum);

    assertThat(existingEnum).isEqualTo(enumValue.toString());
  }

  @Test
  public void correctValues_when_Values() {
    var initialValues = AzureMachineType.values();
    assertThat(initialValues.size()).isEqualTo(700);
    
    var newValue = AzureMachineType.fromString("This_Not_Exist_For_Sure");

    var valuesWithNewValue = AzureMachineType.values();
    assertThat(valuesWithNewValue).contains(newValue);
    assertThat(valuesWithNewValue.size()).isEqualTo(701);
  }
}
