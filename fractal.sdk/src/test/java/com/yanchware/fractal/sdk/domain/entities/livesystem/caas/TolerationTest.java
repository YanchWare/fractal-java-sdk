package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class TolerationTest {

  @Test
  void tolerationIsCorrectlyCreated_when_AllFieldsAreValid() {
    String key = "example-key";
    TolerationOperator operator = TolerationOperator.EQUAL;
    String value = "example-value";
    TaintEffect effect = TaintEffect.NO_SCHEDULE;

    Toleration toleration = Toleration.builder()
        .withKey(key)
        .withOperator(operator)
        .withValue(value)
        .withEffect(effect)
        .build();

    assertThat(toleration).isNotNull();
    assertThat(toleration.getKey()).isEqualTo(key);
    assertThat(toleration.getOperator()).isEqualTo(operator);
    assertThat(toleration.getValue()).isEqualTo(value);
    assertThat(toleration.getEffect()).isEqualTo(effect);
  }

  @Test
  void exceptionThrown_when_KeyIsMissing() {
    Throwable thrown = catchThrowable(() -> Toleration.builder()
        .withOperator(TolerationOperator.EQUAL)
        .withValue("example-value")
        .withEffect(TaintEffect.NO_SCHEDULE)
        .build());

    assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("The key must be provided for a toleration");
  }

  @Test
  void exceptionThrown_when_EffectIsMissing() {
    Throwable thrown = catchThrowable(() -> Toleration.builder()
        .withKey("example-key")
        .withOperator(TolerationOperator.EQUAL)
        .withValue("example-value")
        .build());

    assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("The effect must be provided for a toleration");
  }

  @Test
  void illegalStateExceptionThrown_when_EqualOperatorUsedWithoutValue() {
    Throwable thrown = catchThrowable(() -> Toleration.builder()
        .withKey("example-key")
        .withOperator(TolerationOperator.EQUAL)
        .withEffect(TaintEffect.NO_SCHEDULE)
        .build());

    assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("A value must be provided when the operator is 'Equal'");
  }
}