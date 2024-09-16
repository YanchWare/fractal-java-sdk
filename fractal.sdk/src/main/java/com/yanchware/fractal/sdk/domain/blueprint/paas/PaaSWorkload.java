package com.yanchware.fractal.sdk.domain.blueprint.paas;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_WORKLOAD;

@ToString(callSuper = true)
public class PaaSWorkload extends Component implements BlueprintComponent {
  public static final String TYPE = PAAS_WORKLOAD.getId();
}


