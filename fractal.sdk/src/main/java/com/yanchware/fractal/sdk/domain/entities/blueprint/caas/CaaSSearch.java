package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_SEARCH;

@ToString(callSuper = true)
public class CaaSSearch extends CaaSComponent implements BlueprintComponent {
    public static final String TYPE = CAAS_SEARCH.getId();
}
