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
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> buildAzureNodePool(null, 30,false)
        );

        assertEquals("[AzureNodePool Validation] Name has not been defined and it is required", exception.getMessage());
    }

    @Test
    public void validationError_when_azureNodePoolWithEmptyName() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> buildAzureNodePool("", 30,false)
        );

        assertEquals("[AzureNodePool Validation] Name has not been defined and it is required", exception.getMessage());
    }

    @Test
    public void validationError_when_azureNodePoolWithBlankName() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> buildAzureNodePool("  ", 30,false)
        );

        assertEquals("[AzureNodePool Validation] Name has not been defined and it is required", exception.getMessage());
    }

    @Test
    public void validationError_when_azureNodePoolWithDiskSizeLessThan30Gb() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> buildAzureNodePool("good_name", 29,false)
        );

        assertEquals("[AzureNodePool Validation] Disk size must be at least 30GB", exception.getMessage());
    }

    @Test
    public void noValidationErrors_when_azureNodePoolWithValidFields() {
        assertThat(buildAzureNodePool("azure-node", 30,false).validate()).isEmpty();
    }

    @Test
    public void throwException_When_NameIsEmpty() {
        assertThrows(IllegalArgumentException.class,
            ()-> buildAzureNodePool("", 30,false));
    }

    @Test
    public void returns_3_nodeTaints_when_azureNodePoolWithValidFields() {
        var azureNodePool = buildAzureNodePool("linuxdynamic", 30,false);
        assertEquals(azureNodePool.getNodeTaints().stream().count(), 3);
    }

    @Test
    public void validationError_when_azureNodePoolWithMaxNodeCountNullAndAutoscalingEnabled() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> buildAzureNodePool("good_name", 30,true)
        );

        assertEquals("[AzureNodePool Validation] MinNodeCount has not been defined and it is required when autoscaling is enabled", exception.getMessage());
    }

    private AzureNodePool buildAzureNodePool(String name, int diskSizeGb, boolean autoscalingEnabled) {
        return AzureNodePool.builder()
            .withName(name)
            .withDiskSizeGb(diskSizeGb)
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
            .withAutoscalingEnabled(autoscalingEnabled)
            .build();
    }
}