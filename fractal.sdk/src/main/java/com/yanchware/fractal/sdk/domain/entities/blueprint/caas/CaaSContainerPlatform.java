package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import com.yanchware.fractal.sdk.domain.entities.Component;
import lombok.ToString;

@ToString(callSuper = true)
public class CaaSContainerPlatform extends Component implements BlueprintComponent {
    public static final String TYPE = "NetworkAndCompute.CaaS.ContainerPlatform";
}
