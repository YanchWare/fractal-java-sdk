package com.yanchware.fractal.sdk.domain.environment;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidAlphanumericsHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;

/**
 * <pre>
 * Represents a secret that can be associated with an environment.
 * Secrets are used to store sensitive information, such as passwords or API keys.
 *
 * Secret names can only contain alphanumeric characters and hyphens, cannot start or end with a hyphen,
 * and must be between 1 and 127 characters long.
 *
 * Secret values cannot be empty or null.</pre>
 */
public record Secret(String name, String value) {
  private final static String NAME_NOT_VALID = "[Secret Validation] The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be between 1 and 127 characters";
  private final static String VALUE_NOT_VALID = "[Secret Validation] The value cannot be empty or null";

  /**
   * <pre>Creates a new {@code Secret} with the specified name and value.
   *
   * @param name  The name of the secret. Secret name can only contain alphanumeric characters and hyphens,
   *              cannot start or end with a hyphen, and must be between 1 and 127 characters long.
   * @param value The value of the secret. Secret value cannot be empty or null.
   *
   * @throws IllegalArgumentException If the secret name or value is invalid.
   * </pre>
   */
  public Secret {
    Collection<String> errors = validate(name, value);

    if (!errors.isEmpty()) {
      throw new IllegalArgumentException(String.format(
          "Secret validation failed. Errors: %s",
          Arrays.toString(errors.toArray())));
    }
  }

  /**
   * <pre>Validates the secret name and value.
   *
   * @param name  The name of the secret to validate.
   * @param value The value of the secret to validate.
   *
   * @return A collection of error messages, or an empty collection if the secret is valid.
   * </pre>
   */
  private Collection<String> validate(String name, String value) {
    var errors = new ArrayList<String>();

    if (StringUtils.isBlank(name)) {
      errors.add(NAME_NOT_VALID);
    }

    if (!StringUtils.isBlank(name)) {
      var hasValidCharacters = isValidAlphanumericsHyphens(name);
      var hasValidLengths = isValidStringLength(name, 1, 127);
      if (!hasValidCharacters || !hasValidLengths) {
        errors.add(NAME_NOT_VALID);
      }
    }

    if (StringUtils.isBlank(value)) {
      errors.add(VALUE_NOT_VALID);
    }

    return errors;
  }
}