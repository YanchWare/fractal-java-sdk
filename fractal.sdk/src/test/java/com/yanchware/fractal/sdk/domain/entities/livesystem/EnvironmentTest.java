package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.aggregates.Environment;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EnvironmentTest {

    @Test
    public void exceptionThrown_when_environmentCreatedWithNullId() {
        assertThatThrownBy(() -> generateBuilderWithInfo(null)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Environment id has not been defined");
    }

    @Test
    public void exceptionThrown_when_environmentCreatedWithEmptyId() {
        assertThatThrownBy(() -> generateBuilderWithInfo("")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Environment id has not been defined");
    }

    @Test
    public void exceptionThrown_when_environmentCreatedWithBlankId() {
        assertThatThrownBy(() -> generateBuilderWithInfo("   ")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Environment id has not been defined");
    }

    @Test
    public void noValidationErrors_when_environmentCreatedWithValidId() {
        var env = generateBuilderWithInfo("production-001");
        assertThat(env.validate()).isEmpty();
    }

    private Environment generateBuilderWithInfo(String id) {
        return Environment.builder()
                .withId(id)
                .withDisplayName("PROD")
                .withParentId("123456789")
                .withParentType("folder")
                .build();
    }

}