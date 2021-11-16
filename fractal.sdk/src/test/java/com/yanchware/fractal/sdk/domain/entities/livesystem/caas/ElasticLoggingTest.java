package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.ELASTIC_LOGGING;
import static org.assertj.core.api.Assertions.*;

class ElasticLoggingTest {
    @Test
    public void exceptionThrown_when_BuiltWithNullId() {
        assertThatThrownBy(() -> ElasticLogging.builder().withId("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Component Id is illegal");
    }

    @Test
    public void exceptionThrown_when_BuiltWithContainerPlatformBlank() {
        assertThatThrownBy(() -> ElasticLogging.builder().withId("el-log").withContainerPlatform("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform defined was either empty or blank");
    }

    @Test
    public void exceptionThrown_when_BuiltWithContainerPlatformEmpty() {
        assertThatThrownBy(() -> ElasticLogging.builder().withId("el-log").withContainerPlatform("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform defined was either empty or blank");
    }

    @Test
    public void exceptionThrown_when_BuiltWithEmptyValues() {
        assertThatThrownBy(() -> ElasticLogging.builder().withId("el-log").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Namespace has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithZeroInstances() {
        assertThatThrownBy(() -> ElasticLogging.builder().withId("el-log").withNamespace("namespace").withInstances(0).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Instances defined was either 0 or negative");
    }

    @Test
    public void exceptionThrown_when_BuiltWithNegativeInstances() {
        assertThatThrownBy(() -> ElasticLogging.builder().withId("el-log").withNamespace("namespace").withInstances(-1).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Instances defined was either 0 or negative");
    }

    @Test
    public void exceptionThrown_when_BuiltWithEmptyElasticVersion() {
        assertThatThrownBy(() -> ElasticLogging.builder().withId("el-log").withNamespace("namespace").withElasticVersion("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Version has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithBlankElasticVersion() {
        assertThatThrownBy(() -> ElasticLogging.builder().withId("el-log").withNamespace("namespace").withElasticVersion("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Version has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithNullElasticVersion() {
        assertThatThrownBy(() -> ElasticLogging.builder().withId("el-log").withNamespace("namespace").withElasticVersion(null).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Version has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithEmptyElasticStorage() {
        assertThatThrownBy(() -> ElasticLogging.builder().withId("el-log").withNamespace("namespace").withStorage("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Storage has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithBlankElasticStorage() {
        assertThatThrownBy(() -> ElasticLogging.builder().withId("el-log").withNamespace("namespace").withStorage("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Storage has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithNullElasticStorage() {
        assertThatThrownBy(() -> ElasticLogging.builder().withId("el-log").withNamespace("namespace").withStorage(null).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Storage has not been defined and it is required");
    }

    @Test
    public void typeIsElasticLogging_when_WithAllRequiredValues() {
        assertThatCode(TestUtils::getElasticLoggingExample).doesNotThrowAnyException();
        assertThat(TestUtils.getElasticLoggingExample().getType()).isEqualTo(ELASTIC_LOGGING);
    }
}