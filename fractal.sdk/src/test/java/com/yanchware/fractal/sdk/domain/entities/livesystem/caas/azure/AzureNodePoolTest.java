package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureNodePoolTest {
    @Test
    public void validationError_when_azureNodePoolWithNullName() {
        assertThat(buildAzureNodePool(null).validate()).contains("AzureNodePool name has not been defined and it is required");
    }

    @Test
    public void validationError_when_azureNodePoolWithEmptyName() {
        assertThat(buildAzureNodePool("").validate()).contains("AzureNodePool name has not been defined and it is required");
    }

    @Test
    public void validationError_when_azureNodePoolWithBlankName() {
        assertThat(buildAzureNodePool("  ").validate()).contains("AzureNodePool name has not been defined and it is required");
    }

    @Test
    public void validationError_when_azureNodePoolWithDiskSizeLessThan30Gb() {
        var azureNodePool = buildAzureNodePool("azure-node");
        azureNodePool.setDiskSizeGb(29);
        assertThat(azureNodePool.validate()).contains("AzureNodePool disk size must be at least 30GB");
    }

    @Test
    public void noValidationErrors_when_azureNodePoolWithValidFields() {
        assertThat(buildAzureNodePool("azure-node").validate()).isEmpty();
    }

    private AzureNodePool buildAzureNodePool(String name) {
        return AzureNodePool.builder().name(name).diskSizeGb(30).build();
    }
}