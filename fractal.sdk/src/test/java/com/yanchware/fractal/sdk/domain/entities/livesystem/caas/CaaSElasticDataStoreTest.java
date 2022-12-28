package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CAAS_ELASTIC_DATASTORE;
import static org.assertj.core.api.Assertions.*;

class CaaSElasticDataStoreTest {
    @Test
    public void exceptionThrown_when_BuiltWithNullId() {
        assertThatThrownBy(() -> CaaSElasticDataStore.builder().withId("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Component Id is illegal");
    }

    @Test
    public void exceptionThrown_when_BuiltWithContainerPlatformBlank() {
        assertThatThrownBy(() -> getElasticDataStoreBuilder().withContainerPlatform("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform defined was either empty or blank");
    }

    @Test
    public void exceptionThrown_when_BuiltWithContainerPlatformEmpty() {
        assertThatThrownBy(() -> getElasticDataStoreBuilder().withContainerPlatform("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("ContainerPlatform defined was either empty or blank");
    }

    @Test
    public void exceptionThrown_when_BuiltWithEmptyValues() {
        assertThatThrownBy(() -> getElasticDataStoreBuilder().build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Namespace has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithZeroInstances() {
        assertThatThrownBy(() -> getElasticDataStoreBuilder().withNamespace("namespace").withInstances(0).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Instances defined was either 0 or negative");
    }

    @Test
    public void exceptionThrown_when_BuiltWithNegativeInstances() {
        assertThatThrownBy(() -> getElasticDataStoreBuilder().withNamespace("namespace").withInstances(-1).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Instances defined was either 0 or negative");
    }

    @Test
    public void exceptionThrown_when_BuiltWithEmptyElasticVersion() {
        assertThatThrownBy(() -> getElasticDataStoreBuilder().withNamespace("namespace").withElasticVersion("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Version has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithBlankElasticVersion() {
        assertThatThrownBy(() -> getElasticDataStoreBuilder().withNamespace("namespace").withElasticVersion("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Version has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithNullElasticVersion() {
        assertThatThrownBy(() -> getElasticDataStoreBuilder().withNamespace("namespace").withElasticVersion(null).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Elastic Version has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithEmptyElasticStorage() {
        assertThatThrownBy(() -> getElasticDataStoreBuilder().withNamespace("namespace").withStorage("").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Storage has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithBlankElasticStorage() {
        assertThatThrownBy(() -> getElasticDataStoreBuilder().withNamespace("namespace").withStorage("   ").build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Storage has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_BuiltWithNullElasticStorage() {
        assertThatThrownBy(() -> getElasticDataStoreBuilder().withNamespace("namespace").withStorage(null).build()).
                isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Storage has not been defined and it is required");
    }

    @Test
    public void typeIsElasticDataStore_when_WithAllRequiredValues() {
        assertThatCode(TestUtils::getElasticDataStoreExample).doesNotThrowAnyException();
        assertThat(TestUtils.getElasticDataStoreExample().getType()).isEqualTo(CAAS_ELASTIC_DATASTORE);
    }

    private static CaaSElasticDataStore.ElasticDataStoreBuilder getElasticDataStoreBuilder() {
        return CaaSElasticDataStore.builder().withId("el-data-store");
    }
}