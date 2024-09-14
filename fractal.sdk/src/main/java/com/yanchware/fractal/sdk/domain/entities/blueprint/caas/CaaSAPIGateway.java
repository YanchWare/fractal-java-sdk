package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_API_GATEWAY;

@ToString(callSuper = true)
public class CaaSAPIGateway extends CaaSComponent implements BlueprintComponent {
  public static final String TYPE = CAAS_API_GATEWAY.getId();

  public CaaSAPIGateway() {
    this.setRecreateOnFailure(true);
  }
}