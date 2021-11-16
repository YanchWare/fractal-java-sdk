package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSTracing;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.JaegerStorageType.Elastic;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.JaegerStorageType.Unknown;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.JAEGER;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Jaeger extends CaaSTracing implements LiveSystemComponent {
    private final static String NAMESPACE_IS_NULL_OR_EMPTY = "[Jaeger Validation] Namespace has not been defined and it is required";
    private final static String CONTAINER_PLATFORM_IS_EMPTY = "[Jaeger Validation] ContainerPlatform defined was either empty or blank and it is required";

    private String namespace;
    private String containerPlatform;
    private JaegerStorageSetting storageSettings;
    private ProviderType provider;

    protected Jaeger() {
        storageSettings = new JaegerStorageSetting();
        storageSettings.setType(Unknown);
    }

    @Override
    public ProviderType getProvider() {
        return provider;
    }

    public static JaegerBuilder builder() {
        return new JaegerBuilder();
    }

    public static class JaegerBuilder extends Builder<Jaeger, JaegerBuilder> {

        @Override
        protected Jaeger createComponent() {
            return new Jaeger();
        }

        @Override
        protected JaegerBuilder getBuilder() {
            return this;
        }

        public JaegerBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public JaegerBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        public JaegerBuilder withStorageTypeElastic() {
            if (component.getStorageSettings() == null) {
                component.setStorageSettings(new JaegerStorageSetting());
            }
            component.getStorageSettings().setType(Elastic);
            return builder;
        }

        public JaegerBuilder withStorageVersion(String version) {
            if (component.getStorageSettings() == null) {
                component.setStorageSettings(new JaegerStorageSetting());
            }
            component.getStorageSettings().setVersion(version);
            return builder;
        }

        public JaegerBuilder withStorageInstances(int instances) {
            if (component.getStorageSettings() == null) {
                component.setStorageSettings(new JaegerStorageSetting());
            }
            component.getStorageSettings().setInstances(instances);
            return builder;
        }

        public JaegerBuilder withStorageSize(String size) {
            if (component.getStorageSettings() == null) {
                component.setStorageSettings(new JaegerStorageSetting());
            }
            component.getStorageSettings().setSize(size);
            return builder;
        }

        public JaegerBuilder withStorageStorageClassName(String storageClassName) {
            if (component.getStorageSettings() == null) {
                component.setStorageSettings(new JaegerStorageSetting());
            }
            component.getStorageSettings().setStorageClassName(storageClassName);
            return builder;
        }

        public JaegerBuilder withStorageCpu(int cpu) {
            if (component.getStorageSettings() == null) {
                component.setStorageSettings(new JaegerStorageSetting());
            }
            component.getStorageSettings().setCpu(cpu);
            return builder;
        }

        public JaegerBuilder withStorageMemory(int memory) {
            if (component.getStorageSettings() == null) {
                component.setStorageSettings(new JaegerStorageSetting());
            }
            component.getStorageSettings().setMemory(memory);
            return builder;
        }

        @Override
        public Jaeger build() {
            component.setType(JAEGER);
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
        return errors;
    }
}
