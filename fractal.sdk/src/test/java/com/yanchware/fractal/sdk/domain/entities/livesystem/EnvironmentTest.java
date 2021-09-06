package com.yanchware.fractal.sdk.domain.entities.livesystem;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EnvironmentTest {

    @Test
    public void when_EnvironmentCreatedWithNullId_ExceptionThrown() {
        assertThatThrownBy(() -> generateBuilderWithInfo().build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Environment id has not been defined");
    }

    @Test
    public void when_EnvironmentCreatedWithEmptyId_ExceptionThrown() {
        assertThatThrownBy(() -> generateBuilderWithInfo().build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Environment id has not been defined");
    }

    @Test
    public void when_EnvironmentCreatedWithBlankId_ExceptionThrown() {
        assertThatThrownBy(() -> generateBuilderWithInfo().build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Environment id has not been defined");
    }

    @Test
    public void when_EnvironmentCreatedWithValidId_NoValidationErrors() {
        var env = generateBuilderWithInfo().id("production-001").build();
        assertThat(env.validate()).isEmpty();
    }

    private Environment.EnvironmentBuilder generateBuilderWithInfo() {
        return Environment.builder()
                .id("    ")
                .displayName("PROD")
                .parentId("123456789")
                .parentType("folder");
    }

}