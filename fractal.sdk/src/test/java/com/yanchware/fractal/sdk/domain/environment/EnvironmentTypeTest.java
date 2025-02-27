package com.yanchware.fractal.sdk.domain.environment;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EnvironmentTypeTest {
    @Test
    void should_returnPersonal_when_inputIsPersonal() {
        assertThat(EnvironmentType.fromString("Personal")).isEqualTo(EnvironmentType.PERSONAL);
    }

    @Test
    void should_returnPersonal_when_inputIsPersonalIgnoreCase() {
        assertThat(EnvironmentType.fromString("personal")).isEqualTo(EnvironmentType.PERSONAL);
    }

    @Test
    void should_returnOrganizational_when_inputIsOrganizational() {
        assertThat(EnvironmentType.fromString("Organizational")).isEqualTo(EnvironmentType.ORGANIZATIONAL);
    }

    @Test
    void should_returnOrganizational_when_inputIsOrganizationalIgnoreCase() {
        assertThat(EnvironmentType.fromString("organizational")).isEqualTo(EnvironmentType.ORGANIZATIONAL);
    }

    @Test
    void should_throwException_when_inputIsNull() {
        assertThatThrownBy(() -> EnvironmentType.fromString(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Environment type cannot be null or blank");
    }

    @Test
    void should_throwException_when_inputIsEmpty() {
        assertThatThrownBy(() -> EnvironmentType.fromString(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Environment type cannot be null or blank");
    }

    @Test
    void should_throwException_when_inputIsBlank() {
        assertThatThrownBy(() -> EnvironmentType.fromString("  "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Environment type cannot be null or blank");
    }

    @Test
    void should_throwException_when_inputIsInvalid() {
        assertThatThrownBy(() -> EnvironmentType.fromString("InvalidType"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid environment type: InvalidType");
    }
}