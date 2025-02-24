package com.yanchware.fractal.sdk.domain.environment;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidAlphanumericsHyphens;

/**
 * <pre>
 * Represents a secret that can be associated with an environment.
 * Secrets are used to store sensitive information, such as passwords or API keys.
 *
 * Secret short names can only contain alphanumeric characters and hyphens, cannot start or end with a hyphen.
 *
 * Secret values cannot be empty or null.</pre>
 */
public record Secret(String shortName, String displayName, String description, String value) {
  private final static String SHORT_NAME_NOT_VALID = "[Secret Validation] The Short Name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen";
  private final static String DISPLAY_NAME_NOT_VALID = "[Secret Validation] The Display Name cannot be empty or null";
  private final static String VALUE_NOT_VALID = "[Secret Validation] The value cannot be empty or null";

  /**
   * <pre>Creates a new {@code Secret} with the specified short name, display name, and value.
   *
   * @param shortName    The short name of the secret, which must adhere to the following constraints:
   *                     <ul>
   *                       <li>Must not be empty or null.</li>
   *                       <li>Must not start or end with a hyphen.</li>
   *                       <li>Must only contain alphanumeric characters and hyphens.</li>
   *                     </ul>
   * @param displayName The display name of the secret, which must not be empty or null.
   * @param value       The value of the secret, which must not be empty or null.
   *
   * @throws IllegalArgumentException If the secret short name, display name, or value is invalid.
   * </pre>
   */
  public Secret(String shortName, String displayName, String value) {
    this(shortName, displayName, null, value);
  }

  /**
   * <pre>Creates a new {@code Secret} with the specified short name, display name, description, and value.
   *
   * @param shortName    The short name of the secret, which must adhere to the following constraints:
   *                     <ul>
   *                       <li>Must not be empty or null.</li>
   *                       <li>Must not start or end with a hyphen.</li>
   *                       <li>Must only contain alphanumeric characters and hyphens.</li>
   *                     </ul>
   * @param displayName The display name of the secret, which must not be empty or null.
   * @param description A description of the secret (optional).
   * @param value       The value of the secret, which must not be empty or null.
   *
   * @throws IllegalArgumentException If the secret short name, display name, or value is invalid.
   * </pre>
   */
  public Secret {
    Collection<String> errors = validate(shortName, displayName, value);

    if (!errors.isEmpty()) {
      throw new IllegalArgumentException(String.format(
          "Secret validation failed. Errors: %s",
          Arrays.toString(errors.toArray())));
    }
  }

  /**
   * <pre>Validates the secret name and value.
   *
   * @param shortName  The shortName of the secret to validate.
   * @param displayName  The displayName of the secret to validate.
   * @param value The value of the secret to validate.
   *
   * @return A collection of error messages, or an empty collection if the secret is valid.
   * </pre>
   */
  private Collection<String> validate(String shortName, String displayName, String value) {
    var errors = new ArrayList<String>();

    if (StringUtils.isBlank(shortName)) {
      errors.add(SHORT_NAME_NOT_VALID);
    }

    if (!StringUtils.isBlank(shortName)) {
      var hasValidCharacters = isValidAlphanumericsHyphens(shortName);
      if (!hasValidCharacters) {
        errors.add(SHORT_NAME_NOT_VALID);
      }
    }

    if (StringUtils.isBlank(displayName)) {
      errors.add(DISPLAY_NAME_NOT_VALID);
    }

    if (StringUtils.isBlank(value)) {
      errors.add(VALUE_NOT_VALID);
    }

    return errors;
  }
}