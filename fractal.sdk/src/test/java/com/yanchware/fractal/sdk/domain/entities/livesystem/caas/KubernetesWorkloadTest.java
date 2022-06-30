package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.KubernetesWorkload.*;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.K8S_WORKLOAD;
import static org.assertj.core.api.Assertions.*;

public class KubernetesWorkloadTest {

  @Test
  public void exceptionThrown_when_workloadBuiltWithNullId() {
    assertThatThrownBy(() -> builder().withId("").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Component Id is illegal");
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithContainerPlatformBlank() {
    assertThatThrownBy(() -> generateBuilder().withContainerPlatform("").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("ContainerPlatform is either empty or blank");
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithContainerPlatformEmpty() {
    assertThatThrownBy(() -> generateBuilder().withContainerPlatform("   ").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("ContainerPlatform is either empty or blank");
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithEmptyValues() {
    assertThatThrownBy(() -> generateBuilder().build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContainingAll(
            "Namespace is either empty or blank",
            "privateSSHKeyPassphraseSecretId is either empty or blank",
            "privateSSHKeySecretId is either empty or blank",
            "sshRepositoryURI is either empty or blank",
            "repoId is either empty or blank");
  }

  @Test
  public void typeIsKubernetes_when_workloadBuiltWithAllRequiredValues() {
    var builder = generateBuilder()
        .withNamespace("fractal")
        .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
        .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withBranchName("branch-name");
    assertThat(builder.build().getType()).isEqualTo(K8S_WORKLOAD);
    assertThatCode(builder::build).doesNotThrowAnyException();
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithWorkloadSecretIdKeyEmpty() {
    var builder = generateBuilder()
        .withNamespace("fractal")
        .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
        .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withSecretIdKey("");
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Workload Secret Id Key is either empty or blank");
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithBranchNameKeyEmpty() {
    var builder = generateBuilder()
        .withNamespace("fractal")
        .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
        .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withBranchName("");
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("branchName is either empty or blank and it is required");
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithWorkloadSecretPasswordKeyEmpty() {
    var builder = generateBuilder()
        .withNamespace("fractal")
        .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
        .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withSecretPasswordKey("");
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Workload Secret Password Key is either empty or blank");
  }

  private KubernetesWorkloadBuilder generateBuilder() {
    return builder().withId("kube");
  }
}
