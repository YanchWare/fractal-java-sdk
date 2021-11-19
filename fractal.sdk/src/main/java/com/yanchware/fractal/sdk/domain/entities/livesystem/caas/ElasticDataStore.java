package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.ELASTIC_DATASTORE;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class ElasticDataStore extends CaaSDocumentDBImpl implements LiveSystemComponent {

    public static ElasticDataStoreBuilder builder() {
        return new ElasticDataStoreBuilder();
    }

    public static class ElasticDataStoreBuilder extends Builder<ElasticDataStore, ElasticDataStoreBuilder> {
        @Override
        protected ElasticDataStore createComponent() {
            return new ElasticDataStore();
        }

        @Override
        protected ElasticDataStoreBuilder getBuilder() {
            return this;
        }

        public ElasticDataStoreBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        public ElasticDataStoreBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public ElasticDataStoreBuilder withAPM(boolean isAPMRequired) {
            component.setAPMRequired(isAPMRequired);
            return builder;
        }

        public ElasticDataStoreBuilder withKibana(boolean isKibanaRequired) {
            component.setKibanaRequired(isKibanaRequired);
            return builder;
        }

        public ElasticDataStoreBuilder withElasticVersion(String elasticVersion) {
            component.setElasticVersion(elasticVersion);
            return builder;
        }

        public ElasticDataStoreBuilder withInstances(int elasticInstances) {
            component.setElasticInstances(elasticInstances);
            return builder;
        }

        public ElasticDataStoreBuilder withStorage(String storage) {
            component.setStorage(storage);
            return builder;
        }

        public ElasticDataStoreBuilder withStorageClassName(String storageClassName) {
            component.setStorageClassName(storageClassName);
            return builder;
        }

        public ElasticDataStoreBuilder withMemory(int memory) {
            component.setMemory(memory);
            return builder;
        }

        public ElasticDataStoreBuilder withCpu(int cpu) {
            component.setCpu(cpu);
            return builder;
        }

        @Override
        public ElasticDataStore build() {
            component.setType(ELASTIC_DATASTORE);
            return super.build();
        }
    }
}
