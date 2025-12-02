package com.yanchware.fractal.sdk.domain.fractal.caas;

import com.yanchware.fractal.sdk.domain.fractal.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_LOGGING;

@ToString(callSuper = true)
public class CaaSLogging extends CaaSComponent implements BlueprintComponent {
  public static final String TYPE = CAAS_LOGGING.getId();
}
