package com.yanchware.fractal.sdk.domain.environment;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidAlphanumericsHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;

/**
 * Represents a CI/CD profile with configuration details for continuous integration and continuous deployment.
 *
 * <p>A CI/CD profile includes:</p>
 * <ul>
 *   <li>Name: A unique name for the profile.</li>
 *   <li>SSH Private Key Data: The data of the SSH private key used for authentication.</li>
 *   <li>SSH Private Key Passphrase: (Optional) The passphrase for the SSH private key.</li>
 * </ul>
 *
 * @param name                    The name of the CI/CD profile.
 * @param sshPrivateKeyData       The SSH private key data.
 * @param sshPrivateKeyPassphrase The SSH private key passphrase (optional).
 */
public record CiCdProfile(String name, String sshPrivateKeyData, String sshPrivateKeyPassphrase) {
  private final static String NAME_NOT_VALID = "[CiCdProfile Validation] The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be between 1 and 127 characters";
  private final static String VALUE_NOT_VALID = "[CiCdProfile Validation] The SSH Private Key Data cannot be empty or null";

  /**
   * Creates a new CI/CD profile with the specified parameters.
   *
   * @throws IllegalArgumentException if the validation of the profile fails.
   */
  public CiCdProfile {
    Collection<String> errors = validate(name, sshPrivateKeyData);

    if (!errors.isEmpty()) {
      throw new IllegalArgumentException(String.format(
          "Secret validation failed. Errors: %s",
          Arrays.toString(errors.toArray())));
    }
  }

  /**
   * Validates the CI/CD profile to ensure that the name and SSH private key data are valid.
   *
   * @return A collection of error messages, or an empty collection if the profile is valid.
   */
  private Collection<String> validate(String name, String sshPrivateKeyData) {
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

    if (StringUtils.isBlank(sshPrivateKeyData)) {
      errors.add(VALUE_NOT_VALID);
    }

    return errors;
  }
}
