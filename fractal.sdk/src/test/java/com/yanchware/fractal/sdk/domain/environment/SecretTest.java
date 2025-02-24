package com.yanchware.fractal.sdk.domain.environment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SecretTest {
  private static final String VALID_SHORT_NAME = "my-secret";
  private static final String VALID_DISPLAY_NAME = "My Secret";
  private static final String VALID_DESCRIPTION = "Test secret";
  private static final String VALID_VALUE = "secret-value";

  private static final String SHORT_NAME_NOT_VALID_ERROR_STARTS_WITH = "[Secret Validation] The Short Name only allow alphanumeric characters and hyphens";
  private static final String DISPLAY_NAME_NOT_VALID_ERROR_STARTS_WITH = "[Secret Validation] The Display Name cannot be empty or null";
  private static final String VALUE_NOT_VALID_ERROR_STARTS_WITH = "[Secret Validation] The value cannot be empty or null";


  @Test
  void exceptionThrown_when_shortNameIsEmpty() {
    assertThatThrownBy(() -> new Secret("", VALID_DISPLAY_NAME, VALID_VALUE))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(SHORT_NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_shortNameIsNull() {
    assertThatThrownBy(() -> new Secret(null, VALID_DISPLAY_NAME, VALID_VALUE))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(SHORT_NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_shortNameStartsWithHyphen() {
    assertThatThrownBy(() -> new Secret("-my-secret", VALID_DISPLAY_NAME, VALID_VALUE))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(SHORT_NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_shortNameEndsWithHyphen() {
    assertThatThrownBy(() -> new Secret("my-secret-", VALID_DISPLAY_NAME, VALID_VALUE))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(SHORT_NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_shortNameContainsInvalidCharacters() {
    assertThatThrownBy(() -> new Secret("my_secret%", VALID_DISPLAY_NAME, VALID_VALUE))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(SHORT_NAME_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_shortNameIsTooLong() {
    String longName = "a".repeat(128);
    assertThatThrownBy(() -> new Secret(longName, VALID_DISPLAY_NAME, VALID_VALUE))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(SHORT_NAME_NOT_VALID_ERROR_STARTS_WITH);
  }
  
  @Test
  void exceptionThrown_when_valueIsEmpty() {
    assertThatThrownBy(() -> new Secret(VALID_SHORT_NAME, VALID_DISPLAY_NAME,""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(VALUE_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_valueIsNull() {
    assertThatThrownBy(() -> new Secret(VALID_SHORT_NAME, VALID_DISPLAY_NAME,null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(VALUE_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void exceptionThrown_when_nameAndValueAreInvalid() {
    assertThatThrownBy(() -> new Secret(null, null, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining(SHORT_NAME_NOT_VALID_ERROR_STARTS_WITH)
            .hasMessageContaining(DISPLAY_NAME_NOT_VALID_ERROR_STARTS_WITH)
            .hasMessageContaining(VALUE_NOT_VALID_ERROR_STARTS_WITH);
  }

  @Test
  void noValidationErrors_when_secretIsValid() {
    Secret secret = new Secret(VALID_SHORT_NAME, VALID_DISPLAY_NAME, VALID_DESCRIPTION, VALID_VALUE);
    assertThat(secret).isNotNull();
    assertThat(secret.shortName()).isEqualTo(VALID_SHORT_NAME);
    assertThat(secret.displayName()).isEqualTo(VALID_DISPLAY_NAME);
    assertThat(secret.description()).isEqualTo(VALID_DESCRIPTION);
    assertThat(secret.value()).isEqualTo(VALID_VALUE);
  }

  @Test
  void noValidationErrors_when_shortNameIsMinimumLength() {
    Secret secret = new Secret("a", VALID_DISPLAY_NAME, VALID_DESCRIPTION, VALID_VALUE); // Minimum length (1)
    assertThat(secret).isNotNull();
  }

  @Test
  void noValidationErrors_when_shortNameIsMaximumLength() {
    String validShortName = "a".repeat(127); // Maximum length (127)
    Secret secret = new Secret(validShortName, VALID_DISPLAY_NAME, VALID_DESCRIPTION, VALID_VALUE);
    assertThat(secret).isNotNull();
  }

  @Test
  void noValidationErrors_when_descriptionIsNull() {
    Secret secret = new Secret(VALID_SHORT_NAME, VALID_DISPLAY_NAME, null, VALID_VALUE); // Null description
    assertThat(secret).isNotNull();
  }

  @Test
  void noValidationErrors_when_descriptionIsEmpty() {
    Secret secret = new Secret(VALID_SHORT_NAME, VALID_DISPLAY_NAME, "", VALID_VALUE); // Empty description
    assertThat(secret).isNotNull();
  }

  @Test
  void noValidationErrors_when_shortNameContainsHyphens() {
    Secret secret = new Secret("my-secret-name", VALID_DISPLAY_NAME, VALID_DESCRIPTION, VALID_VALUE);
    assertThat(secret).isNotNull();
  }

  @Test
  void noValidationErrors_when_shortNameIsMixedCase() {
    Secret secret = new Secret("MySecretName", VALID_DISPLAY_NAME, VALID_DESCRIPTION, VALID_VALUE);
    assertThat(secret).isNotNull();
  }

  @Test
  void noValidationErrors_when_displayNameContainsSpaces() {
    Secret secret = new Secret(VALID_SHORT_NAME, "My Secret Name", VALID_DESCRIPTION, VALID_VALUE);
    assertThat(secret).isNotNull();
  }

  @Test
  void noValidationErrors_when_descriptionContainsSpecialCharacters() {
    Secret secret = new Secret(VALID_SHORT_NAME, VALID_DISPLAY_NAME, "Test description with!@#$%^&*()", VALID_VALUE);
    assertThat(secret).isNotNull();
  }

  @Test
  void noValidationErrors_when_valueIsLong() {
    String longValue = "a".repeat(1000); // Or any other long value
    Secret secret = new Secret(VALID_SHORT_NAME, VALID_DISPLAY_NAME, VALID_DESCRIPTION, longValue);
    assertThat(secret).isNotNull();
  }
}