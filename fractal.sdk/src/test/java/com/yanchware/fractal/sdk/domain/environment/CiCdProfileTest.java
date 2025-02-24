package com.yanchware.fractal.sdk.domain.environment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CiCdProfileTest {

    private static final String VALID_SHORT_NAME = "my-profile";
    private static final String VALID_DISPLAY_NAME = "My Profile";
    private static final String VALID_SSH_KEY_DATA = "ssh-key-data";
    private static final String VALID_PASSPHRASE = "passphrase";
    
    @Test
    void exceptionThrown_when_shortNameIsEmpty() {
        assertThatThrownBy(() -> new CiCdProfile("", "My Profile", "","ssh-key-data", "passphrase"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The Short Name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be between 1 and 127 characters");
    }

    @Test
    void exceptionThrown_when_shortNameIsNull() {
        assertThatThrownBy(() -> new CiCdProfile(null, "My Profile", "ssh-key-data", "passphrase"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The Short Name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be between 1 and 127 characters");
    }

    @Test
    void exceptionThrown_when_shortNameStartsWithHyphen() {
        assertThatThrownBy(() -> new CiCdProfile("-my-profile", "My Profile", "ssh-key-data", "passphrase"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The Short Name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be between 1 and 127 characters");
    }

    @Test
    void exceptionThrown_when_shortNameEndsWithHyphen() {
        assertThatThrownBy(() -> new CiCdProfile("my-profile-", "My Profile", "ssh-key-data", "passphrase"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The Short Name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be between 1 and 127 characters");
    }

    @Test
    void exceptionThrown_when_shortNameContainsInvalidCharacters() {
        assertThatThrownBy(() -> new CiCdProfile("my_profile%", "My Profile", "ssh-key-data", "passphrase"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The Short Name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be between 1 and 127 characters");
    }

    @Test
    void exceptionThrown_when_shortNameIsTooLong() {
        String longName = "a".repeat(128);
        assertThatThrownBy(() -> new CiCdProfile(longName, "My Profile", "ssh-key-data", "passphrase"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The Short Name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be between 1 and 127 characters");
    }

    @Test
    void exceptionThrown_when_displayNameIsEmpty() {
        assertThatThrownBy(() -> new CiCdProfile("my-profile", "", "ssh-key-data", "passphrase"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The Display Name cannot be empty or null");
    }

    @Test
    void exceptionThrown_when_displayNameIsNull() {
        assertThatThrownBy(() -> new CiCdProfile("my-profile", null, "ssh-key-data", "passphrase"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The Display Name cannot be empty or null");
    }

    @Test
    void exceptionThrown_when_sshPrivateKeyDataIsEmpty() {
        assertThatThrownBy(() -> new CiCdProfile("my-profile", "My Profile", "", "passphrase"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The SSH Private Key Data cannot be empty or null");
    }

    @Test
    void exceptionThrown_when_sshPrivateKeyDataIsNull() {
        assertThatThrownBy(() -> new CiCdProfile("my-profile", "My Profile", null, "passphrase"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The SSH Private Key Data cannot be empty or null");
    }

    @Test
    void noValidationErrors_when_ciCdProfileIsValid() {
        CiCdProfile profile = new CiCdProfile(VALID_SHORT_NAME, VALID_DISPLAY_NAME, VALID_SSH_KEY_DATA, VALID_PASSPHRASE);
        assertThat(profile).isNotNull();
        assertThat(profile.shortName()).isEqualTo(VALID_SHORT_NAME);
        assertThat(profile.displayName()).isEqualTo(VALID_DISPLAY_NAME);
        assertThat(profile.sshPrivateKeyData()).isEqualTo(VALID_SSH_KEY_DATA);
        assertThat(profile.sshPrivateKeyPassphrase()).isEqualTo(VALID_PASSPHRASE);
    }

    @Test
    void noValidationErrors_when_shortNameIsMinimumLength() {
        // Short name with 1 character (minimum length)
        var shortName = "a";
        CiCdProfile profile = new CiCdProfile(shortName, VALID_DISPLAY_NAME, VALID_SSH_KEY_DATA, VALID_PASSPHRASE);
        assertThat(profile).isNotNull();
        assertThat(profile.shortName()).isEqualTo(shortName);
    }

    @Test
    void noValidationErrors_when_shortNameIsMaximumLength() {
        // Short name with 127 characters (maximum length)
        String shortName = "a".repeat(127);
        CiCdProfile profile = new CiCdProfile(shortName, VALID_DISPLAY_NAME, VALID_SSH_KEY_DATA, VALID_PASSPHRASE);
        assertThat(profile).isNotNull();
        assertThat(profile.shortName()).isEqualTo(shortName);
    }

    @Test
    void noValidationErrors_when_descriptionIsNotNull() {
        String description = "This is a test description.";
        CiCdProfile profile = new CiCdProfile(VALID_SHORT_NAME, VALID_DISPLAY_NAME, description, VALID_SSH_KEY_DATA, VALID_PASSPHRASE);
        assertThat(profile).isNotNull();
        assertThat(profile.description()).isEqualTo(description);
    }

    @Test
    void noValidationErrors_when_passphraseIsNull() {
        // Passphrase can be null
        CiCdProfile profile = new CiCdProfile(VALID_SHORT_NAME, VALID_DISPLAY_NAME, VALID_SSH_KEY_DATA, null);
        assertThat(profile).isNotNull();
        assertThat(profile.sshPrivateKeyPassphrase()).isNull();
    }

    @Test
    void noValidationErrors_when_passphraseIsEmpty() {
        // Passphrase can be empty
        CiCdProfile profile = new CiCdProfile(VALID_SHORT_NAME, VALID_DISPLAY_NAME, VALID_SSH_KEY_DATA, "");
        assertThat(profile).isNotNull();
        assertThat(profile.sshPrivateKeyPassphrase()).isEmpty();
    }
}