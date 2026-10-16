package com.yanchware.fractal.sdk.domain.blueprint.caas;

import com.yanchware.fractal.sdk.domain.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_TRACING;

@ToString(callSuper = true)
public class CaaSTracing extends CaaSComponent implements BlueprintComponent {
  public static final String TYPE = CAAS_TRACING.getId();
}
