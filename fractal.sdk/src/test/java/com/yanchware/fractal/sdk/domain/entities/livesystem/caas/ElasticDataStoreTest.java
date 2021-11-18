package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.ELASTIC_DATASTORE;
import static org.assertj.core.api.Assertions.*;

class ElasticDataStoreTest {
    @Test
    public void exceptionThrown_when_BuiltWithNullId() {
        assertThatThrownBy(() -> ElasticDataStore.builder().withId("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Component Id is illegal");
    }

    @Test
    public void exceptionThrown_when_BuiltWithContainerPlatformBlank() {
        assertThatThrownBy(() -> ElasticDataStore.builder().withId("el-data-store").withContainerPlatform("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform defined was either empty or blank");
    }

    @Test
    public void exceptionThrown_when_BuiltWithContainerPlatformEmpty() {
        assertThatThrownBy(() -> ElasticDataStore.builder().withId("el-data-store").withContainerPlatform("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform defined was either empty or blank");
    }

    @Test
    public void exceptionThrown_when_BuiltWithEmptyValues() {
        assertThatThrownBy(() -> ElasticDataStore.builder().withId("el-data-store").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Namespace has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithZeroInstances() {
        assertThatThrownBy(() -> ElasticDataStore.builder().withId("el-data-store").withNamespace("namespace").withInstances(0).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Instances defined was either 0 or negative");
    }

    @Test
    public void exceptionThrown_when_BuiltWithNegativeInstances() {
        assertThatThrownBy(() -> ElasticDataStore.builder().withId("el-data-store").withNamespace("namespace").withInstances(-1).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Instances defined was either 0 or negative");
    }

    @Test
    public void exceptionThrown_when_BuiltWithEmptyElasticVersion() {
        assertThatThrownBy(() -> ElasticDataStore.builder().withId("el-data-store").withNamespace("namespace").withElasticVersion("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Version has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithBlankElasticVersion() {
        assertThatThrownBy(() -> ElasticDataStore.builder().withId("el-data-store").withNamespace("namespace").withElasticVersion("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Version has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithNullElasticVersion() {
        assertThatThrownBy(() -> ElasticDataStore.builder().withId("el-data-store").withNamespace("namespace").withElasticVersion(null).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Version has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithEmptyElasticStorage() {
        assertThatThrownBy(() -> ElasticDataStore.builder().withId("el-data-store").withNamespace("namespace").withStorage("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Storage has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithBlankElasticStorage() {
        assertThatThrownBy(() -> ElasticDataStore.builder().withId("el-data-store").withNamespace("namespace").withStorage("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Storage has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithNullElasticStorage() {
        assertThatThrownBy(() -> ElasticDataStore.builder().withId("el-data-store").withNamespace("namespace").withStorage(null).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Storage has not been defined and it is required");
    }

    @Test
    public void typeIsElasticDataStore_when_WithAllRequiredValues() {
        assertThatCode(TestUtils::getElasticDataStoreExample).doesNotThrowAnyException();
        assertThat(TestUtils.getElasticDataStoreExample().getType()).isEqualTo(ELASTIC_DATASTORE);
    }
}