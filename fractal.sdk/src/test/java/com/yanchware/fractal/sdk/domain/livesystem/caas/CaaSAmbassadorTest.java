package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.values.ComponentId;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_AMBASSADOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CaaSAmbassadorTest {

  @Test
  public void exceptionThrown_when_ambassadorCreatedWithNullId() {
    assertThatThrownBy(() -> CaaSAmbassador.builder()
      .withId("")
      .build())
    .isInstanceOf(IllegalArgumentException.class)
    .hasMessageContainingAll("A valid component id cannot be null, empty or contain spaces");
  }

  @Test
  public void exceptionThrown_when_ambassadorCreatedWithNoNamespace() {
    assertThatThrownBy(() -> CaaSAmbassador.builder()
      .withId(ComponentId.from("ambassador"))
      .build())
    .isInstanceOf(IllegalArgumentException.class)
    .hasMessageContaining("[CaaSAmbassador Validation] Namespace has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_ambassadorCreatedWithEmptyContainerPlatform() {
    assertThatThrownBy(() -> CaaSAmbassador.builder().withId(ComponentId.from("ambassador"))
      .withNamespace("ambassador")
      .withContainerPlatform("")
      .build())
    .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("[CaaSAmbassador Validation] ContainerPlatform defined was either empty or blank and it is required");
  }

  @Test
  public void exceptionThrown_when_ambassadorCreatedWithEmptyLicense() {
    var ambassadorBuilder = ambassadorBuilder().withLicenseKey("");
    assertThatThrownBy(ambassadorBuilder::build).isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("License Key defined was either empty or blank");
  }
  
  @Test
  public void exceptionThrown_when_ambassadorCreatedWithEmptyHostOwnerEmail() {
    var ambassadorBuilder = ambassadorBuilder().withHostOwnerEmail("");
    assertThatThrownBy(ambassadorBuilder::build)
      .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Host Owner Email has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_ambassadorCreatedWithEmptyAcmeProviderAuthority() {
    var ambassadorBuilder = ambassadorBuilder().withAcmeProviderAuthority("");
    assertThatThrownBy(ambassadorBuilder::build)
      .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("[CaaSAmbassador Validation] Automated Certificate Management Environment (ACME) Provider Authority has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_ambassadorCreatedWithEmptyTlsSecret() {
    var ambassadorBuilder = ambassadorBuilder().withTlsSecretName("");
    assertThatThrownBy(ambassadorBuilder::build).isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("TLS Secret has not been defined and it is required");
  }

  @Test
  public void noValidationErrors_when_ambassadorHasRequiredFields() {
    assertThat(ambassadorBuilder().build().validate()).isEmpty();
  }

  @Test
  public void typeIsAmbassador_when_ambassadorIsBuilt() {
    var ambassador = ambassadorBuilder().build();
    assertThat(ambassador.getType()).isEqualTo(CAAS_AMBASSADOR);
  }

  private CaaSAmbassador.AmbassadorBuilder ambassadorBuilder() {
    return CaaSAmbassador.builder()
        .withId("ambassador")
        .withNamespace("ambassador")
        .withHost("host")
        .withHostOwnerEmail("email@email.com")
        .withAcmeProviderAuthority("authority")
        .withTlsSecretName("tls");
  }
}