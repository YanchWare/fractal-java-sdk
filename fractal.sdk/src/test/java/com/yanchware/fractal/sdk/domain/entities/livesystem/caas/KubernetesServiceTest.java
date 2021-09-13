package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CONTAINER_PLATFORM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class KubernetesServiceTest {

    @Test
    public void typeIsKubernetes_when_aksIsBuiltWithoutSpecifyType() {
        var builder = generateBuilder();
        assertThat(builder.build().getType()).isEqualTo(CONTAINER_PLATFORM);
        assertThatCode(() -> builder.build()).doesNotThrowAnyException();
    }

    private KubernetesService.KubernetesServiceBuilder generateBuilder() {
        return KubernetesService.builder().id(ComponentId.from("kube"));
    }
}
