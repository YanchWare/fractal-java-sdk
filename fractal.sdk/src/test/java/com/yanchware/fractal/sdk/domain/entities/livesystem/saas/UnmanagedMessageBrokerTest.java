
package com.yanchware.fractal.sdk.domain.entities.livesystem.saas;

import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.SAAS_UNMANAGED_BROKER;
import static org.assertj.core.api.Assertions.*;

class UnmanagedMessageBrokerTest {
    @Test
    public void exceptionThrown_when_componentBuiltWithNullId() {
        assertThatThrownBy(() -> UnmanagedBrokerComponent.builder().withId("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Component Id is illegal");
    }

    @Test
    public void exceptionThrown_when_componentBuiltWithEmptyValues() {
        assertThatThrownBy(() -> UnmanagedBrokerComponent.builder().withId("unmanaged-storage").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Secret Value has not been defined and it is required");
    }

    @Test
    public void typeIsUnmanagedBrokerComponent_when_BuiltWithAllRequiredValues() {
        var secretValue = "aSecretValue";
        var builder = UnmanagedBrokerComponent.builder()
                .withId("unmanaged-storage")
                .withSecretValue(secretValue);
        assertThatCode(builder::build).doesNotThrowAnyException();
        assertThat(builder.build().getType()).isEqualTo(SAAS_UNMANAGED_BROKER);
        assertThat(builder.build().getSecretValue()).isEqualTo(secretValue);
    }
}