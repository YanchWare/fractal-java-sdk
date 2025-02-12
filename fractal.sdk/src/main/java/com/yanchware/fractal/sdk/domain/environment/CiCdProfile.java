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
 *   <li>ShortName: A unique name for the profile.</li>
 *   <li>DisplayName: A display name for the profile.</li>
 *   <li>SSH Private Key Data: The data of the SSH private key used for authentication.</li>
 *   <li>SSH Private Key Passphrase: (Optional) The passphrase for the SSH private key.</li>
 * </ul>
 *
 * @param shortName               The shortName of the CI/CD profile.
 * @param displayName             The displayName of the CI/CD profile.
 * @param sshPrivateKeyData       The SSH private key data.
 * @param sshPrivateKeyPassphrase The SSH private key passphrase (optional).
 */
public record CiCdProfile(String shortName, String displayName, String sshPrivateKeyData, String sshPrivateKeyPassphrase) {
  private final static String SHORT_NAME_NOT_VALID = "[CI/CD Profile Validation] The Short Name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be between 1 and 127 characters";
  private final static String DISPLAY_NAME_NOT_VALID = "[CI/CD Profile Validation] The Display Name cannot be empty or null";
  private final static String SSH_PRIVATE_KEY_DATA_NOT_VALID = "[CI/CD Profile Validation] The SSH Private Key Data cannot be empty or null";

  /**
   * Creates a new CI/CD profile with the specified parameters.
   *
   * @throws IllegalArgumentException if the validation of the profile fails.
   */
  public CiCdProfile {
    Collection<String> errors = validate(shortName, displayName, sshPrivateKeyData);

    if (!errors.isEmpty()) {
      throw new IllegalArgumentException(String.format(
          "CI/CD Profile validation failed. Errors: %s",
          Arrays.toString(errors.toArray())));
    }
  }

  /**
   * Validates the CI/CD profile to ensure that the name and SSH private key data are valid.
   *
   * @return A collection of error messages, or an empty collection if the profile is valid.
   */
  private Collection<String> validate(String shortName, String displayName, String sshPrivateKeyData) {
    var errors = new ArrayList<String>();

    if (StringUtils.isBlank(shortName)) {
      errors.add(SHORT_NAME_NOT_VALID);
    }

    if (!StringUtils.isBlank(shortName)) {
      var hasValidCharacters = isValidAlphanumericsHyphens(shortName);
      var hasValidLengths = isValidStringLength(shortName, 1, 127);
      if (!hasValidCharacters || !hasValidLengths) {
        errors.add(SHORT_NAME_NOT_VALID);
      }
    }

    if (StringUtils.isBlank(displayName)) {
      errors.add(DISPLAY_NAME_NOT_VALID);
    }

    if (StringUtils.isBlank(sshPrivateKeyData)) {
      errors.add(SSH_PRIVATE_KEY_DATA_NOT_VALID);
    }

    return errors;
  }
}
