package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.blueprint.caas.CaaSAPIGateway;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.ToString;

@ToString(callSuper = true)
public abstract class CaaSAPIGatewayImpl extends CaaSAPIGateway implements LiveSystemComponent {

  @Override
  public ProviderType getProvider() {
    return ProviderType.CAAS;
  }
}
