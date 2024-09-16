package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_ELASTIC_LOGGING;
import static org.assertj.core.api.Assertions.*;

class CaaSElasticLoggingTest {
    @Test
    public void exceptionThrown_when_BuiltWithNullId() {
        assertThatThrownBy(() -> CaaSElasticLogging.builder().withId("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Component Id is illegal");
    }

    @Test
    public void exceptionThrown_when_BuiltWithContainerPlatformBlank() {
        assertThatThrownBy(() -> CaaSElasticLogging.builder().withId("el-log").withContainerPlatform("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform defined was either empty or blank");
    }

    @Test
    public void exceptionThrown_when_BuiltWithContainerPlatformEmpty() {
        assertThatThrownBy(() -> CaaSElasticLogging.builder().withId("el-log").withContainerPlatform("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform defined was either empty or blank");
    }

    @Test
    public void exceptionThrown_when_BuiltWithEmptyValues() {
        assertThatThrownBy(() -> CaaSElasticLogging.builder().withId("el-log").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Namespace has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithZeroInstances() {
        assertThatThrownBy(() -> CaaSElasticLogging.builder().withId("el-log").withNamespace("namespace").withInstances(0).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Instances defined was either 0 or negative");
    }

    @Test
    public void exceptionThrown_when_BuiltWithNegativeInstances() {
        assertThatThrownBy(() -> CaaSElasticLogging.builder().withId("el-log").withNamespace("namespace").withInstances(-1).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Instances defined was either 0 or negative");
    }

    @Test
    public void exceptionThrown_when_BuiltWithEmptyElasticVersion() {
        assertThatThrownBy(() -> CaaSElasticLogging.builder().withId("el-log").withNamespace("namespace").withElasticVersion("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Version has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithBlankElasticVersion() {
        assertThatThrownBy(() -> CaaSElasticLogging.builder().withId("el-log").withNamespace("namespace").withElasticVersion("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Version has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithNullElasticVersion() {
        assertThatThrownBy(() -> CaaSElasticLogging.builder().withId("el-log").withNamespace("namespace").withElasticVersion(null).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Version has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithEmptyElasticStorage() {
        assertThatThrownBy(() -> CaaSElasticLogging.builder().withId("el-log").withNamespace("namespace").withStorage("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Storage has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithBlankElasticStorage() {
        assertThatThrownBy(() -> CaaSElasticLogging.builder().withId("el-log").withNamespace("namespace").withStorage("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Storage has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithNullElasticStorage() {
        assertThatThrownBy(() -> CaaSElasticLogging.builder().withId("el-log").withNamespace("namespace").withStorage(null).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Storage has not been defined and it is required");
    }

    @Test
    public void typeIsElasticLogging_when_WithAllRequiredValues() {
        assertThatCode(TestUtils::getElasticLoggingExample).doesNotThrowAnyException();
        var elasticLogging = TestUtils.getElasticLoggingExample();
        
        assertThat(elasticLogging.getType()).isEqualTo(CAAS_ELASTIC_LOGGING);
        assertThat(elasticLogging.isRecreateOnFailure()).isFalse();
    }
}