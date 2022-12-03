package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CAAS_WORKLOAD;

@ToString(callSuper = true)
public class CaaSWorkload extends CaaSComponent implements BlueprintComponent {
  public static final String TYPE = CAAS_WORKLOAD.getId();
}
