package com.yanchware.fractal.sdk.domain.entities.livesystem.saas;

import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.SAAS_UNMANAGED_SECURITY;
import static org.assertj.core.api.Assertions.*;

class UnmanagedSecurityComponentTest {
  @Test
  public void exceptionThrown_when_componentBuiltWithNullId() {
    assertThatThrownBy(() -> UnmanagedSecurityComponent.builder().withId("").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Component Id is illegal");
  }

  @Test
  public void exceptionThrown_when_componentBuiltWithEmptyValues() {
    assertThatThrownBy(() -> UnmanagedSecurityComponent.builder().withId("external-security").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Secret Value has not been defined and it is required");
  }

  @Test
  public void typeIsExternalSecurityComponent_when_BuiltWithAllRequiredValues() {
    var secretValue = "aSecretValue";
    var builder = UnmanagedSecurityComponent.builder()
        .withId("external-security")
        .withSecretValue(secretValue);
    assertThatCode(builder::build).doesNotThrowAnyException();
    assertThat(builder.build().getType()).isEqualTo(SAAS_UNMANAGED_SECURITY);
    assertThat(builder.build().getSecretName()).isBlank();
    assertThat(builder.build().getSecretValue()).isEqualTo(secretValue);
  }

  @Test
  public void secretName_IsNotEmpty_when_BuiltWithAllRequiredValues() {
    var secretName = "aSecretName";
    var secretValue = "aSecretValue";
    var builder = UnmanagedSecurityComponent.builder()
        .withId("external-security")
        .withSecretName(secretName)
        .withSecretValue(secretValue);
    
    assertThatCode(builder::build).doesNotThrowAnyException();
    assertThat(builder.build().getType()).isEqualTo(SAAS_UNMANAGED_SECURITY);
    assertThat(builder.build().getSecretName()).isNotBlank();
    assertThat(builder.build().getSecretName()).isEqualTo(secretName);
    assertThat(builder.build().getSecretValue()).isEqualTo(secretValue);
  }
}