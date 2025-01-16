package com.yanchware.fractal.sdk.domain.environment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SecretTest {
  private final static String NAME_NOT_VALID_ERROR_STARTS_WITH = "[Secret Validation] The name only allow " +
    "alphanumeric characters and hyphens";
  private final static String VALUE_NOT_VALID_ERROR_STARTS_WITH = "[Secret Validation] The value cannot be empty or " +
    "null";

  @Test
  void exceptionThrown_when_nameIsEmpty() {
    assertThatThrownBy(() -> new Secret("", "secretValue"))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining(NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_nameIsNull() {
    assertThatThrownBy(() -> new Secret(null, "secretValue"))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining(NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_nameStartsWithHyphen() {
    assertThatThrownBy(() -> new Secret("-my-secret", "secretValue"))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining(NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_nameEndsWithHyphen() {
    assertThatThrownBy(() -> new Secret("my-secret-", "secretValue"))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining(NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_nameContainsInvalidCharacters() {
    assertThatThrownBy(() -> new Secret("my_secret%", "secretValue"))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining(NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_nameIsTooLong() {
    String longName = "a".repeat(128);
    assertThatThrownBy(() -> new Secret(longName, "secretValue"))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining(NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_valueIsEmpty() {
    assertThatThrownBy(() -> new Secret("my-secret", ""))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining(VALUE_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_valueIsNull() {
    assertThatThrownBy(() -> new Secret("my-secret", null))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining(VALUE_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_nameAndValueAreInvalid() {
    assertThatThrownBy(() -> new Secret("", null))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining(NAME_NOT_VALID_ERROR_STARTS_WITH)
      .hasMessageContaining(VALUE_NOT_VALID_ERROR_STARTS_WITH);
  }
}