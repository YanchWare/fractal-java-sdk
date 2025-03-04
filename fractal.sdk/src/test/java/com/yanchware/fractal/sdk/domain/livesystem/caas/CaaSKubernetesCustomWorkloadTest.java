package com.yanchware.fractal.sdk.domain.livesystem.caas;

import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.livesystem.caas.CaaSKubernetesWorkload.KubernetesWorkloadBuilder;
import static com.yanchware.fractal.sdk.domain.livesystem.caas.CaaSKubernetesWorkload.builder;
import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_K8S_WORKLOAD;
import static org.assertj.core.api.Assertions.*;

public class CaaSKubernetesCustomWorkloadTest {

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
        hasMessageContaining("ContainerPlatform defined was either empty or blank and it is required");
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithContainerPlatformEmpty() {
    assertThatThrownBy(() -> generateBuilder().withContainerPlatform("   ").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("ContainerPlatform defined was either empty or blank and it is required");
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithEmptyValues() {
    assertThatThrownBy(() -> generateBuilder().build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContainingAll(
            "Namespace has not been defined and it is required",
            "sshRepositoryURI is either empty or blank",
            "repoId is either empty or blank");
  }

  @Test
  public void typeIsKubernetes_when_workloadBuiltWithAllRequiredValues() {
    var builder = generateBuilder()
        .withNamespace("fractal")
        .withPrivateSSHKeyPassphraseEnvironmentSecretShortName("svc-private-ssh-key-pass")
        .withPrivateSSHKeyEnvironmentSecretShortName("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withBranchName("branch-name");
    assertThat(builder.build().getType()).isEqualTo(CAAS_K8S_WORKLOAD);
    assertThatCode(builder::build).doesNotThrowAnyException();
  }

  @Test
  public void properValuesSet_when_workloadBuiltWithAllRequiredValues() {
    var serviceAccountName = "required-service-account-name";
    var customWorkload = generateBuilder()
        .withNamespace("fractal")
        .withPrivateSSHKeyPassphraseEnvironmentSecretShortName("svc-private-ssh-key-pass")
        .withPrivateSSHKeyEnvironmentSecretShortName("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withBranchName("branch-name")
        .withServiceAccountName(serviceAccountName)
        .withWorkloadIdentityEnabled(false)
        .withEnvironmentSecretShortName("secret-1")
        .withEnvironmentSecretShortName("secret-2")
        .withEnvironmentSecretShortName("secret-3")
        .withCiCdProfileShortName("ci-cd-profile-name")
        .build();
    
    assertThat(customWorkload.getServiceAccountName()).isEqualTo(serviceAccountName);
    assertThat(customWorkload.getWorkloadIdentityEnabled()).isFalse();
    assertThat(customWorkload.getEnvironmentSecretShortNames()).hasSize(3);
    assertThat(customWorkload.getCiCdProfileShortName()).isEqualTo("ci-cd-profile-name");
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithWorkloadSecretIdKeyEmpty() {
    var builder = generateBuilder()
        .withNamespace("fractal")
        .withPrivateSSHKeyPassphraseEnvironmentSecretShortName("svc-private-ssh-key-pass")
        .withPrivateSSHKeyEnvironmentSecretShortName("svc-private-ssh-key-secret")
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
        .withPrivateSSHKeyPassphraseEnvironmentSecretShortName("svc-private-ssh-key-pass")
        .withPrivateSSHKeyEnvironmentSecretShortName("svc-private-ssh-key-secret")
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
        .withPrivateSSHKeyPassphraseEnvironmentSecretShortName("svc-private-ssh-key-pass")
        .withPrivateSSHKeyEnvironmentSecretShortName("svc-private-ssh-key-secret")
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
