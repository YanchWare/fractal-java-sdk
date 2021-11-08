package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.OCELOT;
import static org.assertj.core.api.Assertions.*;

class OcelotTest {
    @Test
    public void exceptionThrown_when_ocelotBuiltWithNullId() {
        assertThatThrownBy(() -> Ocelot.builder().withId("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Component Id is illegal");
    }

    @Test
    public void exceptionThrown_when_ocelotBuiltWithContainerPlatformBlank() {
        assertThatThrownBy(() -> Ocelot.builder().withId("ocelot").withContainerPlatform("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform defined was either empty or blank");
    }

    @Test
    public void exceptionThrown_when_ocelotBuiltWithContainerPlatformEmpty() {
        assertThatThrownBy(() -> Ocelot.builder().withId("ocelot").withContainerPlatform("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform defined was either empty or blank");
    }

    @Test
    public void exceptionThrown_when_ocelotBuiltWithEmptyValues() {
        assertThatThrownBy(() -> Ocelot.builder().withId("ocelot").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContainingAll(
                        "Namespace has not been defined and it is required",
                        "Host has not been defined and it is required",
                        "Host Owner Email has not been defined and it is required",
                        "Cookie max age has not been defined or is less");
    }

    @Test
    public void typeIsKubernetes_when_svcBuiltWithAllRequiredValues() {
        var builder = Ocelot.builder()
                .withId("ocelot")
                .withNamespace("security")
                .withHost("api.fractal-arch.org")
                .withHostOwnerEmail("hello@fractal-arch.org")
                .withCookieMaxAgeSec(3600)
                .withCorsOrigins("https://fractal-arch.org")
                .withPathPrefix("/api/*");
        assertThat(builder.build().getType()).isEqualTo(OCELOT);
        assertThatCode(builder::build).doesNotThrowAnyException();
    }
}