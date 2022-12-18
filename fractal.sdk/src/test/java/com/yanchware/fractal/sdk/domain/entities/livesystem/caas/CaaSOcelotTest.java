package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CAAS_OCELOT;
import static org.assertj.core.api.Assertions.*;

class CaaSOcelotTest {
    @Test
    public void exceptionThrown_when_ocelotBuiltWithNullId() {
        assertThatThrownBy(() -> CaaSOcelot.builder().withId("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Component Id is illegal");
    }

    @Test
    public void exceptionThrown_when_ocelotBuiltWithContainerPlatformBlank() {
        assertThatThrownBy(() -> CaaSOcelot.builder().withId("ocelot").withContainerPlatform("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform defined was either empty or blank");
    }

    @Test
    public void exceptionThrown_when_ocelotBuiltWithContainerPlatformEmpty() {
        assertThatThrownBy(() -> CaaSOcelot.builder().withId("ocelot").withContainerPlatform("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform defined was either empty or blank");
    }

    @Test
    public void exceptionThrown_when_ocelotBuiltWithEmptyValues() {
        assertThatThrownBy(() -> CaaSOcelot.builder().withId("ocelot").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContainingAll(
                        "Namespace has not been defined and it is required",
                        "Host has not been defined and it is required",
                        "Host Owner Email has not been defined and it is required",
                        "Cookie max age has not been defined or is less");
    }

    @Test
    public void typeIsOcelot_when_BuiltWithAllRequiredValues() {
        var builder = CaaSOcelot.builder()
                .withId("ocelot")
                .withNamespace("security")
                .withHost("api.fractal-arch.org")
                .withHostOwnerEmail("hello@fractal-arch.org")
                .withCookieMaxAgeSec(3600)
                .withCorsOrigins("https://fractal-arch.org,localhost:8080")
                .withPathPrefix("/api/*");
        assertThatCode(builder::build).doesNotThrowAnyException();
        assertThat(builder.build().getType()).isEqualTo(CAAS_OCELOT);
        assertThat(builder.build().getCorsOrigins()).hasSize(2);
        assertThat(builder.build().getCorsOrigins()).containsExactlyInAnyOrder("https://fractal-arch.org","localhost:8080");
    }
}