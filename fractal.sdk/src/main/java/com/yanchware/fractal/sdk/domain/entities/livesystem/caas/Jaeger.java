package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.JaegerStorageType.Elastic;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.JaegerStorageType.Unknown;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.JAEGER;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Jaeger extends CaaSTracingImpl implements LiveSystemComponent {
    private JaegerStorageSetting storageSettings;

    protected Jaeger() {
        storageSettings = new JaegerStorageSetting();
        storageSettings.setType(Unknown);
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
}
