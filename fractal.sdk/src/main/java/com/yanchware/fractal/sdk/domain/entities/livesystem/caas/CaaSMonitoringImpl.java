package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSMonitoring;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.ToString;

import java.util.Collection;

@ToString(callSuper = true)
public abstract class CaaSMonitoringImpl extends CaaSMonitoring implements LiveSystemComponent {

    @Override
    public ProviderType getProvider(){
        return ProviderType.CAAS;
    }

    @Override
    public Collection<String> validate() {
        return super.validate();
    }
}
