package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.API_GATEWAY;

@ToString(callSuper = true)
public class CaaSAPIGateway extends CaaSComponent implements BlueprintComponent {
  public static final String TYPE = API_GATEWAY.getId();
}