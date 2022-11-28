package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.ELASTIC_LOGGING;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class CaaSElasticLogging extends CaaSLoggingImpl implements LiveSystemComponent {
    private final static String ELASTIC_INSTANCES_NEGATIVE_OR_ZERO = "[ElasticLogging Validation] Elastic Instances defined was either 0 or negative and it needs to be greater than 0";
    private final static String VERSION_IS_BLANK = "[ElasticLogging Validation] Elastic Version has not been defined and it is required";

    private boolean isApmRequired;
    private boolean isKibanaRequired;

    private String elasticVersion;
    private int elasticInstances;

    protected CaaSElasticLogging() {
        isApmRequired = false;
        isKibanaRequired = true;
    }

    public static ElasticLoggingBuilder builder() {
        return new ElasticLoggingBuilder();
    }

    public static class ElasticLoggingBuilder extends Builder<CaaSElasticLogging, ElasticLoggingBuilder> {
        @Override
        protected CaaSElasticLogging createComponent() {
            return new CaaSElasticLogging();
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

        public ElasticLoggingBuilder withAPM(boolean isApmRequired) {
            component.setApmRequired(isApmRequired);
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
        public CaaSElasticLogging build() {
            component.setType(ELASTIC_LOGGING);
            return super.build();
        }
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (elasticInstances <= 0) {
            errors.add(ELASTIC_INSTANCES_NEGATIVE_OR_ZERO);
        }

        if (isBlank(elasticVersion)) {
            errors.add(VERSION_IS_BLANK);
        }

        return errors;
    }
}
