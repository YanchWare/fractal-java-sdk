package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.gcp;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.gcp.GcpNodePool;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GcpNodePoolTest {

    @Test
    public void validationError_when_gcpNodePoolWithNullName() {
        assertThat(buildGcpNodePool(null).validate()).contains("[GcpNodePool Validation] Name has not been defined and it is required");
    }

    @Test
    public void validationError_when_gcpNodePoolWithEmptyName() {
        assertThat(buildGcpNodePool("").validate()).contains("[GcpNodePool Validation] Name has not been defined and it is required");
    }

    @Test
    public void validationError_when_gcpNodePoolWithBlankName() {
        assertThat(buildGcpNodePool("  ").validate()).contains("[GcpNodePool Validation] Name has not been defined and it is required");
    }

    @Test
    public void noValidationErrors_when_gcpNodePoolWithValidFields() {
        assertThat(buildGcpNodePool("gcp-node").validate()).isEmpty();
    }

    private GcpNodePool buildGcpNodePool(String name) {
        return GcpNodePool.builder().withName(name).build();
    }
}