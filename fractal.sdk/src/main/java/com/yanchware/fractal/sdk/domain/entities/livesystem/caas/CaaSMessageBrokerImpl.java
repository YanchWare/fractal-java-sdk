package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSMessageBroker;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class CaaSMessageBrokerImpl extends CaaSMessageBroker implements LiveSystemComponent {
    private final static String NAMESPACE_IS_NULL_OR_EMPTY = "[CaaSMessageBroker Validation] Namespace has not been defined and it is required";
    private final static String CONTAINER_PLATFORM_IS_EMPTY = "[CaaSMessageBroker Validation] ContainerPlatform defined was either empty or blank and it is required";

    private String containerPlatform;
    private String namespace;
    private ProviderType provider;

    @Override
    public ProviderType getProvider() {
        return provider;
    }

    public abstract void initialiseParameters(ProviderType provider, Component parentComponent);

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
