package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSTracing;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.JaegerStorageType.Unknown;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class CaaSTracingImpl extends CaaSTracing implements LiveSystemComponent {
    private final static String NAMESPACE_IS_NULL_OR_EMPTY = "[CaaSTracing Validation] Namespace has not been defined and it is required";
    private final static String CONTAINER_PLATFORM_IS_EMPTY = "[CaaSTracing Validation] ContainerPlatform defined was either empty or blank and it is required";

    private String namespace;
    private String containerPlatform;
    private JaegerStorageSetting storageSettings;
    private ProviderType provider;

    protected CaaSTracingImpl() {
        storageSettings = new JaegerStorageSetting();
        storageSettings.setType(Unknown);
    }
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
        return errors;
    }
}
