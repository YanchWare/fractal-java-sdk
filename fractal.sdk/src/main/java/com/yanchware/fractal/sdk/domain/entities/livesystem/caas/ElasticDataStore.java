package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSDocumentDB;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.ELASTIC_DATASTORE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class ElasticDataStore extends CaaSDocumentDB implements LiveSystemComponent {
    private final static String NAMESPACE_IS_NULL_OR_EMPTY = "[ElasticDataStore Validation] Namespace has not been defined and it is required";
    private final static String CONTAINER_PLATFORM_IS_EMPTY = "[ElasticDataStore Validation] ContainerPlatform defined was either empty or blank and it is required";
    private final static String ELASTIC_INSTANCES_NEGATIVE_OR_ZERO = "[ElasticDataStore Validation] Elastic Instances defined was either 0 or negative and it needs to be greater than 0";
    private final static String VERSION_IS_BLANK = "[ElasticDataStore Validation] Elastic Version has not been defined and it is required";
    private final static String STORAGE_IS_BLANK = "[ElasticDataStore Validation] Elastic Storage has not been defined and it is required";

    private String containerPlatform;
    private String namespace;
    private boolean isAPMRequired;
    private boolean isKibanaRequired;
    private String elasticVersion;
    private int elasticInstances;
    private String storage;
    private String storageClassName;
    private int memory;
    private int cpu;
    private ProviderType provider;

    protected ElasticDataStore() {
        isAPMRequired = false;
        isKibanaRequired = true;
    }

    @Override
    public ProviderType getProvider() {
        return provider;
    }

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

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (isBlank(namespace)) {
            errors.add(NAMESPACE_IS_NULL_OR_EMPTY);
        }

        if (containerPlatform != null && isBlank(containerPlatform)) {
            errors.add(CONTAINER_PLATFORM_IS_EMPTY);
        }

        if(elasticInstances <= 0) {
            errors.add(ELASTIC_INSTANCES_NEGATIVE_OR_ZERO);
        }

        if(isBlank(elasticVersion)) {
            errors.add(VERSION_IS_BLANK);
        }

        if(isBlank(storage)) {
            errors.add(STORAGE_IS_BLANK);
        }

        return errors;
    }
}
