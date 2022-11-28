package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSLogging;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public abstract class CaaSLoggingImpl extends CaaSLogging implements LiveSystemComponent {
    private final static String NAMESPACE_IS_NULL_OR_EMPTY = "[CaaSLogging Validation] Namespace has not been defined and it is required";
    private final static String CONTAINER_PLATFORM_IS_EMPTY = "[CaaSLogging Validation] ContainerPlatform defined was either empty or blank and it is required";
    private final static String STORAGE_IS_BLANK = "[ElasticLogging Validation] Elastic Storage has not been defined and it is required";

    private String containerPlatform;
    private String namespace;
    private String storage;
    private String storageClassName;
    private int memory;
    private int cpu;
    private ProviderType provider;

    @Override
    public ProviderType getProvider() {
        return provider;
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

        if (isBlank(storage)) {
            errors.add(STORAGE_IS_BLANK);
        }

        return errors;
    }
}
