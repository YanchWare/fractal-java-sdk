package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSAPIGateway;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.AMBASSADOR;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Ambassador extends CaaSAPIGateway implements LiveSystemComponent {
    private final static String NAMESPACE_IS_NULL_OR_EMPTY = "[Ambassador Validation] Namespace has not been defined and it is required";
    private final static String CONTAINER_PLATFORM_IS_EMPTY = "[Ambassador Validation] ContainerPlatform defined was either empty or blank and it is required";

    private String containerPlatform;
    private String namespace;
    private ProviderType provider;

    protected Ambassador() {
    }

    @Override
    public ProviderType getProvider() {
        return provider;
    }

    public static AmbassadorBuilder builder() {
        return new AmbassadorBuilder();
    }

    public static class AmbassadorBuilder extends Builder<Ambassador, AmbassadorBuilder> {
        @Override
        protected Ambassador createComponent() {
            return new Ambassador();
        }

        @Override
        protected AmbassadorBuilder getBuilder() {
            return this;
        }

        public AmbassadorBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public AmbassadorBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        @Override
        public Ambassador build() {
            component.setType(AMBASSADOR);
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
