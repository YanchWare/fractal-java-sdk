package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSMonitoring;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@ToString(callSuper = true)
public abstract class CaaSMonitoringImpl extends CaaSMonitoring implements LiveSystemComponent {

    @Getter
    @Setter
    private ProviderType provider;

    @Override
    public Collection<String> validate() {
        return super.validate();
    }
}
