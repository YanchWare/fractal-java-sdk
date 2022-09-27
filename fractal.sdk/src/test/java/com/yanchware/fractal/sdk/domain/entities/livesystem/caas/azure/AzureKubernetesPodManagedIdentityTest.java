package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.PodManagedIdentity;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.yanchware.fractal.sdk.utils.TestUtils.getBasicAks;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureKubernetesPodManagedIdentityTest {

  @Test
  public void exceptionThrown_when_aksCreatedWithNameNotValid() {
    assertThatThrownBy(() -> getBasicAks()
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
    assertThatThrownBy(() -> getBasicAks()
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
    assertThatThrownBy(() -> getBasicAks()
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
    assertThatThrownBy(() -> getBasicAks()
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
    assertThatThrownBy(() -> getBasicAks()
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
}
