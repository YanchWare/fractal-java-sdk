package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.PodManagedIdentity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureNodePool;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureKubernetesPodTest {

  @Test
  public void exceptionThrown_when_aksCreatedWithNameNotValid() {
    assertThatThrownBy(() -> generateBuilder()
        .withPodManagedIdentity(PodManagedIdentity.builder()
            .withName("")
            .build())
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Name has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithNamespaceNotValid() {
    assertThatThrownBy(() -> generateBuilder()
        .withPodManagedIdentity(PodManagedIdentity.builder()
            .withName("azure-pod-identity")
            .withNamespace("")
            .build())
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Namespace has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithExceptionPodLabelsNotValid() {
    assertThatThrownBy(() -> generateBuilder()
        .withPodManagedIdentity(PodManagedIdentity.builder()
            .withName("azure-pod-identity")
            .withNamespace("kube-system")
            .withExceptionPodLabels(null)
            .build())
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Exception pod labels has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithEnableNotValid() {
    assertThatThrownBy(() -> generateBuilder()
        .withPodManagedIdentity(PodManagedIdentity.builder()
            .withName("azure-pod-identity")
            .withNamespace("kube-system")
            .withExceptionPodLabels(Map.of("app", "mic", "component", "mic"))
            .withEnable(null)
            .build())
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Enable has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithAllowNetworkPluginKubeNetNotValid() {
    assertThatThrownBy(() -> generateBuilder()
        .withPodManagedIdentity(PodManagedIdentity.builder()
            .withName("azure-pod-identity")
            .withNamespace("kube-system")
            .withExceptionPodLabels(Map.of("app", "mic", "component", "mic"))
            .withEnable(true)
            .withAllowNetworkPluginKubeNet(null)
            .build())
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("AllowNetworkPluginKubeNet has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithPodManagedIdentityValid() {
    assertThat(generateBuilder()
        .withPodManagedIdentity(PodManagedIdentity.builder()
            .withName("azure-pod-identity")
            .withNamespace("kube-system")
            .withExceptionPodLabels(Map.of("app", "mic", "component", "mic"))
            .withEnable(true)
            .withAllowNetworkPluginKubeNet(true)
            .build())
        .build()
        .validate()).isEmpty();
  }

  private AzureKubernetesService.AzureKubernetesServiceBuilder generateBuilder() {
    return AzureKubernetesService.builder()
        .withId(ComponentId.from("test"))
        .withNodePool(AzureNodePool.builder()
            .withName("azure-node-pool-name")
            .withDiskSizeGb(30)
            .withInitialNodeCount(1)
            .withAutoscalingEnabled(false).build());
  }
}
