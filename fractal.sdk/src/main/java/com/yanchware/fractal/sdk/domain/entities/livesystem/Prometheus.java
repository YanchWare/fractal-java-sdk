package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSPrometheus;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PROMETHEUS;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Prometheus extends CaaSPrometheus implements LiveSystemComponent {
    public static final String TYPE = PROMETHEUS.getId();
    private final static String NAMESPACE_IS_NULL_OR_EMPTY = "[Prometheus Validation] Namespace has not been defined and it is required";
    private final static String CONTAINER_PLATFORM_IS_EMPTY = "[Prometheus Validation] ContainerPlatform defined was either empty or blank and it is required";

    private String namespace;
    private String containerPlatform;
    private ProviderType provider;

    @Override
    public ProviderType getProvider() {
        return provider;
    }

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
            component.setVersion(DEFAULT_VERSION);
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
