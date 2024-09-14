package com.yanchware.fractal.sdk.domain.entities.blueprint.saas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.SAAS_UNMANAGED_BROKER;

@ToString(callSuper = true)
public class UnmanagedBrokerComponent extends Component implements BlueprintComponent {
  public static final String TYPE = SAAS_UNMANAGED_BROKER.getId();
}
