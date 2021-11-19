package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.gcp;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.gcp.GcpNodePool;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.gcp.GoogleKubernetesEngine;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KUBERNETES;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;

public class GoogleKubernetesEngineTest {

    @Test
    public void noValidationErrors_when_gkeHasRequiredFields() {
        assertThat(generateBuilder().build().validate()).isEmpty();
    }

    @Test
    public void typeIsKubernetes_when_gkeIsBuilt() {
        assertThat(generateBuilder().build().getType()).isEqualTo(KUBERNETES);
    }

    @Test
    public void exceptionThrown_when_gkeCreatedWithNullId() {
        assertThatThrownBy(() -> generateBuilder().withId("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("A valid component id cannot be null, empty or contain spaces");
    }

    @Test
    public void typeIsKubernetes_when_gkeIsBuiltWithoutSpecifyType() {
        var gkeBuilder = generateBuilder();
        assertThatCode(gkeBuilder::build).doesNotThrowAnyException();
        assertThat(gkeBuilder.build().getType()).isEqualTo(KUBERNETES);
    }

    @Test
    public void exceptionThrown_when_gkeCreatedWithNullNodePools() {
        var gke = GoogleKubernetesEngine.builder()
                .withId("test");
        assertThatThrownBy(gke::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Node pool list is null or empty");
    }

    @Test
    public void exceptionThrown_when_gkeCreatedWithEmptyNodePools() {
        var gke = GoogleKubernetesEngine.builder()
                .withId("test")
                .withNodePools(emptyList());
        assertThatThrownBy(gke::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Node pool list is null or empty");
    }

    private GoogleKubernetesEngine.GoogleKubernetesEngineBuilder generateBuilder() {
        return GoogleKubernetesEngine.builder()
                .withId("test")
                .withNodePool(GcpNodePool.builder().name("gcp-node-pool-name").build());
    }

}