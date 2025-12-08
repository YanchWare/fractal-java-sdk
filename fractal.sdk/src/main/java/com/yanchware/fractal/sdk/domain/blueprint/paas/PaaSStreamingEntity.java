package com.yanchware.fractal.sdk.domain.blueprint.paas;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_MESSAGE_ENTITY;
import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_STREAMING_ENTITY;

@ToString(callSuper = true)
public class PaaSStreamingEntity extends Component implements BlueprintComponent {
  public static final String TYPE = PAAS_STREAMING_ENTITY.getId();
}
