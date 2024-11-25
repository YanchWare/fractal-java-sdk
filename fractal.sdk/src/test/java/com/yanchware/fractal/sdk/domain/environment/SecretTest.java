package com.yanchware.fractal.sdk.domain.environment;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SecretTest {
  private final static String NAME_NOT_VALID_ERROR_STARTS_WITH = "[Secret Validation] The name only allow alphanumeric characters and hyphens";
  private final static String VALUE_NOT_VALID_ERROR_STARTS_WITH = "[Secret Validation] The value cannot be empty or null";
  
  @Test
  void exceptionThrown_when_nameIsEmpty() {
    assertThatThrownBy(() -> Secret.builder()
        .withName("")
        .withValue("secretValue")
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_nameIsNull() {
    assertThatThrownBy(() -> Secret.builder()
        .withName(null)
        .withValue("secretValue")
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_nameStartsWithHyphen() {
    assertThatThrownBy(() -> Secret.builder()
        .withName("-my-secret")
        .withValue("secretValue")
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_nameEndsWithHyphen() {
    assertThatThrownBy(() -> Secret.builder()
        .withName("my-secret-")
        .withValue("secretValue")
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_nameContainsInvalidCharacters() {
    assertThatThrownBy(() -> Secret.builder()
        .withName("my_secret%")
        .withValue("secretValue")
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_nameIsTooLong() {
    String longName = "a".repeat(128);
    assertThatThrownBy(() -> Secret.builder()
        .withName(longName)
        .withValue("secretValue")
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_valueIsEmpty() {
    assertThatThrownBy(() -> Secret.builder()
        .withName("my-secret")
        .withValue("")
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(VALUE_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_valueIsNull() {
    assertThatThrownBy(() -> Secret.builder()
        .withName("my-secret")
        .withValue(null)
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(VALUE_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_nameAndValueAreInvalid() {
    assertThatThrownBy(() -> Secret.builder()
        .withName("")
        .withValue(null)
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(NAME_NOT_VALID_ERROR_STARTS_WITH)
        .hasMessageContaining(VALUE_NOT_VALID_ERROR_STARTS_WITH);
  }
  
}