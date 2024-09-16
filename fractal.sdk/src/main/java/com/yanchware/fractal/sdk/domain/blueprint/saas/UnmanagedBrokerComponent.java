package com.yanchware.fractal.sdk.domain.blueprint.saas;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.SAAS_UNMANAGED_BROKER;

@ToString(callSuper = true)
public class UnmanagedBrokerComponent extends Component implements BlueprintComponent {
  public static final String TYPE = SAAS_UNMANAGED_BROKER.getId();
}
