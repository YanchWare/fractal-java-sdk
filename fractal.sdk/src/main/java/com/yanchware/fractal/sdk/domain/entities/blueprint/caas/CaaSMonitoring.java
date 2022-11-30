package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CAAS_MONITORING;

public class CaaSMonitoring extends CaaSComponent implements BlueprintComponent {
  public static final String TYPE = CAAS_MONITORING.getId();
}
