package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class JaegerTest {
    @Test
    public void exceptionThrown_when_BuiltWithNullId() {
        assertThatThrownBy(() -> Jaeger.builder().withId("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Component Id is illegal");
    }

    @Test
    public void exceptionThrown_when_BuiltWithContainerPlatformBlank() {
        assertThatThrownBy(() -> Jaeger.builder().withId("jaeger").withContainerPlatform("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform defined was either empty or blank");
    }

    @Test
    public void exceptionThrown_when_BuiltWithContainerPlatformEmpty() {
        assertThatThrownBy(() -> Jaeger.builder().withId("jaeger").withContainerPlatform("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform defined was either empty or blank");
    }

    @Test
    public void exceptionThrown_when_BuiltWithEmptyValues() {
        assertThatThrownBy(() -> Jaeger.builder().withId("jaeger").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContainingAll(
                        "Namespace has not been defined and it is required");
    }

    @Test
    public void typeIsJaeger_when_WithAllRequiredValues() {
        assertThatCode(TestUtils::getJaegerExample).doesNotThrowAnyException();
        assertThat(getJaegerExample().getType()).isEqualTo(JAEGER);
    }
}
