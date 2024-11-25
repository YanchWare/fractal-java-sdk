package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.Validatable;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidAlphanumericsHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;

//@Getter
//@Setter(AccessLevel.PRIVATE)
//public class Secret implements Validatable {
//  private final static String NAME_NOT_VALID = "[Secret Validation] The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be between 1 and 127 characters";
//  private final static String VALUE_NOT_VALID = "[Secret Validation] The value cannot be empty or null";
//  private String name;
//  private String value;
//
//  public static SecretBuilder builder() {
//    return new SecretBuilder();
//  }
//
//  @Override
//  public Collection<String> validate() {
//    var errors = new ArrayList<String>();
//
//    if(StringUtils.isBlank(name)) {
//      errors.add(NAME_NOT_VALID);
//    }
//    
//    if (!StringUtils.isBlank(name)) {
//      var hasValidCharacters = isValidAlphanumericsHyphens(name);
//      var hasValidLengths = isValidStringLength(name, 1, 127);
//      if (!hasValidCharacters || !hasValidLengths) {
//        errors.add(NAME_NOT_VALID);
//      }
//    }
//
//    if(StringUtils.isBlank(value)) {
//      errors.add(VALUE_NOT_VALID);
//    }
//
//    return errors;
//  }
//
//  public static class SecretBuilder {
//    private final Secret secret;
//    private final SecretBuilder builder;
//
//    public SecretBuilder() {
//      this.secret = new Secret();
//      this.builder = this;
//    }
//
//    /**
//     * <pre>
//     * The name of the secret.
//     * Secret names can only contain alphanumeric characters and dashes.
//     * The value must be between 1 and 127 characters long
//     *
//     * Regex pattern: ^[a-z0-9]+$
//     * </pre>
//     */
//    public SecretBuilder withName(String name) {
//      secret.setName(name);
//      return builder;
//    }
//
//    public SecretBuilder withValue(String value) {
//      secret.setValue(value);
//      return builder;
//    }
//
//    public Secret build() {
//      Collection<String> errors = secret.validate();
//
//      if (!errors.isEmpty()) {
//        throw new IllegalArgumentException(String.format(
//            "Secret validation failed. Errors: %s",
//            Arrays.toString(errors.toArray())));
//      }
//
//      return secret;
//    }
//  }
//}


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
public record Secret(String name, String value) implements Validatable {
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
    Collection<String> errors = validate();

    if (!errors.isEmpty()) {
      throw new IllegalArgumentException(String.format(
          "Secret validation failed. Errors: %s",
          Arrays.toString(errors.toArray())));
    }
  }

  @Override
  public Collection<String> validate() {
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