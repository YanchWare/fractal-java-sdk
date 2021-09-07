package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.gcp;

import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KUBERNETES;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GoogleKubernetesEngineTest {

    @Test
    public void noValidationErrors_when_gkeHasRequiredFields() {
        assertThat(generateBuilder().build().validate()).isEmpty();
    }

    @Test
    public void exceptionThrown_when_gkeCreatedWithNullId() {
        assertThatThrownBy(() -> generateBuilder().id(null).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Component id has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_gkeCreatedWithNullType() {
        assertThatThrownBy(() -> generateBuilder().type(null).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Component type has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_gkeCreatedWithNullNodePools() {
        var gke = GoogleKubernetesEngine.builder()
                .id(ComponentId.from("test"))
                .type(KUBERNETES);
        assertThatThrownBy(gke::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Node pool list is null or empty");
    }

    @Test
    public void exceptionThrown_when_gkeCreatedWithEmptyNodePools() {
        var gke = GoogleKubernetesEngine.builder()
                .id(ComponentId.from("test"))
                .type(KUBERNETES)
                .nodePools(emptyList());
        assertThatThrownBy(gke::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Node pool list is null or empty");
    }

    private GoogleKubernetesEngine.GoogleKubernetesEngineBuilder generateBuilder() {
        return GoogleKubernetesEngine.builder()
                .id(ComponentId.from("test"))
                .type(KUBERNETES)
                .nodePool(GcpNodePool.builder().name("gcp-node-pool-name").build());
    }

}