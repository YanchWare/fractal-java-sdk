package com.yanchware.fractal.sdk.domain.blueprint.iaas;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.blueprint.BlueprintComponent;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.IAAS_VIRTUAL_MACHINE;

public class IaaSVirtualMachine extends Component implements BlueprintComponent {
  public static final String TYPE = IAAS_VIRTUAL_MACHINE.getId();
}
