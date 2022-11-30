package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CAAS_LOGGING;

public class CaaSLogging extends CaaSComponent implements BlueprintComponent {
  public static final String TYPE = CAAS_LOGGING.getId();
}
