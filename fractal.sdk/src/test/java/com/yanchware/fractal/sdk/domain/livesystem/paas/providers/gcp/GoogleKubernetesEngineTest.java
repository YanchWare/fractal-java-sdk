package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp;

import com.yanchware.fractal.sdk.domain.livesystem.caas.PriorityClass;
import com.yanchware.fractal.sdk.domain.livesystem.paas.PodManagedIdentity;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_KUBERNETES;
import static com.yanchware.fractal.sdk.utils.TestUtils.getDefaultGke;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;

public class GoogleKubernetesEngineTest {

  @Test
  public void noValidationErrors_when_basicGke() {
    assertThat(getDefaultGke().build().validate()).isEmpty();
  }

  @Test
  public void typeIsKubernetes_when_gkeIsBuilt() {
    assertThat(getDefaultGke().build().getType()).isEqualTo(PAAS_KUBERNETES);
  }

  @Test
  public void exceptionThrown_when_gkeCreatedWithNullId() {
    assertThatThrownBy(() -> getDefaultGke().withId("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("A valid component id cannot be null, empty or contain spaces");
  }

  @Test
  public void typeIsKubernetes_when_gkeIsBuiltWithoutSpecifyType() {
    var gkeBuilder = getDefaultGke();
    assertThatCode(gkeBuilder::build).doesNotThrowAnyException();
    assertThat(gkeBuilder.build().getType()).isEqualTo(PAAS_KUBERNETES);
  }

  @Test
  public void exceptionThrown_when_gkeCreatedWithNullNodePools() {
    var gke = GoogleKubernetesEngine.builder()
      .withId("test");
    assertThatThrownBy(gke::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Node pool list " +
      "is null or empty");
  }

  @Test
  public void exceptionThrown_when_gkeCreatedWithEmptyNodePools() {
    var gke = GoogleKubernetesEngine.builder()
      .withId("test")
      .withNodePools(emptyList());
    assertThatThrownBy(gke::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Node pool list " +
      "is null or empty");
  }

  @Test
  public void exceptionThrown_when_gkeCreatedWithPodIdentityAndPriorityClasses() {
    var gke = getDefaultGke()
      .withPodManagedIdentity(PodManagedIdentity.builder()
        .withName("azure-pod-identity")
        .withNamespace("kube-system")
        .withExceptionPodLabels(Map.of("app", "mic", "component", "mic"))
        .withEnable(true)
        .withAllowNetworkPluginKubeNet(true)
        .build())
      .withPriorityClass(PriorityClass.builder()
        .withName("test")
        .withValue(200)
        .build());
    assertThatThrownBy(gke::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll("Priority " +
      "classes are not fully supported yet for GCP", "Pod Managed Identity is not fully supported yet for GCP");
  }

}