package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

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

  private void generateBuilderWithInfo(String shortName) {
    OperationalEnvironment.builder()
        .withShortName(shortName)
        .withResourceGroup(UUID.randomUUID())
        .build();
  }
}