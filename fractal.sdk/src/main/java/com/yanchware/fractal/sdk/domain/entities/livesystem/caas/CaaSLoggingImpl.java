package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSLogging;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class CaaSLoggingImpl extends CaaSLogging implements LiveSystemComponent {

    private String storage;
    private String storageClassName;
    private int memory;
    private int cpu;

    @Override
    public ProviderType getProvider(){
        return ProviderType.CAAS;
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (isBlank(storage)) {
            errors.add(getStorageIsBlankErrorMessage());
        }

        return errors;
    }

    private String getStorageIsBlankErrorMessage() {
        return String.format("[%s Validation] Storage has not been defined and it is required", this.getClass().getName());
    }
}
