
package com.yanchware.fractal.sdk.domain.entities.livesystem.saas;

import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.SAAS_UNMANAGED_STORAGE;
import static org.assertj.core.api.Assertions.*;

class UnmanagedStorageAccountTest {
    @Test
    public void exceptionThrown_when_componentBuiltWithNullId() {
        assertThatThrownBy(() -> UnmanagedStorageComponent.builder().withId("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Component Id is illegal");
    }

    @Test
    public void exceptionThrown_when_componentBuiltWithEmptyName() {
        assertThatThrownBy(() -> UnmanagedStorageComponent.builder()
            .withId("unmanaged-storage")
            .withSecretValue("secret")
            .build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Secret Name has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_componentBuiltWithEmptyValues() {
        assertThatThrownBy(() -> UnmanagedStorageComponent.builder().withId("unmanaged-storage").build()).
            isInstanceOf(IllegalArgumentException.class).
            hasMessageContaining("Secret Value has not been defined and it is required");
    }

    @Test
    public void typeIsUnmanagedStorageComponent_when_BuiltWithAllRequiredValues() {
        var secretName = "aSecretName";
        var secretValue = "aSecretValue";
        var builder = UnmanagedStorageComponent.builder()
                .withId("unmanaged-storage")
                .withSecretName(secretName)
                .withSecretValue(secretValue);
        assertThatCode(builder::build).doesNotThrowAnyException();
        assertThat(builder.build().getType()).isEqualTo(SAAS_UNMANAGED_STORAGE);
        assertThat(builder.build().getSecretName()).isEqualTo(secretName);
        assertThat(builder.build().getSecretValue()).isEqualTo(secretValue);
    }
}