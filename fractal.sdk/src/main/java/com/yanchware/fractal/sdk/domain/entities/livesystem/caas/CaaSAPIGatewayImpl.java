package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSAPIGateway;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public abstract class CaaSAPIGatewayImpl extends CaaSAPIGateway implements LiveSystemComponent {

    @Setter
    @Getter
    private ProviderType provider;
}
