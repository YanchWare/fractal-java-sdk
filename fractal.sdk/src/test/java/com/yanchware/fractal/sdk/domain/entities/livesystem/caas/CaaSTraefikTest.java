package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.jayway.jsonpath.JsonPath;
import com.yanchware.fractal.sdk.TestWithFixture;
import com.yanchware.fractal.sdk.utils.TestUtils;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    var traefikBuilder = traefikBuilderWithoutEntryPoints().withEntryPoints(null);

    assertThatThrownBy(traefikBuilder::build)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("at least one entrypoint");
  }

  @Test
  public void exceptionThrown_when_ambassadorCreatedWithEmptyEntryPoints() {
    var traefikBuilder = traefikBuilderWithoutEntryPoints()
        .withEntryPoints(new ArrayList<>());

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

  @Test
  public void exceptionThrown_when_traefikCreatedWithTracingJaegerNull() {
    assertThatThrownBy(() -> traefikBuilder()
        .withTracing(
            TraefikTracing.builder()
                .withJaeger(null)
                .build())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContainingAll("JaegerTracing instance cannot be null");
  }

  @Test
  public void exceptionThrown_when_traefikCreatedWithTracingJaegerCollectorEndpointAndLocalAgentPort() {
    assertThatThrownBy(() -> traefikBuilder()
        .withTracing(
            TraefikTracing.builder()
                .withJaeger(JaegerTracing.builder()
                    .withCollectorEndpoint("https://local.host.com:5778/sampling")
                    .withLocalAgentPort(5778)
                    .build())
                .build())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContainingAll("localAgentPort should not be provided when collectorEndpoint is provided");
  }

  @Test
  public void exceptionThrown_when_traefikCreatedWithTracingJaegerCollectorEndpointAndLocalAgentUrlPath() {
    assertThatThrownBy(() -> traefikBuilder()
        .withTracing(
            TraefikTracing.builder()
                .withJaeger(JaegerTracing.builder()
                    .withCollectorEndpoint("https://local.host.com:5778/sampling")
                    .withLocalAgentUrlPath("/sampling")
                    .build())
                .build())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContainingAll("localAgentUrlPath should not be provided when collectorEndpoint is provided");
  }


  @Test
  public void exceptionThrown_when_traefikCreatedWithTracingJaegerLocalAgentPortOnly() {
    assertThatThrownBy(() -> traefikBuilder()
        .withTracing(
            TraefikTracing.builder()
                .withJaeger(JaegerTracing.builder()
                    .withLocalAgentPort(5778)
                    .build())
                .build())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContainingAll("localAgentUrlPath must be defined");
  }

  @Test
  public void exceptionThrown_when_traefikCreatedWithTracingJaegerLocalAgentUrlPathOnly() {
    assertThatThrownBy(() -> traefikBuilder()
        .withTracing(
            TraefikTracing.builder()
                .withJaeger(JaegerTracing.builder()
                    .withLocalAgentUrlPath("/sampling")
                    .build())
                .build())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContainingAll("localAgentPort must be defined");
  }

  @Test
  public void exceptionThrown_when_traefikCreatedWithTracingNegativeJaegerLocalAgentPort() {
    assertThatThrownBy(() -> traefikBuilder()
        .withTracing(
            TraefikTracing.builder()
                .withJaeger(JaegerTracing.builder()
                    .withLocalAgentUrlPath("/sampling")
                    .withLocalAgentPort(-1)
                    .build())
                .build())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContainingAll("localAgentPort must be between 1 and 65535");
  }

  @Test
  public void exceptionThrown_when_traefikCreatedWithTracingJaegerLocalAgentPort_ToHigh() {
    assertThatThrownBy(() -> traefikBuilder()
        .withTracing(
            TraefikTracing.builder()
                .withJaeger(JaegerTracing.builder()
                    .withLocalAgentUrlPath("/sampling")
                    .withLocalAgentPort(65536)
                    .build())
                .build())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContainingAll("localAgentPort must be between 1 and 65535");
  }

  @Test
  public void noValidationErrors_when_traefikBuilderHasRequiredFields() {
    var localAgentPort = 5000;
    var localAgentUrlPath = "/sampling";

    var traefik = traefikBuilder()
        .withTracing(
            TraefikTracing.builder()
                .withJaeger(JaegerTracing.builder()
                    .withLocalAgentUrlPath(localAgentUrlPath)
                    .withLocalAgentPort(localAgentPort)
                    .build())
                .build())
        .withResourceManagement(ResourceManagement.builder()
            .withLimits(ContainerResources.builder()
                .withCpu(a(Integer.class).toString())
                .withMemory(a(Integer.class).toString())
                .build())
            .build())
        .withNodeSelectors(Map.of(
            a(String.class), a(String.class),
            a(String.class), a(String.class)))
        .withToleration(Toleration.builder()
            .withKey(a(String.class))
            .withOperator(TolerationOperator.EQUAL)
            .withValue(a(String.class))
            .withEffect(TaintEffect.NO_EXECUTE)
            .build())
        .withTolerations(List.of(
            Toleration.builder()
                .withKey(a(String.class))
                .withOperator(TolerationOperator.EXISTS)
                .withValue(a(String.class))
                .withEffect(TaintEffect.NO_EXECUTE)
                .build(),
            Toleration.builder()
                .withKey(a(String.class))
                .withOperator(TolerationOperator.EXISTS)
                .withValue(a(String.class))
                .withEffect(TaintEffect.NO_EXECUTE)
                .build()
        ))
        .withPriorityClassName(a(String.class))
        .build();

    var json = TestUtils.getJsonRepresentation(traefik);
    assertThat(traefik.validate()).isEmpty();
    assertThat(json).isNotBlank();
    var collectorEndpoint = JsonPath.read(json, "$.tracing.jaeger.collectorEndpoint");
    assertThat(collectorEndpoint).isNull();

    var localAgentPortFromJson = JsonPath.read(json, "$.tracing.jaeger.localAgentPort");
    assertThat(localAgentPortFromJson).isEqualTo(localAgentPort);

    var localAgentUrlPathFromJson = JsonPath.read(json, "$.tracing.jaeger.localAgentUrlPath");
    assertThat(localAgentUrlPathFromJson).isEqualTo(localAgentUrlPath);

    assertThat(traefik.getTracing().getJaeger())
        .extracting(JaegerTracing::getLocalAgentUrlPath, JaegerTracing::getLocalAgentPort)
        .containsExactly(localAgentUrlPath, localAgentPort);

    assertThat(traefik.getNodeSelectors()).hasSize(2);
    assertThat(traefik.getTolerations()).hasSize(3);
  }

  private CaaSTraefik.TraefikBuilder traefikBuilder() {
    return traefikBuilderWithoutEntryPoints()
        .withEntryPoints(aListOf(TraefikEntryPoint.class));
  }

  private CaaSTraefik.TraefikBuilder traefikBuilderWithoutEntryPoints() {
    return CaaSTraefik.builder()
        .withId("traefik")
        .withNamespace(a(String.class))
        .withHostname(a(String.class));
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