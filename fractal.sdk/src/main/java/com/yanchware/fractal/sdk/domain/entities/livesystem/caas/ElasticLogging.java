package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSLogging;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.ELASTIC_LOGGING;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class ElasticLogging extends CaaSLogging implements LiveSystemComponent {
    private final static String NAMESPACE_IS_NULL_OR_EMPTY = "[ElasticLogging Validation] Namespace has not been defined and it is required";
    private final static String CONTAINER_PLATFORM_IS_EMPTY = "[ElasticLogging Validation] ContainerPlatform defined was either empty or blank and it is required";
    private final static String ELASTIC_INSTANCES_NEGATIVE_OR_ZERO = "[ElasticLogging Validation] Elastic Instances defined was either 0 or negative and it needs to be greater than 0";
    private final static String VERSION_IS_BLANK = "[ElasticLogging Validation] Elastic Version has not been defined and it is required";
    private final static String STORAGE_IS_BLANK = "[ElasticLogging Validation] Elastic Storage has not been defined and it is required";

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

    protected ElasticLogging() {
        isAPMRequired = false;
        isKibanaRequired = true;
    }

    @Override
    public ProviderType getProvider() {
        return provider;
    }

    public static ElasticLoggingBuilder builder() {
        return new ElasticLoggingBuilder();
    }

    public static class ElasticLoggingBuilder extends Builder<ElasticLogging, ElasticLoggingBuilder> {
        @Override
        protected ElasticLogging createComponent() {
            return new ElasticLogging();
        }

        @Override
        protected ElasticLoggingBuilder getBuilder() {
            return this;
        }

        public ElasticLoggingBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        public ElasticLoggingBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public ElasticLoggingBuilder withAPM(boolean isAPMRequired) {
            component.setAPMRequired(isAPMRequired);
            return builder;
        }

        public ElasticLoggingBuilder withKibana(boolean isKibanaRequired) {
            component.setKibanaRequired(isKibanaRequired);
            return builder;
        }

        public ElasticLoggingBuilder withElasticVersion(String elasticVersion) {
            component.setElasticVersion(elasticVersion);
            return builder;
        }

        public ElasticLoggingBuilder withInstances(int elasticInstances) {
            component.setElasticInstances(elasticInstances);
            return builder;
        }

        public ElasticLoggingBuilder withStorage(String storage) {
            component.setStorage(storage);
            return builder;
        }

        public ElasticLoggingBuilder withStorageClassName(String storageClassName) {
            component.setStorageClassName(storageClassName);
            return builder;
        }

        public ElasticLoggingBuilder withMemory(int memory) {
            component.setMemory(memory);
            return builder;
        }

        public ElasticLoggingBuilder withCpu(int cpu) {
            component.setCpu(cpu);
            return builder;
        }

        @Override
        public ElasticLogging build() {
            component.setType(ELASTIC_LOGGING);
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
