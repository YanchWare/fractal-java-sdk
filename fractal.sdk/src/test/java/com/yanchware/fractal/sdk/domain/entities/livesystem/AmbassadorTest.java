package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.AMBASSADOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AmbassadorTest {

    @Test
    public void exceptionThrown_when_ambassadorCreatedWithNullId() {
        assertThatThrownBy(() -> Ambassador.builder().withId("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll("A valid component id cannot be null, empty or contain spaces");
    }

    @Test
    public void exceptionThrown_when_ambassadorCreatedWithNoNamespace() {
        assertThatThrownBy(() -> Ambassador.builder().withId(ComponentId.from("ambassador")).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("[Ambassador Validation] Namespace has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_ambassadorCreatedWithEmptyContainerPlatform() {
        assertThatThrownBy(() -> Ambassador.builder().withId(ComponentId.from("ambassador")).withNamespace("ambassador").withContainerPlatform("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("[Ambassador Validation] ContainerPlatform defined was either empty or blank and it is required");
    }

    @Test
    public void noValidationErrors_when_ambassadorHasRequiredFields() {
        assertThat(Ambassador.builder().withId(ComponentId.from("ambassador")).withNamespace("ambassador").build().validate()).isEmpty();
    }

    @Test
    public void typeIsAmbassador_when_ambassadorIsBuilt() {
        var ambassador = Ambassador.builder().withId(ComponentId.from("ambassador")).withNamespace("ambassador").build();
        assertThat(ambassador.getType()).isEqualTo(AMBASSADOR);
    }
}