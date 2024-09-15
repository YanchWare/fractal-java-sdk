package com.yanchware.fractal.sdk.domain.blueprint.caas;

import com.yanchware.fractal.sdk.domain.blueprint.BlueprintComponent;
import com.yanchware.fractal.sdk.domain.blueprint.caas.CaaSComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_API_GATEWAY;

@ToString(callSuper = true)
public class CaaSAPIGateway extends CaaSComponent implements BlueprintComponent {
  public static final String TYPE = CAAS_API_GATEWAY.getId();

  public CaaSAPIGateway() {
    this.setRecreateOnFailure(true);
  }
}