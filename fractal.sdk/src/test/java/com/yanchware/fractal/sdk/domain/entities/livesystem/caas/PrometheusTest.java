package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PROMETHEUS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PrometheusTest {

  @Test
  public void exceptionThrown_when_prometheusCreatedWithNullId() {
    assertThatThrownBy(() -> Prometheus.builder().withId(""))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContainingAll("A valid component id cannot be null, empty or contain spaces");
  }

  @Test
  public void exceptionThrown_when_prometheusCreatedWithNoNamespace() {
    var prometheusBuilder = Prometheus.builder()
      .withId(ComponentId.from("prometheus"));

    assertThatThrownBy(prometheusBuilder::build)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("[CaaSMonitoring Validation] Namespace has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_prometheusCreatedWithEmptyContainerPlatform() {
    var prometheusBuilder = Prometheus.builder()
      .withId(ComponentId.from("prometheus"))
      .withNamespace("prometheus")
      .withContainerPlatform("");

    assertThatThrownBy(prometheusBuilder::build)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("[CaaSMonitoring Validation] ContainerPlatform defined was either empty or blank and it is required");
  }

  @Test
  public void exceptionThrown_when_ambassadorCreatedWithEmptyApiGatewayUrl() {
    var prometheusBuilder = prometheusBuilder().withApiGatewayUrl("");

    assertThatThrownBy(prometheusBuilder::build)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("API Gateway URL has not been defined and it is required");
  }

  @Test
  public void noValidationErrors_when_prometheusHasRequiredFields() {
    assertThat(prometheusBuilder().build().validate()).isEmpty();
  }

  @Test
  public void typeIsPrometheus_when_prometheusIsBuilt() {
    var ambassador = prometheusBuilder().build();
    assertThat(ambassador.getType()).isEqualTo(PROMETHEUS);
  }

  private Prometheus.PrometheusBuilder prometheusBuilder() {
    return Prometheus.builder()
        .withId("prometheus")
        .withNamespace("prometheus")
        .withApiGatewayUrl("apiGatewayUrl");
  }
}