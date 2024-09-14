package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_MONITORING;

@ToString(callSuper = true)
public class CaaSMonitoring extends CaaSComponent implements BlueprintComponent {
  public static final String TYPE = CAAS_MONITORING.getId();
}
