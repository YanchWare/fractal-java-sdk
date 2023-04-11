package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSAPIGateway;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.ToString;

@ToString(callSuper = true)
public abstract class CaaSAPIGatewayImpl extends CaaSAPIGateway implements LiveSystemComponent {

    @Override
    public ProviderType getProvider(){
        return ProviderType.CAAS;
    }
}
