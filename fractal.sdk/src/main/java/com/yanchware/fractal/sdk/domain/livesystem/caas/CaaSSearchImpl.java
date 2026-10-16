package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.blueprint.caas.CaaSSearch;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
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
public abstract class CaaSSearchImpl extends CaaSSearch implements LiveSystemComponent {
    private String storage;
    private String storageClassName;
    private String memory;
    private String cpu;

    @Override
    public ProviderType getProvider(){
        return ProviderType.CAAS;
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (storage != null && isBlank(storage)) {
            errors.add(getBlankErrorMessage("Storage"));
        }

        if (storageClassName != null && isBlank(storageClassName)) {
            errors.add(getBlankErrorMessage("Storage class name"));
        }

        if (memory != null && isBlank(memory)) {
            errors.add(getBlankErrorMessage("Memory"));
        }

        if (cpu != null && isBlank(cpu)) {
            errors.add(getBlankErrorMessage("CPU"));
        }

        return errors;
    }

    private String getBlankErrorMessage(String field) {
        return String.format("[%s Validation] %s defined was either empty or blank", this.getClass().getSimpleName(), field);
    }
}
