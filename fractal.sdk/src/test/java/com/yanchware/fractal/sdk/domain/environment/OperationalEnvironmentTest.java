package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OperationalEnvironmentTest {
  @Test
  public void exceptionThrown_when_environmentCreatedWithNullShortName() {
    assertThatThrownBy(() -> generateBuilderWithInfo(null))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Environment ShortName has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_environmentCreatedWithEmptyShortName() {
    assertThatThrownBy(() -> generateBuilderWithInfo(""))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Environment ShortName has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_environmentCreatedWithBlankShortName() {
    assertThatThrownBy(() -> generateBuilderWithInfo("   "))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Environment ShortName has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_environmentCreatedWithUpercaseShortName() {
    assertThatThrownBy(() -> generateBuilderWithInfo("Production-001"))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Environment ShortName must only contain lowercase letters, numbers, and dashes.");
  }


  @Test
  public void exceptionThrown_when_environmentResourceGroupsNotDefined() {
    assertThatThrownBy(() -> OperationalEnvironment.builder()
      .withShortName("production-001")
      .withAzureSubscription(
        AzureRegion.AUSTRALIA_CENTRAL,
        UUID.randomUUID()).build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Environment ResourceGroups has not been defined and it is required");
  }

  @Test
  public void noValidationErrors_when_environmentCreateWithRequiredData() {
    var environment = OperationalEnvironment.builder()
      .withShortName("production-001")
      .withResourceGroup(UUID.randomUUID())
      .withAzureSubscription(AzureRegion.WEST_EUROPE, UUID.randomUUID())
      .build();

    assertThat(environment.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(environment);
    assertThat(jsonEnvironment).isNotBlank();
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithSecrets() {
    var environment = OperationalEnvironment.builder()
      .withShortName("production-001")
      .withResourceGroup(UUID.randomUUID())
      .withAzureSubscription(AzureRegion.WEST_EUROPE, UUID.randomUUID())
      .withSecret(new Secret("secret-1", "value-1"))
      .withSecret(new Secret("secret-2", "value-2"))
      .withSecrets(List.of(
        new Secret("secret-3", "value-3"),
        new Secret("secret-4", "value-4")
      ))
      .build();

    assertThat(environment.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(environment);
    assertThat(jsonEnvironment).isNotBlank();

    var secrets = environment.getSecrets();

    // Assert that the secrets has exactly 4 entries
    assertThat(secrets).hasSize(4);
  }

  private void generateBuilderWithInfo(String shortName) {
    OperationalEnvironment.builder()
      .withShortName(shortName)
      .withResourceGroup(UUID.randomUUID())
      .build();
  }
}