package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CAAS_MONITORING;

public class CaaSPrometheus extends Component implements BlueprintComponent {
    public static final String TYPE = CAAS_MONITORING.getId();
}
