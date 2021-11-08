package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.KubernetesService.*;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.K8S_SERVICE;
import static org.assertj.core.api.Assertions.*;

public class KubernetesServiceTest {

    @Test
    public void exceptionThrown_when_svcBuiltWithNullId() {
        assertThatThrownBy(() -> builder().withId("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Component Id is illegal");
    }

    @Test
    public void exceptionThrown_when_svcBuiltWithContainerPlatformBlank() {
        assertThatThrownBy(() -> generateBuilder().withContainerPlatform("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform is either empty or blank");
    }

    @Test
    public void exceptionThrown_when_svcBuiltWithContainerPlatformEmpty() {
        assertThatThrownBy(() -> generateBuilder().withContainerPlatform("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform is either empty or blank");
    }

    @Test
    public void exceptionThrown_when_svcBuiltWithEmptyValues() {
        assertThatThrownBy(() -> generateBuilder().build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContainingAll(
                        "Namespace is either empty or blank",
                        "privateSSHKeyPassphraseSecretId is either empty or blank",
                        "privateSSHKeySecretId is either empty or blank",
                        "publicSSHKey is either empty or blank",
                        "sshRepositoryURI is either empty or blank",
                        "repoId is either empty or blank");
    }

    @Test
    public void typeIsKubernetes_when_svcBuiltWithAllRequiredValues() {
        var builder = generateBuilder()
                .withNamespace("fractal")
                .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
                .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
                .withPublicSSHKey("public-ssh")
                .withSshRepositoryURI("ssh")
                .withRepoId("repo-id");
        assertThat(builder.build().getType()).isEqualTo(K8S_SERVICE);
        assertThatCode(builder::build).doesNotThrowAnyException();
    }

    private KubernetesServiceBuilder generateBuilder() {
        return builder().withId("kube");
    }
}
