package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.utils.ReflectionUtils;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_OPENSEARCH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CaaSOpenSearchTest {

  @Test
  public void exceptionThrown_when_builtWithEmptyId() {
    assertThatThrownBy(() -> CaaSOpenSearch.builder().withId("").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("A valid component id cannot be null, empty or contain spaces");
  }

  @Test
  public void exceptionThrown_when_builtWithBlankContainerPlatform() {
    assertThatThrownBy(() -> openSearchBuilder().withContainerPlatform("   ").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("ContainerPlatform defined was either empty or blank");
  }

  @Test
  public void exceptionThrown_when_builtWithBlankNamespace() {
    assertThatThrownBy(() -> openSearchBuilder().withNamespace(" ").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Namespace has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_builtWithZeroReplicas() {
    assertThatThrownBy(() -> openSearchBuilder().withReplicas(0).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Replicas defined was either 0 or negative");
  }

  @Test
  public void exceptionThrown_when_builtWithNegativeReplicas() {
    assertThatThrownBy(() -> openSearchBuilder().withReplicas(-1).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Replicas defined was either 0 or negative");
  }

  @Test
  public void exceptionThrown_when_builtWithBlankStorage() {
    assertThatThrownBy(() -> openSearchBuilder().withStorage(" ").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Storage defined was either empty or blank");
  }

  @Test
  public void exceptionThrown_when_builtWithBlankStorageClassName() {
    assertThatThrownBy(() -> openSearchBuilder().withStorageClassName("").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Storage class name defined was either empty or blank");
  }

  @Test
  public void exceptionThrown_when_builtWithBlankMemoryOrCpu() {
    assertThatThrownBy(() -> openSearchBuilder().withMemory("").withCpu(" ").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContainingAll(
            "Memory defined was either empty or blank",
            "CPU defined was either empty or blank");
  }

  @Test
  public void defaultsLeftToAgent_when_onlyIdIsSet() {
    var openSearch = openSearchBuilder().build();
    var parameters = parametersOf(openSearch);

    assertThat(openSearch.getType()).isEqualTo(CAAS_OPENSEARCH);
    assertThat(openSearch.getNamespace()).isEqualTo("opensearch");
    assertThat(parameters)
        .containsOnlyKeys("namespace")
        .containsEntry("namespace", "opensearch");
  }

  @Test
  public void parametersMatchContract_when_allValuesAreSet() {
    var openSearch = openSearchBuilder()
        .withNamespace("search")
        .withReplicas(5)
        .withStorage("200Gi")
        .withStorageClassName("premium")
        .withMemory("8Gi")
        .withCpu("4")
        .withDashboards(true)
        .withValues(Map.of("singleNode", false))
        .build();

    assertThat(parametersOf(openSearch))
        .containsEntry("namespace", "search")
        .containsEntry("replicas", 5)
        .containsEntry("storage", "200Gi")
        .containsEntry("storageClassName", "premium")
        .containsEntry("memory", "8Gi")
        .containsEntry("cpu", "4")
        .containsEntry("dashboards", true)
        .containsEntry("values", Map.of("singleNode", false))
        .hasSize(8);
  }

  @Test
  public void typeIsOpenSearch_when_exampleIsBuilt() {
    assertThatCode(TestUtils::getOpenSearchExample).doesNotThrowAnyException();
    var openSearch = TestUtils.getOpenSearchExample();
    assertThat(openSearch.getType()).isEqualTo(CAAS_OPENSEARCH);
    assertThat(openSearch.isRecreateOnFailure()).isFalse();
  }

  @Test
  public void attachedToCluster_when_addedAsDocumentDB() {
    var aks = TestUtils.getAksBuilder().withDocumentDB(openSearchBuilder().build()).build();
    var openSearch = aks.getDocumentDBInstances().getFirst();

    assertThat(openSearch).isInstanceOf(CaaSOpenSearch.class);
    assertThat(openSearch.getContainerPlatform()).isEqualTo(aks.getId().getValue());
    assertThat(openSearch.getDependencies()).containsExactly(aks.getId());
  }

  @SuppressWarnings("unchecked")
  private static Map<String, Object> parametersOf(CaaSOpenSearch openSearch) {
    return (Map<String, Object>) ReflectionUtils.getAllFields(openSearch).get("parameters");
  }

  private static CaaSOpenSearch.OpenSearchBuilder openSearchBuilder() {
    return CaaSOpenSearch.builder().withId("opensearch");
  }
}
