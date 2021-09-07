package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.aggregates.Environment;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EnvironmentTest {

    @Test
    public void exceptionThrown_when_environmentCreatedWithNullId() {
        assertThatThrownBy(() -> generateBuilderWithInfo(null).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Environment id has not been defined");
    }

    @Test
    public void exceptionThrown_when_environmentCreatedWithEmptyId() {
        assertThatThrownBy(() -> generateBuilderWithInfo("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Environment id has not been defined");
    }

    @Test
    public void exceptionThrown_when_environmentCreatedWithBlankId() {
        assertThatThrownBy(() -> generateBuilderWithInfo("   ").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Environment id has not been defined");
    }

    @Test
    public void noValidationErrors_when_environmentCreatedWithValidId() {
        var env = generateBuilderWithInfo("production-001").build();
        assertThat(env.validate()).isEmpty();
    }

    private Environment.EnvironmentBuilder generateBuilderWithInfo(String id) {
        return Environment.builder()
                .id(id)
                .displayName("PROD")
                .parentId("123456789")
                .parentType("folder");
    }

}