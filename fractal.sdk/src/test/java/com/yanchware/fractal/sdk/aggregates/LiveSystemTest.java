package com.yanchware.fractal.sdk.aggregates;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure.AzureNodePool;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KUBERNETES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LiveSystemTest {

    @Test
    public void multipleValidationErrors_when_liveSystemHasNoFields() {
        assertThatThrownBy(() -> LiveSystem.builder().build()).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll("Id has not been defined", "ResourceGroupId has not been defined and it is required", "Components list is null or empty");
    }

    @Test
    public void multipleValidationErrors_when_liveSystemHasNullId() {
        assertThatThrownBy(() -> LiveSystem.builder().id(null).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Id has not been defined");
    }

    @Test
    public void multipleValidationErrors_when_liveSystemHasEmptyId() {
        assertThatThrownBy(() -> LiveSystem.builder().id("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Id has not been defined");
    }

    @Test
    public void multipleValidationErrors_when_liveSystemHasBlankId() {
        assertThatThrownBy(() -> LiveSystem.builder().id("   ").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Id has not been defined");
    }

    @Test
    public void multipleValidationErrors_when_liveSystemHasNullResourceGroupId() {
        assertThatThrownBy(() -> LiveSystem.builder().resourceGroupId(null).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("ResourceGroupId has not been defined and it is required");
    }

    @Test
    public void multipleValidationErrors_when_liveSystemHasEmptyResourceGroupId() {
        assertThatThrownBy(() -> LiveSystem.builder().resourceGroupId("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("ResourceGroupId has not been defined and it is required");
    }

    @Test
    public void multipleValidationErrors_when_liveSystemHasBlankResourceGroupId() {
        assertThatThrownBy(() -> LiveSystem.builder().resourceGroupId("   ").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("ResourceGroupId has not been defined and it is required");
    }

    @Test
    public void multipleValidationErrors_when_liveSystemHasNoComponents() {
        assertThatThrownBy(() -> LiveSystem.builder().component(null).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Components list is null or empty and at least one component is required");
    }

    @Test
    public void noValidationErrors_when_liveSystemWithValidFields() {
        assertThat(generateBuilder().validate()).isEmpty();
    }

    private LiveSystem generateBuilder() {
        return LiveSystem.builder()
                .id("ls")
                .resourceGroupId("res/group")
                .component(AzureKubernetesService.builder()
                        .id(ComponentId.from("aks-1"))
                        .type(KUBERNETES)
                        .nodePool(AzureNodePool.builder().
                                name("aks-node-pool").
                                diskSizeGb(35).
                                build())
                        .build())
                .build();
    }
}