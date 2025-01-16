package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.blueprint.caas.CaaSMonitoring;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.ToString;

import java.util.Collection;

@ToString(callSuper = true)
public abstract class CaaSMonitoringImpl extends CaaSMonitoring implements LiveSystemComponent {

  @Override
  public ProviderType getProvider() {
    return ProviderType.CAAS;
  }

  @Override
  public Collection<String> validate() {
    return super.validate();
  }
}
