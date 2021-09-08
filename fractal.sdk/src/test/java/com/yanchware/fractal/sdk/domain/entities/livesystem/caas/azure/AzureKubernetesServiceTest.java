package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KUBERNETES;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;

public class AzureKubernetesServiceTest {

    @Test
    public void noValidationErrors_when_aksHasRequiredFields() {
        assertThat(generateBuilder().build().validate()).isEmpty();
    }

    @Test
    public void exceptionThrown_when_aksCreatedWithNullId() {
        assertThatThrownBy(() -> generateBuilder().id(null).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Component id has not been defined and it is required");
    }

    @Test
    public void typeIsKubernetes_when_aksIsBuiltWithoutSpecifyType() {
        var aksBuilder = generateBuilder();
        assertThat(aksBuilder.build().getType()).isEqualTo(KUBERNETES);
        assertThatCode(() -> aksBuilder.build()).doesNotThrowAnyException();
    }

    @Test
    public void exceptionThrown_when_aksCreatedWithNullNodePools() {
        var aks = AzureKubernetesService.builder()
                .id(ComponentId.from("test"));
        assertThatThrownBy(aks::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Node pool list is null or empty");
    }

    @Test
    public void exceptionThrown_when_aksCreatedWithEmptyNodePools() {
        var aks = AzureKubernetesService.builder()
                .id(ComponentId.from("test"))
                .nodePools(emptyList());
        assertThatThrownBy(aks::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Node pool list is null or empty");
    }

    private AzureKubernetesService.AzureKubernetesServiceBuilder generateBuilder() {
        return AzureKubernetesService.builder()
                .id(ComponentId.from("test"))
                .nodePool(AzureNodePool.builder().name("azure-node-pool-name").diskSizeGb(30).build());
    }
}