package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.Test;

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
        assertThatThrownBy(() -> generateBuilder().withId("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("A valid component id cannot be null, empty or contain spaces");
    }

    @Test
    public void typeIsKubernetes_when_aksIsBuiltWithoutSpecifyType() {
        var aksBuilder = generateBuilder();
        assertThat(aksBuilder.build().getType()).isEqualTo(KUBERNETES);
        assertThatCode(aksBuilder::build).doesNotThrowAnyException();
    }

    @Test
    public void exceptionThrown_when_aksCreatedWithNullNodePools() {
        var aks = AzureKubernetesService.builder()
                .withId(ComponentId.from("test"));
        assertThatThrownBy(aks::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Node pool list is null or empty");
    }

    @Test
    public void exceptionThrown_when_aksCreatedWithEmptyNodePools() {
        var aks = AzureKubernetesService.builder()
                .withId(ComponentId.from("test"))
                .withNodePools(emptyList());
        assertThatThrownBy(aks::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Node pool list is null or empty");
    }

    private AzureKubernetesService.AzureKubernetesServiceBuilder generateBuilder() {
        return AzureKubernetesService.builder()
                .withId(ComponentId.from("test"))
                .withNodePool(AzureNodePool.builder().name("azure-node-pool-name").diskSizeGb(30).build());
    }
}