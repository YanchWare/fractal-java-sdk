package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.ComponentLink;
import com.yanchware.fractal.sdk.domain.values.ComponentId;
import com.yanchware.fractal.sdk.utils.ReflectionUtils;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_JAEGER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CaaSJaegerTest {

  @Test
  public void exceptionThrown_when_builtWithEmptyId() {
    assertThatThrownBy(() -> CaaSJaeger.builder().withId("").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("A valid component id cannot be null, empty or contain spaces");
  }

  @Test
  public void exceptionThrown_when_builtWithoutOpenSearchLink() {
    assertThatThrownBy(() -> CaaSJaeger.builder().withId("jaeger").withNamespace("tracing").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("needs exactly one OpenSearch link: call linkToOpenSearch(...)");
  }

  @Test
  public void exceptionThrown_when_linkedToOpenSearchTwice() {
    assertThatThrownBy(() -> jaegerBuilder()
        .linkToOpenSearch(ComponentId.from("other-search"), null)
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("linkToOpenSearch(...) was called more than once");
  }

  @Test
  public void exceptionThrown_when_builtWithoutNamespace() {
    assertThatThrownBy(() -> CaaSJaeger.builder()
        .withId("jaeger")
        .linkToOpenSearch(openSearch())
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("[CaaSJaeger Validation] Namespace has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_indexPrefixHasWildcard() {
    assertThatThrownBy(() -> CaaSJaeger.builder().linkToOpenSearch(openSearch(), "jaeger-*"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Index prefix 'jaeger-*' is illegal");
  }

  @Test
  public void exceptionThrown_when_indexPrefixHasUppercase() {
    assertThatThrownBy(() -> CaaSJaeger.builder().linkToOpenSearch(openSearch(), "Jaeger-"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Index prefix 'Jaeger-' is illegal");
  }

  @Test
  public void exceptionThrown_when_indexPrefixIsBlank() {
    assertThatThrownBy(() -> CaaSJaeger.builder().linkToOpenSearch(openSearch(), "  "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("is illegal");
  }

  @Test
  public void linkAndDependencyOnOpenSearch_when_linkedWithDefaultPrefix() {
    var jaeger = CaaSJaeger.builder()
        .withNamespace("tracing")
        .linkToOpenSearch(openSearch())
        .withId("traces")
        .build();

    assertThat(jaeger.getType()).isEqualTo(CAAS_JAEGER);
    assertThat(jaeger.getDependencies()).containsExactly(ComponentId.from("opensearch"));
    assertThat(jaeger.getLinks())
        .singleElement()
        .satisfies(link -> {
          assertThat(link.getComponentId()).isEqualTo("opensearch");
          assertThat(link.getSettings()).isEqualTo(Map.of(
              "access", "read-write",
              "indexPrefix", "traces-jaeger-"));
        });
  }

  @Test
  public void linkCarriesGivenPrefix_when_linkedWithCustomPrefix() {
    var jaeger = CaaSJaeger.builder()
        .withId("jaeger")
        .withNamespace("tracing")
        .linkToOpenSearch(ComponentId.from("shared-search"), "team-a.traces_")
        .build();

    assertThat(jaeger.getDependencies()).containsExactly(ComponentId.from("shared-search"));
    assertThat(jaeger.getLinks())
        .extracting(ComponentLink::getSettings)
        .containsExactly(Map.of("access", "read-write", "indexPrefix", "team-a.traces_"));
  }

  @Test
  public void parametersHaveNoStorageSettings_when_built() {
    var parameters = (Map<String, Object>) ReflectionUtils.getAllFields(jaegerBuilder().build()).get("parameters");
    assertThat(parameters).containsOnlyKeys("namespace");
  }

  @Test
  public void attachedToCluster_when_addedAsTracing() {
    var aks = TestUtils.getAksBuilder().withTracing(jaegerBuilder().build()).build();
    var jaeger = aks.getTracingInstances().getFirst();

    assertThat(jaeger.getContainerPlatform()).isEqualTo(aks.getId().getValue());
    assertThat(jaeger.getDependencies()).containsExactlyInAnyOrder(aks.getId(), ComponentId.from("opensearch"));
  }

  private static CaaSOpenSearch openSearch() {
    return CaaSOpenSearch.builder().withId("opensearch").build();
  }

  private static CaaSJaeger.JaegerBuilder jaegerBuilder() {
    return CaaSJaeger.builder()
        .withId("jaeger")
        .withNamespace("tracing")
        .linkToOpenSearch(openSearch());
  }
}
