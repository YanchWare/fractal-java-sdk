package com.yanchware.fractal.sdk.domain.blueprint.caas;

import com.yanchware.fractal.sdk.domain.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_WORKLOAD;

@ToString(callSuper = true)
public class CaaSWorkload extends CaaSComponent implements BlueprintComponent {
  public static final String TYPE = CAAS_WORKLOAD.getId();
}
