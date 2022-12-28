package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.TestWithFixture;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CAAS_TRAEFIK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CaaSTraefikTest extends TestWithFixture {

  @Test
  public void exceptionThrown_when_traefikCreatedWithNullId() {
    assertThatThrownBy(() -> CaaSTraefik.builder().withId(""))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContainingAll("A valid component id cannot be null, empty or contain spaces");
  }

  @Test
  public void exceptionThrown_when_traefikCreatedWithNoNamespace() {
    var traefikBuilder = CaaSTraefik.builder()
      .withId(ComponentId.from("traefik"));

    assertThatThrownBy(traefikBuilder::build)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("[CaaSTraefik Validation] Namespace has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_traefikCreatedWithEmptyContainerPlatform() {
    var traefikBuilder = CaaSTraefik.builder()
      .withId(ComponentId.from("traefik"))
      .withNamespace("traefik")
      .withContainerPlatform("");

    assertThatThrownBy(traefikBuilder::build)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("[CaaSTraefik Validation] ContainerPlatform defined was either empty or blank and it is required");
  }

  @Test
  public void exceptionThrown_when_ambassadorCreatedWithNullEntryPoints() {
    var traefikBuilder = traefikBuilder().withEntryPoints(null);
    
    assertThatThrownBy(traefikBuilder::build)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("at least one entrypoint");
  }

  @Test
  public void exceptionThrown_when_ambassadorCreatedWithEmptyEntryPoints() {
    var traefikBuilder = traefikBuilder().withEntryPoints(new ArrayList<>());

    assertThatThrownBy(traefikBuilder::build)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("at least one entrypoint");
  }

  @Test
  public void exceptionThrown_when_ambassadorCreatedWithForwardAuthSettingsMissingClientId() {
    var traefikBuilder = traefikBuilder()
      .withForwardAuth(new ForwardAuthSettings(
        null,
        a(String.class),
        a(String.class),
        a(String.class)));

    assertThatThrownBy(traefikBuilder::build)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("OIDC has been partially configured");
  }

  @Test
  public void exceptionThrown_when_ambassadorCreatedWithForwardAuthSettingsMissingClientSecretId() {
    var traefikBuilder = traefikBuilder()
      .withForwardAuth(new ForwardAuthSettings(
        a(String.class),
        null,
        a(String.class),
        a(String.class)));

    assertThatThrownBy(traefikBuilder::build)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("OIDC has been partially configured");
  }

  @Test
  public void exceptionThrown_when_ambassadorCreatedWithForwardAuthSettingsForwardAuthSecret() {
    var traefikBuilder = traefikBuilder()
      .withForwardAuth(new ForwardAuthSettings(
        a(String.class),
        a(String.class),
        null,
        a(String.class)));

    assertThatThrownBy(traefikBuilder::build)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("OIDC has been partially configured");
  }

  @Test
  public void exceptionThrown_when_ambassadorCreatedWithForwardAuthSettingsIssuer() {
    var traefikBuilder = traefikBuilder()
      .withForwardAuth(new ForwardAuthSettings(
        a(String.class),
        a(String.class),
        a(String.class),
        null));

    assertThatThrownBy(traefikBuilder::build)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("OIDC has been partially configured");
  }


  @Test
  public void noValidationErrors_when_traefikHasRequiredFields() {
    assertThat(traefikBuilder().build().validate()).isEmpty();
  }

  @Test
  public void noValidationErrors_when_traefikHasRequiredFieldsWithForwardAuth() {
    assertThat(traefikBuilderWithForwardAuth().build().validate()).isEmpty();
  }

  @Test
  public void typeIsTraefik_when_traefikIsBuilt() {
    var ambassador = traefikBuilder().build();
    assertThat(ambassador.getType()).isEqualTo(CAAS_TRAEFIK);
  }

  private CaaSTraefik.TraefikBuilder traefikBuilder() {
    return CaaSTraefik.builder()
      .withId("traefik")
      .withNamespace(a(String.class))
      .withHostname(a(String.class))
      .withEntryPoints(aListOf(TraefikEntryPoint.class));
  }

  private CaaSTraefik.TraefikBuilder traefikBuilderWithForwardAuth() {
    return traefikBuilder()
      .withForwardAuth(new ForwardAuthSettings(
        a(String.class),
        a(String.class),
        a(String.class),
        a(String.class)));
  }

}