
package com.yanchware.fractal.sdk.domain.livesystem.saas;

import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.SAAS_UNMANAGED_STORAGE;
import static org.assertj.core.api.Assertions.*;

class UnmanagedStorageAccountTest {
  @Test
  public void exceptionThrown_when_componentBuiltWithNullId() {
    assertThatThrownBy(() -> UnmanagedStorageComponent.builder().withId("").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Component Id is illegal");
  }

  @Test
  public void exceptionThrown_when_componentBuiltWithEmptyValues() {
    assertThatThrownBy(() -> UnmanagedStorageComponent.builder().withId("unmanaged-storage").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Secret Value has not been defined and it is required");
  }

  @Test
  public void typeIsUnmanagedStorageComponent_when_BuiltWithAllRequiredValues() {
    var secretValue = "aSecretValue";
    var builder = UnmanagedStorageComponent.builder()
        .withId("unmanaged-storage")
        .withSecretValue(secretValue);
    assertThatCode(builder::build).doesNotThrowAnyException();
    assertThat(builder.build().getType()).isEqualTo(SAAS_UNMANAGED_STORAGE);
    assertThat(builder.build().getSecretName()).isBlank();
    assertThat(builder.build().getSecretValue()).isEqualTo(secretValue);
  }

  @Test
  public void secretName_IsNotEmpty_when_componentBuiltWithSecretName() {
    var secretName = "aSecretName";
    var secretValue = "aSecretValue";
    var builder = UnmanagedStorageComponent.builder()
        .withId("unmanaged-storage")
        .withSecretName(secretName)
        .withSecretValue(secretValue);
    
    assertThatCode(builder::build).doesNotThrowAnyException();
    assertThat(builder.build().getType()).isEqualTo(SAAS_UNMANAGED_STORAGE);
    assertThat(builder.build().getSecretName()).isNotBlank();
    assertThat(builder.build().getSecretName()).isEqualTo(secretName);
    assertThat(builder.build().getSecretValue()).isEqualTo(secretValue);
  }

  @Test
  public void json_IsNotEmpty_when_componentBuilt() {
    var secretName = "aSecretName";
    var secretValue = "aSecretValue";
    var builder = UnmanagedStorageComponent.builder()
        .withId("unmanaged-storage")
        .withSecretName(secretName)
        .withSecretValue(secretValue);

    var json = TestUtils.getJsonRepresentation(builder.build());
    
    assertThatCode(builder::build).doesNotThrowAnyException();
    assertThat(builder.build().getType()).isEqualTo(SAAS_UNMANAGED_STORAGE);
    assertThat(json).isNotBlank();
  }
}