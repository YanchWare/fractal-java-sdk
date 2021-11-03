package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PROMETHEUS;

public class CaaSPrometheus extends Component implements BlueprintComponent {
    public static final String TYPE = PROMETHEUS.getId();
}
