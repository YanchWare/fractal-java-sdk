package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;


import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureNodePool;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.NodeTaint;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.TaintEffect;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AzureNodePoolTest {
    @Test
    public void validationError_when_azureNodePoolWithNullName() {
        assertThat(buildAzureNodePool(null).validate()).contains("[AzureNodePool Validation] Name has not been defined and it is required");
    }

    @Test
    public void validationError_when_azureNodePoolWithEmptyName() {
        assertThat(buildAzureNodePool("").validate()).contains("[AzureNodePool Validation] Name has not been defined and it is required");
    }

    @Test
    public void validationError_when_azureNodePoolWithBlankName() {
        assertThat(buildAzureNodePool("  ").validate()).contains("[AzureNodePool Validation] Name has not been defined and it is required");
    }

    @Test
    public void validationError_when_azureNodePoolWithDiskSizeLessThan30Gb() {
        var azureNodePool = buildAzureNodePool("azure-node");
        azureNodePool.setDiskSizeGb(29);
        assertThat(azureNodePool.validate()).contains("[AzureNodePool Validation] Disk size must be at least 30GB");
    }

    @Test
    public void noValidationErrors_when_azureNodePoolWithValidFields() {
        assertThat(buildAzureNodePool("azure-node").validate()).isEmpty();
    }

    @Test
    public void throwException_When_NameIsNull() {
        assertThrows(NullPointerException.class,
            ()-> buildAzureNodePool(null));
    }

    @Test
    public void throwException_When_NameIsEmpty() {
        assertThrows(IllegalArgumentException.class,
            ()-> buildAzureNodePool(""));
    }

    @Test
    public void returns_3_nodeTaints_when_azureNodePoolWithValidFields() {
        var azureNodePool = buildAzureNodePool("azure-node");
        assertEquals(azureNodePool.getNodeTaints().stream().count(), 3);
    }

    private AzureNodePool buildAzureNodePool(String name) {
        return AzureNodePool.builder()
            .withName(name)
            .withDiskSizeGb(30)
            .withInitialNodeCount(1)
            .withMaxNodeCount(3)
            .withNodeTaint(NodeTaint.builder()
                .withKey("testKey")
                .withValue("testValue")
                .withEffect(TaintEffect.NO_EXECUTE)
                .build())
            .withNodeTaints(List.of(
                NodeTaint.builder().withKey("key1").withValue("value1").withEffect(TaintEffect.NO_SCHEDULE).build(),
                NodeTaint.builder().withKey("key2").withValue("value3").withEffect(TaintEffect.PREFER_NO_SCHEDULE).build()
            ))
            .build();
    }
}