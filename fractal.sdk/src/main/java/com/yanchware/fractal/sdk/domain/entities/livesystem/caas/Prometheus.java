package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PROMETHEUS;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Prometheus extends CaaSMonitoringImpl implements LiveSystemComponent {

    public static PrometheusBuilder builder() {
        return new PrometheusBuilder();
    }

    public static class PrometheusBuilder extends Builder<Prometheus, PrometheusBuilder> {

        @Override
        protected Prometheus createComponent() {
            return new Prometheus();
        }

        @Override
        protected PrometheusBuilder getBuilder() {
            return this;
        }

        public PrometheusBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public PrometheusBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        @Override
        public Prometheus build() {
            component.setType(PROMETHEUS);
            return super.build();
        }
    }
}
