package com.yanchware.fractal.sdk.domain.environment;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidAlphanumericsHyphens;

public record CiCdProfile(String shortName, String displayName, String description, String sshPrivateKeyData, String sshPrivateKeyPassphrase) {
  private final static String SHORT_NAME_NOT_VALID = "[CI/CD Profile Validation] The Short Name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen";
  private final static String DISPLAY_NAME_NOT_VALID = "[CI/CD Profile Validation] The Display Name cannot be empty or null";
  private final static String SSH_PRIVATE_KEY_DATA_NOT_VALID = "[CI/CD Profile Validation] The SSH Private Key Data cannot be empty or null";

  /**
   * Creates a new CI/CD profile with the specified parameters, without a description.
   *
   * <p>This constructor provides a concise way to create a CI/CD profile when a description is not needed.
   *
   * @param shortName               The short name of the CI/CD profile, which must adhere to the following constraints:
   *                                <ul>
   *                                  <li>Must not be empty or null.</li>
   *                                  <li>Must not start or end with a hyphen.</li>
   *                                  <li>Must only contain alphanumeric characters and hyphens.</li>
   *                                </ul>
   * @param displayName             The display name of the CI/CD profile.
   * @param sshPrivateKeyData       The SSH private key data.
   * @param sshPrivateKeyPassphrase The SSH private key passphrase.
   * @throws IllegalArgumentException if the validation of the profile fails.
   */
  public CiCdProfile(String shortName, String displayName, String sshPrivateKeyData, String sshPrivateKeyPassphrase) {
    this(shortName, displayName, null, sshPrivateKeyData, sshPrivateKeyPassphrase);
  }

  /**
   * Creates a new CI/CD profile with the specified parameters.
   *
   * <p>This constructor allows for the creation of a CI/CD profile with description.
   * If a description is not needed, you can use the constructor overload that omits the {@code description} parameter.</p>
   *
   * @param shortName               The short name of the CI/CD profile, which must adhere to the following constraints:
   *                                <ul>
   *                                  <li>Must not be empty or null.</li>
   *                                  <li>Must not start or end with a hyphen.</li>
   *                                  <li>Must only contain alphanumeric characters and hyphens.</li>
   *                                </ul>
   * @param displayName             The display name of the CI/CD profile, which must not be empty or null.
   * @param description             A description of the profile.
   * @param sshPrivateKeyData       The SSH private key data, which must not be empty or null.
   * @param sshPrivateKeyPassphrase The SSH private key passphrase.
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
      if (!hasValidCharacters) {
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
